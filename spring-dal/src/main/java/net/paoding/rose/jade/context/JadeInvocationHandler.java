package net.paoding.rose.jade.context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.annotation.SQLType;
import net.paoding.rose.jade.dataaccess.DataAccessFactory;
import net.paoding.rose.jade.rowmapper.RowMapperFactory;
import net.paoding.rose.jade.statement.DAOMetaData;
import net.paoding.rose.jade.statement.Interpreter;
import net.paoding.rose.jade.statement.InterpreterFactory;
import net.paoding.rose.jade.statement.JdbcStatement;
import net.paoding.rose.jade.statement.Querier;
import net.paoding.rose.jade.statement.SelectQuerier;
import net.paoding.rose.jade.statement.Statement;
import net.paoding.rose.jade.statement.StatementMetaData;
import net.paoding.rose.jade.statement.StatementWrapperProvider;
import net.paoding.rose.jade.statement.UpdateQuerier;
import net.paoding.rose.jade.statement.cached.CacheProvider;
import net.paoding.rose.jade.statement.cached.CachedStatement;

/**
 * 2016 by gtp log->slf4j
 * 
 * jade接口实现类:基于动态代理封装元数据，负责对DAO接口的代理
 */
public class JadeInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(JadeInvocationHandler.class);

    /**
     * 对Statement对象做了缓存
     */
    private final ConcurrentHashMap<Method, Statement> statements = new ConcurrentHashMap<Method, Statement>();

    private final DAOMetaData daoMetaData;/*dao接口元信息封装*/

    private final DataAccessFactory dataAccessFactory;/*数据源访问*/

    private final RowMapperFactory rowMapperFactory;/*行映射处理器*/

    private final InterpreterFactory interpreterFactory;/*解析器，增强预留，可以理解为jade的拦截器*/

    private final CacheProvider cacheProvider;/*缓存提供者*/
    
    private final StatementWrapperProvider statementWrapperProvider;/*statement封装*/
    
    public JadeInvocationHandler(
            DAOMetaData daoMetaData,
            InterpreterFactory interpreterFactory,
            RowMapperFactory rowMapperFactory,
            DataAccessFactory dataAccessFactory,
            CacheProvider cacheProvider,
            StatementWrapperProvider statementWrapperProvider) {
        this.daoMetaData = daoMetaData;
        this.rowMapperFactory = rowMapperFactory;
        this.dataAccessFactory = dataAccessFactory;
        this.interpreterFactory = interpreterFactory;
        this.cacheProvider = cacheProvider;
        this.statementWrapperProvider = statementWrapperProvider;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      
    	final boolean debugEnabled = logger.isDebugEnabled();
        if (debugEnabled) {
            logger.debug("invoking " + daoMetaData.getDAOClass().getName() + "#" + method.getName());
        }

        /*Object对象的方法忽略*/
        if (method.getDeclaringClass() == Object.class) {
            return invokeObjectMethod(proxy, method, args);
        }
        
        /*获取当前DAO方法对应的Statement对象*/
        Statement statement = getStatement(method);
        
        /*将参数放入 Map，组织结构为:number=value或sqlParam标注的值=value的格式,两种同有会重复存储*/
        Map<String, Object> parameters=null;
        StatementMetaData statemenetMetaData = statement.getMetaData();
        if (args == null || args.length == 0) {
            parameters = new HashMap<String, Object>(4);
        } else {
        	/*map装填因子一般取0.75，而参数一般不多，故这里+4是合理的*/
            parameters = new HashMap<String, Object>(args.length * 2 + 4);
            for (int i = 0; i < args.length; i++) {
                parameters.put(":"+(i+1), args[i]);
                SQLParam sqlParam = statemenetMetaData.getSQLParamAt(i);
                if (sqlParam != null) {
                    parameters.put(sqlParam.value(), args[i]);
                }
            }
        }
        
        /*logging*/
        StringBuilder invocationInfo = null;
        if (debugEnabled) {
            invocationInfo = getInvocationInfo(statemenetMetaData, parameters);
            logger.debug("invoking " + invocationInfo);
        }

        /*executing*/
        long begin = System.currentTimeMillis();
        final Object result = statement.execute(parameters);
        long cost = System.currentTimeMillis() - begin;

        /*logging*/
        if (logger.isInfoEnabled()) {
            if (invocationInfo == null) {
                invocationInfo = getInvocationInfo(statemenetMetaData, parameters);
            }
            logger.info("cost " + cost + "ms: " + invocationInfo);
        }
        
        return result;
    }

    /**
     * 打印InvocationInfo(SQL和参数)
     * 
     * @param metaData
     * @param parameters
     * @return
     */
    private StringBuilder getInvocationInfo(StatementMetaData metaData,
            Map<String, Object> parameters) {
       
    	StringBuilder sb = new StringBuilder();
        sb.append(metaData).append("\n");
        sb.append("\tsql: ").append(metaData.getSQL()).append("\n");
        sb.append("\tparameters: ");
        ArrayList<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            sb.append(key).append("='").append(parameters.get(key)).append("'  ");
        }
        
        return sb;
    }

    /**
     * Statement是执行封装者
     * 这里做了个缓存以提高效率
     * 
     * @param method
     * @return
     */
    private Statement getStatement(Method method) {
        Statement statement = statements.get(method);
        if (statement == null) {
            synchronized (method) {
                statement = statements.get(method);/*注意这里在同步块中再取一次*/
                /*!!!*/
                if (statement == null) {
                	
                    StatementMetaData smd = new StatementMetaData(daoMetaData, method);
                    SQLType sqlType = smd.getSQLType();
                    Querier querier;
                    
                    /*对sql分为r/w进行不同的处理，这是能支持m/s结构的基础*/
                    if (sqlType == SQLType.READ) {
                        RowMapper<?> rowMapper = rowMapperFactory.getRowMapper(smd);
                        querier = new SelectQuerier(dataAccessFactory, smd, rowMapper);
                    } else {
                        querier = new UpdateQuerier(dataAccessFactory, smd);
                    }
                    
                    /*interpreter可以理解为jade的拦截器，分表就是基于它完成的*/
                    Interpreter[] interpreters = interpreterFactory.getInterpreters(smd);
                    statement = new JdbcStatement(smd, sqlType, interpreters, querier);
                    if (cacheProvider != null) {/*cache的实现是包装模式*/
                        statement = new CachedStatement(cacheProvider, statement);
                    }
                    
                    /*缓存下*/
                    statements.put(method, wrap(statement));
                }
            }
        }
        return statement;
    }
    
    /**
     * 现在包装statement还没有弄懂
     * 根据getStatement看还有问题
     * 
     * FIXME gtp
     * 
     * @param statement
     * @return
     */
    private Statement wrap(Statement statement) {
        if (statementWrapperProvider != null) {
            return statementWrapperProvider.wrap(statement);
        }
        return statement;
    }

    //执行Object的方法
    private Object invokeObjectMethod(Object proxy, Method method, Object[] args)
            throws CloneNotSupportedException {
        String methodName = method.getName();
        if (methodName.equals("toString")) {
            return JadeInvocationHandler.this.toString();
        }
        if (methodName.equals("hashCode")) {
            return daoMetaData.getDAOClass().hashCode() * 13 + this.hashCode();
        }
        if (methodName.equals("equals")) {
            return args[0] == proxy;
        }
        if (methodName.equals("clone")) {
            throw new CloneNotSupportedException("clone is not supported for jade dao.");
        }
        throw new UnsupportedOperationException(daoMetaData.getDAOClass().getName() + "#" + method.getName());
    }

    @Override
    public String toString() {
        DAO dao = daoMetaData.getDAOClass().getAnnotation(DAO.class);
        String toString = daoMetaData.getDAOClass().getName() + "[catalog=" + dao.catalog() + "]";
        return toString;
    }

}
