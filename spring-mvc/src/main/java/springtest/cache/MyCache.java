package springtest.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * 缓存层底层实现
 */
public class MyCache implements Cache {

	private String name;/*不同的缓存用名称类区分*/

	private Map<Object, Object> store = new ConcurrentHashMap<Object, Object>();;

	public MyCache() {
		
	}

	public MyCache(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getNativeCache() {
		return store;
	}

	/**
	 * 根据key得到一个ValueWrapper，然后调用其get方法获取值
	 * 
	 */
	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper result = null;
		Object thevalue = store.get(key);
		if (thevalue != null) {
			result = new SimpleValueWrapper(thevalue);
		}
		return result;
	}

	/**
	 * 保存数据
	 */
	@Override
	public void put(Object key, Object value) {
		store.put(key, value);
	}

	/**
	 * 根据key删数据
	 */
	@Override
	public void evict(Object key) {
		store.remove(key);
	}

	/**
	 * 清空数据
	 */
	@Override
	public void clear() {
		store.clear();
	}

	/**
	 * 根据key，和value的类型直接获取value
	 */
	@Override
    public <T> T get(Object key, Class<T> type) {
	    return null;
    }

	/**
	 * 如果值不存在，则添加，用来替代如下代码
	 * 
	 * Object existingValue = cache.get(key);
	 * if (existingValue == null) {
	 *     cache.put(key, value);
	 *     return null;
	 * } else {
	 *     return existingValue;
	 * }
	 */
	@Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
	    return null;
    }

	@Override
	public <T> T get(Object arg0, Callable<T> arg1) {
		return null;
	}
}
