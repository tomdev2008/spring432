package com.mvw.jdk7;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;

public class TestConcurrentLinkedQueue {

	@Test
	public void test1(){
		
		//并发 单向链表
		ConcurrentLinkedQueue<String> cl=new ConcurrentLinkedQueue<String>();
		
		//cl.poll();//出
		//cl.peek();//看
		cl.offer("s1");//入:add()实际上是调用的offer()来完成添加操作的,，插入到队列尾部
		cl.offer("s2");
		cl.offer("s3");
		
		System.out.println(cl.poll());
		cl.offer("s4");
		cl.poll();//头部   FIFO
		
		show(cl);
	}
	
	public void show(ConcurrentLinkedQueue<String> cl){
		System.out.println("==========================");
		Iterator<String> it = cl.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
}
