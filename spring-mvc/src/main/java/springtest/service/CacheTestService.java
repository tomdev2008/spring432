package springtest.service;

import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存测试类
 * 
 * <pre>
 *	 @CachePut   写
 *   @CacheEvict 删除 
 *   @Cacheable  先从缓存中读取，如果没有再调用方法获取数据，然后把数据添加到缓存中 
 * </pre>
 */
@Service
public class CacheTestService {

	//下面这些注解还很多属性可以学习
	//缓存本方法  指定key就不会走key的生成规则了 错误的不缓存
	@Cacheable(value="accountCache",key="#id",condition="#resut!=null")
	public long test(String id){
		return new Date().getTime();
	}
	
	//清除缓存  #p.getName()会调用参数的相应的方法   字符连接 spring的el语言表达式
    @CacheEvict(value="accountCache",key="#p.getName()+'_'+#p.getId()")
	public void test2(){
		
	}
    
	//演示内部调用  由于aop proxy原因这里调用test将不缓存
	public long test3(String id){
		return this.test(id);
	}
	
	/**
	 * 测试基于xml的配置 这样可以做到不改变任何原代码
	 * 在开发上可能显得不紧凑，但是这样却很满足显示需要啊
	 * 具体实现可以参考配置文件
	 * 对于目前趋势而言，注解的方式是非常受欢迎的，因为配置方式
	 * 阅读性不佳，特别是后期维护上
	 * 
	 * @param id
	 * @return
	 */
	public long find(String id){
		return new Date().getTime();
	}
	
	public void del(String id){
		
	}
    
    /*
     * supports querying and manipulating an object graph at runtime
     * 
     * 'Hello World' 				字符串
     * 'Hello World'.concat('!')	字符串连接
     * 'Hello World'.bytes  		调用javaBean属性
     * 'Hello World'.bytes.length   
     * new String('hello world').toUpperCase()
     * 
     * 配置中使用语法： #{ <expression string> }
     */
}
