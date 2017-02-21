package net.paoding.rose.jade.context.spring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.lang.IllegalClassException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * spring之数据源处理
 */
public class SpringDataSourceFactory implements DataSourceFactory, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(SpringDataSourceFactory.class);

    private ListableBeanFactory applicationContext;

    /**
     * 按照DAO接口的Class对{@link DataSourceHolder}做缓存
     */
    private ConcurrentHashMap<Class<?>, DataSourceHolder> cachedDataSources = new ConcurrentHashMap<Class<?>, DataSourceHolder>();

    public SpringDataSourceFactory() {
    }

    public SpringDataSourceFactory(ListableBeanFactory applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 处理规则如下，序号代表优先级别:
     * 1)逐层查找id="jade.dataSource.daoClass"的数据源
     * 2)逐层查找catalog标注的
     * 3)查找id="jade.dataSource"
     * 4)查找id="dataSource"
     */
    @Override
    public DataSourceHolder getHolder(StatementMetaData metaData,
            Map<String, Object> runtimeProperties) {
    	
    	/*目标接口Class对象*/
        Class<?> daoClass = metaData.getDAOMetaData().getDAOClass();
        
        /*缓存中有，不进行其它处理*/
        DataSourceHolder holder = cachedDataSources.get(daoClass);
        if (holder != null) {
            return holder;
        }

        /*逐层查找id="jade.dataSource.daoClass"的数据源*/
        holder = getDataSourceByDirectory(daoClass, daoClass.getName());
        if (holder != null) {
            cachedDataSources.put(daoClass, holder);
            return holder;
        }
        
        /*查找DAO注解之catalog指定的数据源，即id="jade.dataSource.daoClass"的查找规则优先于它，catalog标注得也支持逐层查找*/
        String catalog = daoClass.getAnnotation(DAO.class).catalog();
        if (catalog.length() > 0) {
            holder = getDataSourceByDirectory(daoClass, catalog + "." + daoClass.getSimpleName());
        }
        if (holder != null) {
            cachedDataSources.put(daoClass, holder);
            return holder;
        }
        
        /*查找id="jade.dataSource"的*/
        holder = getDataSourceByKey(daoClass, "jade.dataSource");
        if (holder != null) {
            cachedDataSources.put(daoClass, holder);
            return holder;
        }
        
        /*查找id="dataSource"的*/
        holder = getDataSourceByKey(daoClass, "dataSource");
        if (holder != null) {
            cachedDataSources.put(daoClass, holder);
            return holder;
        }
        
        /*如上规则都没有那自然是null,上层自己去处理去*/
        return null;
    }

    /**
     * 从spring容器中寻找对应的数据源。对于给定的一个DAO接口，如
	 * com.mycompany.myapp.dao.UserDAO, 其规则如下：
	 * <p>
	 * <ul>
	 * <li>如果存在id/name为jade.dataSource.com.mycompany.myapp.dao.
	 * UserDAO的数据源，则使用它作为这个DAO的数据源，否则逐级询问配置，直到顶一级包名：jade.dataSource.com</li>
	 * <li>如果以上仍未能确定UserDAO的数据源，且UserDAO接口上的<code>@DAO</code>
	 * 的catalog属性非空（假设其值为myteam.myapp），则视myteam.myapp等同于package名，执行前一个步骤的问询</li>
	 * <ul>
	 * <li>即按此顺序问询Spring容器的配置：jade.dataSource.myteam.myapp.UserDAO，...，jade.
	 * dataSource.myteam</li>
	 * </ul>
	 * <li>
	 * 如果以上仍未能确定UserDAO的数据源，则判断是否存在id/name为jade.dataSource、dataSource的数据源</li>
	 * <li>
	 * 如果以上仍未能确定UserDAO的数据源，则最终就是没有数据源，运行时将会有异常抛出</li>
	 * </ul> <br>
	 * 
     * @param daoClass
     * @param catalog
     * @return
     */
    private DataSourceHolder getDataSourceByDirectory(Class<?> daoClass, String catalog) {
        String tempCatalog = catalog;
        DataSourceHolder dataSource;
        while (tempCatalog != null && tempCatalog.length() > 0) {
            dataSource = getDataSourceByKey(daoClass, "jade.dataSource." + tempCatalog);
            if (dataSource != null) {
                return dataSource;
            }
            int index = tempCatalog.lastIndexOf('.');
            if (index == -1) {
                tempCatalog = null;
            } else {
                tempCatalog = tempCatalog.substring(0, index);
            }
        }
        return null;
    }

    /**
     * applicationContext中查找id=key的{@link DataSourceFactory}或{@link DataSource}的对象
     * 
     * @param daoClass
     * @param key
     * @return
     */
    private DataSourceHolder getDataSourceByKey(Class<?> daoClass, String key) {
        if (applicationContext.containsBean(key)) {
            Object dataSource = applicationContext.getBean(key);
            if (!(dataSource instanceof DataSource) && !(dataSource instanceof DataSourceFactory)) {
                throw new IllegalClassException("expects DataSource or DataSourceFactory, but a " + dataSource.getClass().getName());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("found dataSource: " + key + " for DAO " + daoClass.getName());
            }
            return new DataSourceHolder(dataSource);
        }
        return null;
    }
}
