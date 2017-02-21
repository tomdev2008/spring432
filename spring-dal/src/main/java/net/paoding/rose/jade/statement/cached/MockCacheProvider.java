package net.paoding.rose.jade.statement.cached;

import java.util.concurrent.ConcurrentHashMap;

import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 提供 ConcurrentHashMap 缓存池的 {@link CacheProvider} 实现。
 * 
 * 缓存池：管理每个缓存集合，是缓存容器。真正的处理者是CacheInterface的实现类
 * 由其来处理具体的缓存操作(put/get/clear)
 */
public class MockCacheProvider implements CacheProvider {

    private ConcurrentHashMap<String, MockCache> caches = new ConcurrentHashMap<String, MockCache>();

    /**
     * 根据名称获取,同时提供了元数据可以做更多的定制 
     */
    @Override
    public CacheInterface getCacheByPool(StatementMetaData metaData, String poolName) {
        MockCache cache = caches.get(poolName);
        if (cache == null) {
            cache = new MockCache(poolName);

            MockCache cacheExist = caches.putIfAbsent(poolName, cache);
            if (cacheExist != null) {
                cache = cacheExist;
            }
        }

        return cache;
    }
}
