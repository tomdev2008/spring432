package net.paoding.rose.jade.statement.cached;

/**
 * {@link CacheInterface} 抽象DAO方法所使用的缓存接口
 * 
 */
public interface CacheInterface {

    /**
     * 从缓存取出给定key对应的对象，如果没有则返回null
     * 
     */
    Object get(String key);

    /**
     * 将某个对象和给定的key绑定起来存储在缓存中
     * 
     * @param expiryInSecond - 缓存过期时间，单位为秒
     * 
     */
    boolean set(String key, Object value, int expiryInSecond);

    /**
     * 从 Cache 缓存池删除对象。
     * 
     * @param key - 缓存关键字
     */
    boolean delete(String key);
}
