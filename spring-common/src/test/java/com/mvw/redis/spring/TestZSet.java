package com.mvw.redis.spring;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 建议直接用jedis,spring封装的不好用
 * 并且有点hibernate的味道，都不知道实际对应的命令
 * 自己封装jedis非常不错
 * 
 * zset适合用有序队列
	intersectAndStore(K, K, K)
	intersectAndStore(K, Collection<K>, K)
	unionAndStore(K, K, K)
	unionAndStore(K, Collection<K>, K)
	
	range(K, long, long)
	reverseRange(K, long, long)
	rangeWithScores(K, long, long)
	reverseRangeWithScores(K, long, long)
	
	rangeByLex(K, Range)
	rangeByLex(K, Range, Limit)
	
	rangeByScore(K, double, double)
	rangeByScore(K, double, double, long, long)
	reverseRangeByScore(K, double, double)
	reverseRangeByScore(K, double, double, long, long)
	rangeByScoreWithScores(K, double, double)
	rangeByScoreWithScores(K, double, double, long, long)
	reverseRangeByScoreWithScores(K, double, double)
	reverseRangeByScoreWithScores(K, double, double, long, long)
	
	add(K, V, double)
	add(K, Set<TypedTuple<V>>)
	
	incrementScore(K, V, double)
	
	rank(K, Object)
	reverseRank(K, Object)
	score(K, Object)
	remove(K, Object...)
	removeRange(K, long, long)
	removeRangeByScore(K, double, double)
	count(K, double, double)
	size(K)
	zCard(K)
	
	scan(K, ScanOptions)

 * @author gaotingping
 *
 * 2016年7月26日 下午4:24:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-redis.xml"})
public class TestZSet {

	@Autowired
	private StringRedisTemplate t;
	
	private ZSetOperations<String, String> zset=null;
	
	private final String key="k";
	
	@Before
	public void init(){
		zset = t.opsForZSet();
	}
	
	@Test
	public void test1(){
		for(int i=0;i<10;i++){
			zset.add(key, "v"+i, i);//添加
		}
	}
	
	@Test
	public void test11(){
		for(int i=3;i<7;i++){
			zset.add("k1", "v"+i, i);//添加
		}
	}
	
	@Test //删除
	public void test2(){
		Long result = zset.remove(key, "v8");
		//zset.removeRange(key, start, end);
		//zset.removeRangeByScore(key, min, max);
		System.out.println(result);
	}
	
	@Test //统计
	public void test22(){
		Long c = zset.count("k", 0, 3);//统计
		System.out.println(c);
		c = zset.zCard("k");//统计总数
		System.out.println(c);
	}
	
	@Test //分数
	public void test3(){
		Double d = zset.score("k", "v3");//分数
		zset.incrementScore("k", "v3", 3);//增加分数
		System.out.println(d);
	}
	
	@Test //计算交集
	public void test4(){
		
		//计算交集，将结果存储到最后一个参数，score是两个集合元素的和
		Long tmp = zset.intersectAndStore(key, "k1", "k_k1");
		System.out.println(tmp);
	}
	
	@Test //计算并集
	public void test5(){
		
		//计算交集，将结果存储到最后一个参数，score是两个集合元素的和
		Long tmp = zset.unionAndStore(key, "k1", "k_k1_2");
		System.out.println(tmp);
	}
	
	@Test
	public void test6(){
		Set<String> list = zset.range("k", 0, 3); //指定区间内的成员,这里算的是排名
		for(String lt:list){
			System.out.println(lt);
		}
	}
	
	@Test  //获得指定元素的排名
	public void test7(){
		//排名 从0开始  从大到小
		Long c = zset.reverseRank("k", "v3");
		System.out.println(c);
		
		//排名 从0开始 从小到大
		c=t.opsForZSet().rank("k", "v3");
		System.out.println(c);
	}
}