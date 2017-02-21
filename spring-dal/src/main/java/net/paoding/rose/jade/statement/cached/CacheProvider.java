package net.paoding.rose.jade.statement.cached;

import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 定义 CacheProvider 接口从缓存池名称获取实例。
 * 
 */
public interface CacheProvider {

    /**
     * 从缓存池的名称获取实例。
     * 
     * @param poolName - 缓存池的名称,建立池的概念相当于一个集合，我可以把不同的集合分配到不同的池中
     * 
     * @return 缓存池实例
     */
    CacheInterface getCacheByPool(StatementMetaData metaData, String poolName);
}
