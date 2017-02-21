package net.paoding.rose.jade.statement.cached;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个实现演示实例，提供 ConcurrentHashMap 
 * 缓存池的 {@link CacheInterface} 实现。
 * 可以参考此实例以实现自己的业务封装
 * 
 */
public class MockCache implements CacheInterface {

    private static Logger logger=LoggerFactory.getLogger(MockCache.class);

    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();

    private String poolName; // 缓存名称

    private int maxSize = 100; // 默认值

    public MockCache(String poolName) {
        this.poolName = poolName;
    }

    public MockCache(String poolName, int maxSize) {
        this.poolName = poolName;
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public Object get(String key) {

        Object value = map.get(key);

        if (logger.isDebugEnabled()) {
            logger.debug("Get cache \'" + key + "\' from pool \'" + poolName + "\': " + value);
        }

        return value;
    }

    @Override
    public boolean set(String key, Object value, int expiry) {

        if (logger.isDebugEnabled()) {
            logger.debug("Set cache \'" + key + "\' to pool \'" + poolName + "\': " + value);
        }

        if (map.size() >= maxSize) {
            map.remove(map.keys().nextElement());
        }

        map.put(key, value);
        return true;
    }

    @Override
    public boolean delete(String key) {

        if (logger.isDebugEnabled()) {
            logger.debug("Remove cache \'" + key + "\' from pool \'" + poolName + "\'.");
        }

        map.remove(key);
        return true;
    }
}
