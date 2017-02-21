package net.paoding.rose.jade.context.spring;

import java.lang.reflect.Proxy;

import net.paoding.rose.jade.context.JadeInvocationHandler;
import net.paoding.rose.jade.dataaccess.DataAccessFactory;
import net.paoding.rose.jade.rowmapper.RowMapperFactory;
import net.paoding.rose.jade.statement.DAOMetaData;
import net.paoding.rose.jade.statement.InterpreterFactory;
import net.paoding.rose.jade.statement.StatementWrapperProvider;
import net.paoding.rose.jade.statement.cached.CacheProvider;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 
 * jade bean注册工厂对象<br/>
 * jade的接口经{@link JadeBeanFactoryPostProcessor}<br/>
 * 封装成{@link JadeFactoryBean}的形式注册到spring容器中
 * 
 */
public class JadeFactoryBean implements FactoryBean<Object>, InitializingBean {

    protected Class<?> objectType;

    protected DataAccessFactory dataAccessFactory;

    protected RowMapperFactory rowMapperFactory;

    protected InterpreterFactory interpreterFactory;

    protected CacheProvider cacheProvider;

    protected Object daoObject;
    
    // 可选的
    private StatementWrapperProvider statementWrapperProvider;

    public JadeFactoryBean() {
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

    public void setObjectType(Class<?> objectType) {
        this.objectType = objectType;
    }

    /**
     * 
     * @param dataAccessFactory
     */
    public void setDataAccessFactory(DataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
    }

    public DataAccessFactory getDataAccessFactory() {
        return dataAccessFactory;
    }

    /**
     * 
     * @param rowMapperFactory
     */
    public void setRowMapperFactory(RowMapperFactory rowMapperFactory) {
        this.rowMapperFactory = rowMapperFactory;
    }

    public RowMapperFactory getRowMapperFactory() {
        return rowMapperFactory;
    }

    /**
     * 
     * @param interpreterFactory
     */
    public void setInterpreterFactory(InterpreterFactory interpreterFactory) {
        this.interpreterFactory = interpreterFactory;
    }

    public InterpreterFactory getInterpreterFactory() {
        return interpreterFactory;
    }

    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public CacheProvider getCacheProvider() {
        return cacheProvider;
    }
    
    public StatementWrapperProvider getStatementWrapperProvider() {
        return statementWrapperProvider;
    }
    
    public void setStatementWrapperProvider(StatementWrapperProvider statementWrapperProvider) {
        this.statementWrapperProvider = statementWrapperProvider;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(objectType.isInterface(), "not a interface class: " + objectType.getName());
        Assert.notNull(dataAccessFactory);
        Assert.notNull(rowMapperFactory);
        Assert.notNull(interpreterFactory);
        // cacheProvider可以null，不做assert.notNull判断
    }

    @Override
    public Object getObject() {
        if (daoObject == null) {
            daoObject = createDAO();
            Assert.notNull(daoObject);
        }
        return daoObject;
    }

    protected Object createDAO() {
        try {
            DAOMetaData daoMetaData = new DAOMetaData(objectType);
            JadeInvocationHandler handler = new JadeInvocationHandler(
                    //
                    daoMetaData, interpreterFactory, rowMapperFactory, dataAccessFactory,
                    cacheProvider, statementWrapperProvider);
            return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(),
                    new Class[] { objectType }, handler);
        } catch (RuntimeException e) {
            throw new IllegalStateException("failed to create bean for "
                    + this.objectType.getName(), e);
        }
    }

}
