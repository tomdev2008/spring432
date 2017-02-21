package net.paoding.rose.jade.dataaccess;

import javax.sql.DataSource;

/**
 * 用于表示一个 {@link DataSource} 或 {@link DataSourceFactory} 类。一个
 * {@link DataSourceHolder} 有且只能表示这两种类型其中之一
 * <p>
 * 
 * @see DataSourceFactory
 * 
 */
public class DataSourceHolder {

    private final DataSource dataSource;

    private final DataSourceFactory dataSourceFactory;

    /**
     * 构造一个holder实例，所提供的参数必须是 {@link DataSource} 或
     * {@link DataSourceFactory}类型
     * 
     * @throws IllegalArgumentException
     * 
     * @param dataSourceOrItsFactory
     */
    public DataSourceHolder(Object dataSourceOrItsFactory) {
        if (dataSourceOrItsFactory instanceof DataSource) {
            this.dataSource = (DataSource) dataSourceOrItsFactory;
            this.dataSourceFactory = null;
            return;
        }
        if (dataSourceOrItsFactory instanceof DataSourceFactory) {
            this.dataSource = null;
            this.dataSourceFactory = (DataSourceFactory) dataSourceOrItsFactory;
            return;
        }
        throw new IllegalArgumentException("" + dataSourceOrItsFactory);
    }

    //------------------

    /**
     * 包含的是一个DataSourceFactory?
     */
    public boolean isFactory() {
        return this.dataSourceFactory != null;
    }

    /**
     * 返回所代表的 {@link DataSource}，如不是返回null
     * 
     * @return
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 返回所代表的 {@link DataSourceFactory}，如不是返回null
     * 
     * @return
     */
    public DataSourceFactory getFactory() {
        return dataSourceFactory;
    }

}
