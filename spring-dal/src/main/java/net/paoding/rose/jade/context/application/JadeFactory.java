package net.paoding.rose.jade.context.application;

import java.lang.reflect.Proxy;

import javax.sql.DataSource;

import net.paoding.rose.jade.context.JadeInvocationHandler;
import net.paoding.rose.jade.dataaccess.DataAccessFactoryAdapter;
import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.datasource.SimpleDataSourceFactory;
import net.paoding.rose.jade.rowmapper.DefaultRowMapperFactory;
import net.paoding.rose.jade.rowmapper.RowMapperFactory;
import net.paoding.rose.jade.statement.DAOMetaData;
import net.paoding.rose.jade.statement.DefaultInterpreterFactory;
import net.paoding.rose.jade.statement.Interpreter;
import net.paoding.rose.jade.statement.StatementWrapperProvider;
import net.paoding.rose.jade.statement.cached.CacheProvider;

import org.springframework.util.ClassUtils;

/**
 * jade工厂:测试用
 */
@Deprecated
public class JadeFactory {

    private RowMapperFactory rowMapperFactory = new DefaultRowMapperFactory();

    private DefaultInterpreterFactory interpreterFactory = new DefaultInterpreterFactory();

    private DataAccessFactoryAdapter dataAccessFactory;

    private CacheProvider cacheProvider;

    // 可选的
    private StatementWrapperProvider statementWrapperProvider;

    public JadeFactory() {
    }

    public JadeFactory(DataSource defaultDataSource) {
        setDataSourceFactory(new SimpleDataSourceFactory(defaultDataSource));
    }

    public JadeFactory(DataSourceFactory dataSourceFactory) {
        setDataSourceFactory(dataSourceFactory);
    }

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataAccessFactory = new DataAccessFactoryAdapter(dataSourceFactory);
    }

    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public DataSourceFactory getDataSourceFactory() {
        if (this.dataAccessFactory == null) {
            return null;
        }
        return this.dataAccessFactory.getDataSourceFactory();
    }

    public void setRowMapperFactory(RowMapperFactory rowMapperFactory) {
        this.rowMapperFactory = rowMapperFactory;
    }

    public StatementWrapperProvider getStatementWrapperProvider() {
        return statementWrapperProvider;
    }

    public void setStatementWrapperProvider(StatementWrapperProvider statementWrapperProvider) {
        this.statementWrapperProvider = statementWrapperProvider;
    }

    public void addInterpreter(Interpreter... interpreters) {
        for (Interpreter interpreter : interpreters) {
            interpreterFactory.addInterpreter(interpreter);
        }
    }

    /**
     * 创建DAO代理实现
     * 
     * @param daoClass
     * @return
     * 
     * 2016年3月15日
     */
    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> daoClass) {
        try {
            DAOMetaData daoMetaData = new DAOMetaData(daoClass);
            JadeInvocationHandler handler = new JadeInvocationHandler(
                    daoMetaData, 
                    interpreterFactory, 
                    rowMapperFactory, 
                    dataAccessFactory,
                    cacheProvider, 
                    statementWrapperProvider);
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            return (T) Proxy.newProxyInstance(classLoader, new Class[] { daoClass }, handler);
        } catch (RuntimeException e) {
            throw new IllegalStateException("failed to create bean for " + daoClass.getName(), e);
        }
    }
}
