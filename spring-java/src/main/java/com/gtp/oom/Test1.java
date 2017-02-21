package com.gtp.oom;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

/*
 * #单线程所有
 * 程序计数器: 英文名字是 Program Counter Register，简称PC, 用于记录每个线程执行到哪个地方了(可以认为程序是指令的序列,
 *          PC记录着下一条(或本条)应该执行的指令的地址).
   JVM方法栈:  包含如下面的图所示的一些栈帧(frame).
         本地方法栈: 英文为 Native Method Stack,顾名思义,适用于供 native方法使用的内存空间,本地方法(native method)
         指不用Java语言开发的方法,如 C,C++,PB等编译型语言所开发的动态链接库(中的方法).

 * #共享
 * 堆内存: 英文名称 Heap,是Java编程最频繁使用的内存区域. 其中存储着 数组和对象,当 JVM启动时即创建此内存区域, 垃圾回收主要是指堆内存的回收。
         方法区(永久代): Method Area,存储了 运行时常量池,类结构信息(field and method data) 以及 方法,构造器的代码.
         运行时常量池: Runtime Constant Pool, 每一个类或者接口,在其编译后生成的 .class文件中,有一个部分叫做 常量表(constant_pool_table),
         JVM将class文件加载以后,就解析常量表的内容到运行时常量池. 包括编译时方法中就明确可知的数字值,String值, 以及必须在运行时解析的属性
         域引用(field reference).
 */
public class Test1 {

	/**
	 * 默认情况下java为每个线程分配的栈内存大小是1M，通常情况下，这1M的栈内存空间是足足够用了，因为在通常在栈上存放的
	 * 只是基础类型的数据或者对象的引用，这些东西都不会占据太大的内存， 我们可以通过调整jvm参数，降低为每个线程分配的栈内
	 * 存大小来解决问题，例如在jvm参数中添加-Xss128k将线程栈内存大小设置为128k
	 */
	@Test
	public void test1() {
		/*
		 * java.lang.StackOverflowError 1024次 就溢出了
		 */
		new Test1().stackOverFlowMethod();
	}

	public void stackOverFlowMethod() {
		stackOverFlowMethod();
	}

	/**
	 * 配置参数配合测试
	 * java -verbose:gc -Xmn10M -Xms20M -Xmx20M -XX:+PrintGC OOMTest
	 */
	@Test
	public void test2(){
		/*
		 * java.lang.OutOfMemoryError:java heap space
		 */
		 List<byte[]> buffer = new ArrayList<byte[]>();
		 while(true){
			 buffer.add(new byte[10*1024*1024]);
		 }
	}
	
	/**
	 * 方法区是jvm的一种规范
	 * 而永久代是Hotspot虚拟机特有的概念，是方法区的一种实现
	 * 
	 * jdk8的时候，这里都没有永久代了，取尔代之是元空间(本地内存)
	 * java -verbose:gc -Xmn5M -Xms10M -Xmx10M -XX:MaxPermSize=1M -XX:+PrintGC OOMTest
	 */
	@Test //运行时常量让方法区溢出
	public void test3(){
		/*
		 * OutOfMemoryError: PermGen space
		 * 再热部署情况容易遇到！
		 */
		List<String> list = new ArrayList<String>();
        while(true){
                list.add(UUID.randomUUID().toString().intern());
        }
        
        /*
		    static class MethodAreaOomObject {
		    }
		    public static void main(String[] args) {
		        while(true){
		            Enhancer enhancer = new Enhancer();
		            enhancer.setSuperclass(MethodAreaOomObject.class);
		            enhancer.setUseCache(false);
		            enhancer.setCallback(new MethodInterceptor() {
		                public Object intercept(Object obj, Method method, Object[] args,
		                        MethodProxy proxy) throws Throwable {
		                    return proxy.invoke(obj, args);
		                }
		            });
		            enhancer.create();
		        }
		    }
         */
	}
	
	@Test
	public void test4(){
		/*
		 * OutOfMemoryError:unable to create native thread
		 * 程序创建的线程数超过了操作系统的限制。对于Linux系统，我们可以通过ulimit -u来查看此限制。
		 * 给虚拟机分配的内存过大，导致创建线程的时候需要的native内存太少
		 * 
		 * 可以采取的方案
		 * 1.增大进程所占用的总内存
		 * 2.或者减少-Xmx或者-Xss来达到创建更多线程的目的
		 */
	}
	
	@Test
	public void test5(){
		/**
		 * 造一个方法区溢出
		 */
		
	}
}
