package net.paoding.rose.jade.dataaccess;

import java.util.Map;

import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 这是框架的内部接口，{@link DataAccess}的工厂类。
 * 
 */
public interface DataAccessFactory {

    /**
     * 运行时为框架提供一个 {@link DataAccess} 实例
     */
    DataAccess getDataAccess(StatementMetaData metaData, Map<String, Object> runtime);
}
