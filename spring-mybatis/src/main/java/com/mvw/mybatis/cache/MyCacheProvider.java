package com.mvw.mybatis.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

/**
 * 自定义自己的缓存实现
 * 
 * <code>
 *  特点说明：
	    映射语句文件中的所有 select 语句将会被缓存。
	    映射语句文件中的所有 insert,update 和 delete 语句会刷新缓存。
	    缓存会使用 Least Recently Used(LRU,最近最少使用的)算法来收回。
	    根据时间表(比如 no Flush Interval,没有刷新间隔), 缓存不会以任何时间顺序 来刷新。
	    缓存会存储列表集合或对象(无论查询方法返回什么)的 1024 个引用。
	    缓存会被视为是 read/write(可读/可写)的缓存,意味着对象检索不是共享的,而 且可以安全地被调用者修改,而不干扰其他调用者或线程所做的潜在修改。
    
建议： 不建议使用，太弱了
    1)key无法定制:一般查询应该定义到具体的key，如ID等关键字作为缓存的key
    2)缓存是全局的，无法精确控制缓存部分方法
    3)有更新时，清除了全部缓存，这太简单粗暴了
    
            以上的3点足以说明其就是"幌子",完全骗人的，没有任何的实际应用价值
  
 所有的这些属性都可以通过缓存元素的属性来修改。

	比如: <cache  eviction="FIFO"  flushInterval="60000"  size="512"  readOnly="true"/>
	
	这个更高级的配置创建了一个 FIFO 缓存,并每隔 60 秒刷新,存数结果对象或列表的 512 个引用,而且返回的对象被认为是只读的,
	因此在不同线程中的调用者之间修改它们会 导致冲突。可用的收回策略有, 默认的是 LRU:

    LRU – 最近最少使用的:移除最长时间不被使用的对象。
    FIFO – 先进先出:按对象进入缓存的顺序来移除它们。
    SOFT – 软引用:移除基于垃圾回收器状态和软引用规则的对象。
    WEAK – 弱引用:更积极地移除基于垃圾收集器状态和弱引用规则的对象。

	flushInterval(刷新间隔)可以被设置为任意的正整数,而且它们代表一个合理的毫秒 形式的时间段。
	默认情况是不设置,也就是没有刷新间隔,缓存仅仅调用语句时刷新。
	
	size(引用数目)可以被设置为任意正整数,要记住你缓存的对象数目和你运行环境的 可用内存资源数目。默认值是1024。
	
	readOnly(只读)属性可以被设置为 true 或 false。只读的缓存会给所有调用者返回缓 存对象的相同实例。因此这些对象不能被修改。
	这提供了很重要的性能优势。可读写的缓存 会返回缓存对象的拷贝(通过序列化) 。这会慢一些,但是安全,因此默认是 false。
 * </code>
 */
public class MyCacheProvider implements Cache {

	/**
	 * 读写锁分离
	 */
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	/**
	 * 缓存标识符，一般是类对应的全称
	 */
	private String id;

	/**
	 * 存储容器
	 */
	private Map<Object, Object> cache = new ConcurrentHashMap<Object, Object>();

	public MyCacheProvider(String id) {
		this.id = id;
		System.out.println("缓存初始化成功:" + id);
		// 缓存初始化成功:com.mvw.cpm.dao.MyDao 类的全称
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getSize() {
		return cache.size();
	}

	/**
	 * 设置缓存
	 */
	@Override
	public void putObject(Object key, Object value) {
		//cache.put(key, value);
		System.out.println("缓存:put");
	}

	/**
	 * 获得缓存
	 */
	@Override
	public Object getObject(Object key) {
		System.out.println("缓存:get");
		return cache.get(key);
	}

	/**
	 * 删除缓存
	 */
	@Override
	public Object removeObject(Object key) {
		System.out.println("缓存:del");
		return cache.remove(key);
	}

	/**
	 * 全部清除缓存
	 */
	@Override
	public void clear() {
		cache.clear();
		System.out.println("缓存:clear");
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return rwl;
	}

}
