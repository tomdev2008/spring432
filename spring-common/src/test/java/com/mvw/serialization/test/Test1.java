package com.mvw.serialization.test;

import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.mvw.serialization.support.HessianSerializer;
import com.mvw.serialization.support.HessianSerializer;
import com.mvw.serialization.support.JSONSerializer;
import com.mvw.serialization.support.JavaSerializer;
import com.mvw.serialization.support.KryoSerializer;

/**
 * https://github.com/eishay/jvm-serializers
 * 
 * 序列化测试
 * 无论从性能上还是序列化长度上，kryo都有优势
 * 
 *  jdk耗时=3866
 *  kryo耗时=576     		6.7陪 	~=7
 *  fastJson耗时=2276	    3.9陪	~=4
 *  
 *  Hessian比jdk更差
 *  Hessian2是kryo的2.5陪
 *  
 *  速度和空间上kryo都是最好的选择
 *  
 *  1.注册需要序列化的对象
 *  2.无参构造函数和Serializable接口
 * 
 * @author gaotingping
 *
 * 2016年7月29日 下午6:28:30
 */
@SuppressWarnings("unused")
public class Test1 {

	private Kryo kryo;
	
	private int maxLen=100000;
	
	@Before
	public void init(){
		kryo = new Kryo();
	    kryo.register(TestBean.class);//注册
	    kryo.register(TestSubBean.class);
		 
		//预热
		testJdk();
		testKryo();
		testfastJson();
		testHessian();
	}
	
	@Test
	public void testHessian() {
		for(int i=0;i<maxLen;i++){
			TestBean t=new TestBean();
			HessianSerializer j=new HessianSerializer();
			byte[] b = j.serialize(t);
			Object tmp = j.deserialize(b);
		}
	}

	@Test
	public void testfastJson() {
		for(int i=0;i<maxLen;i++){
			TestBean t=new TestBean();
			JSONSerializer j=new JSONSerializer();
			byte[] b = j.serialize(t);
			Object tmp = j.deserialize(b);
		}
	}

	@Test
	public void testKryo() {
		for(int i=0;i<maxLen;i++){
			TestBean t=new TestBean();
			KryoSerializer j=new KryoSerializer(kryo);
			byte[] b = j.serialize(t);
			Object tmp = j.deserialize(b);
		}
	}

	@Test
	public void testJdk() {
		for(int i=0;i<maxLen;i++){
			TestBean t=new TestBean();
			JavaSerializer j=new JavaSerializer();
			byte[] b = j.serialize(t);
			Object tmp = j.deserialize(b);
		}
	}
	
	@Test //无论从性能上还是序列化长度上，kryo都有优势
	public void test2(){
		
		long s = System.currentTimeMillis();
		testJdk();
		System.out.println("jdk耗时="+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		
		testKryo();
		System.out.println("kryo耗时="+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		
		testfastJson();
		System.out.println("fastJson耗时="+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		
		testHessian();
		System.out.println("Hessian耗时="+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
	}
	
	@Test
	public void test3(){
		for(int i=0;i<10;i++){
			System.out.println();
			test2();
		}
	}
	
	@Test
	public void test99(){
		TestBean t=new TestBean();
		JSONSerializer j=new JSONSerializer();
		byte[] b = j.serialize(t);
		Object tmp = j.deserialize(b);
	}
}
