package com.gtp.pool;

/**
 * 温故而知新
 * 
 * 1.线程是指进程内的一个执行单元,也是进程内的可调度实体
 * 2.java虚拟机采用抢占式调度模型
 * 
 * @author gaotingping
 *
 * 2017年1月19日 上午10:35:43
 */
public class ThreadDefine {

	public static void main(String[] args) {
		
		testStart();
	}

	public static void testStart() {
		Thread t1=new TestExtThread();
		t1.start();
		
		Thread t2 = new Thread(new TestImplThread());
		t2.start();
	}
}

/*
 * 内部关系
 * Thread implements Runnable
 */
class TestExtThread extends Thread{
	
	@Override
	public void run() {
		super.run();
		System.out.println("线程1");
	}
	
}

class TestImplThread implements Runnable {
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
				System.out.println("线程2");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}