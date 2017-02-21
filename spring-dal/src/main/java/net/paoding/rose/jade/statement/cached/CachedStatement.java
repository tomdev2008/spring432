package net.paoding.rose.jade.statement.cached;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.paoding.rose.jade.annotation.Cache;
import net.paoding.rose.jade.annotation.CacheDelete;
import net.paoding.rose.jade.annotation.SQLType;
import net.paoding.rose.jade.statement.Statement;
import net.paoding.rose.jade.statement.StatementMetaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 
 * {@link CachedStatement} 封装了支持Cache的逻辑<p>
 * 将实际动作委托给了<code>realStatement</code><p>
 * --包装设计模式
 * 
 */
public class CachedStatement implements Statement {
	
	private static Logger logger=LoggerFactory.getLogger(CachedStatement.class);

    /**
     * 实际底层Statemenet，比如就是数据库操作的实际执行语句
     */
    private final Statement realStatement;

    /**
     * 注解在DAO方法上的 {@link Cache}，如果没有则为null
     * <p>
     * 仅在该语句为查询语句时侯此有效，即如果某个更新语句的DAO方法上也注解了 {@link Cache} 在这里也仍保留null
     */
    private final Cache cacheAnnotation;

    /**
     * 注解在该语句上的 {@link CacheDelete}，如果没有则为null
     * <p>
     * 
     * 可使用在各种语句上，甚至事查询语句上(很悲剧的：我也不知道为何有这种用法，执行一次查询还会附带删除神马Cache的啊？真的有吗？)
     */
    private final CacheDelete cacheDeleteAnnotation;

    /**
     * cache服务接口
     */
    private final CacheProvider cacheProvider;

    /**
     * 
     * @param cacheProvider
     * @param realStatement
     */
    public CachedStatement(CacheProvider cacheProvider, Statement realStatement) {
        this.realStatement = realStatement;
        this.cacheProvider = cacheProvider;
        StatementMetaData metaData = realStatement.getMetaData();
        SQLType sqlType = metaData.getSQLType();
        cacheDeleteAnnotation = metaData.getMethod().getAnnotation(CacheDelete.class);
        Cache cacheAnnotation = metaData.getMethod().getAnnotation(Cache.class);
        if (sqlType == SQLType.READ) {
            this.cacheAnnotation = cacheAnnotation;
        } else {
        	/*从这里可以看出，缓存注解放在写方法山无效 !!!*/
            this.cacheAnnotation = null;
            if (cacheAnnotation != null) {
                logger.warn("@" + Cache.class.getName() + " is invalid for a " + sqlType + " SQL:" + metaData.getSQL());
            }
        }
    }

    @Override
    public StatementMetaData getMetaData() {
        return realStatement.getMetaData();
    }

    @Override
    public Object execute(Map<String, Object> parameters) {
        Object value = null;
        if (cacheAnnotation == null) {
            value = realStatement.execute(parameters);
        } else {
        	/*先走缓存，没有则执行原方法并将结果如缓存*/
            CacheInterface cache = cacheProvider.getCacheByPool(getMetaData(), cacheAnnotation.pool());
            String cacheKey = buildKey(cacheAnnotation.key(), parameters);
            value = cache.get(cacheKey);
            if (value == null) {
                value = realStatement.execute(parameters);
                cache.set(cacheKey, value, cacheAnnotation.expiry());
            }
        }
        /*这里没有区分sql类型，只要有此注解就可以清除缓存!!!*/
        if (cacheDeleteAnnotation != null) {
            CacheInterface cache = cacheProvider.getCacheByPool(getMetaData(), cacheDeleteAnnotation.pool());
            for (String key : cacheDeleteAnnotation.key()) {
                String cacheKey = buildKey(key, parameters);
                cache.delete(cacheKey);
            }
        }
        return value;
    }

    /** 
     * 参数的模板:原模式无法拿出:name的值，获取到的都是null
     * 特此修改:仅允许xxx:number这样的参数格式来作为缓存的键值 
     */
    private static final Pattern PATTERN = Pattern.compile("(\\:[a-zA-Z0-9\\.]*)");

    /**
     * 查找模板 KEY 中所有的 :name, :name.property 参数替换成实际值。
     * 
     * @param key 			- 作为模板的 KEY
     * @param parameters 	- 传入的参数
     * 
     * @return 最终的缓存 KEY
     */
    private static String buildKey(String key, Map<String, Object> parameters) {

    	Matcher matcher = PATTERN.matcher(key);
        if (matcher.find()) {

            StringBuilder builder = new StringBuilder();

            int index = 0;

            do {
                final String name = matcher.group(1).trim();

                Object value = null;

                int find = name.indexOf('.');
                if (find >= 0) {

                    Object bean = parameters.get(name.substring(0, find));
                    if (bean != null) {
                        value = new BeanWrapperImpl(bean).getPropertyValue(name.substring(find + 1));
                    }

                } else {

                    value = parameters.get(name);
                }

                builder.append(key.substring(index, matcher.start()));
                
                builder.append(value);

                index = matcher.end();

            } while (matcher.find());

            builder.append(key.substring(index));

            return builder.toString();
        }

        return key;
    }
}
