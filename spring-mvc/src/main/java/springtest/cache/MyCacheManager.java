package springtest.cache;

import java.util.Collection;

import org.springframework.cache.support.AbstractCacheManager;

/**
 * 缓存管理器
 *
 * 面对mvc的层次结构，cache放那里更合适呢?
 * view--service--dao
 * 
 * dao层是合适的，view service层是有业务逻辑的，不仅仅返回了个结果
 * 而dao来cache数据，它是纯粹和合理的
 */
public class MyCacheManager extends AbstractCacheManager {

	private Collection<? extends MyCache> caches;

	public void setCaches(Collection<? extends MyCache> caches) {
		this.caches = caches;
	}

	@Override
    protected Collection<? extends MyCache> loadCaches() {
	    return this.caches;
    }
}
