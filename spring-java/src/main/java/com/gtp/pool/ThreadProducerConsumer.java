package com.gtp.pool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 是有一块缓冲区作为仓库，生产者可以将产品放入仓库，消费者则可以从仓库中取走产品
 * 
 * 实现方式 wait()/notify()方法 await()/signal()方法 BlockingQueue阻塞队列方法
 * PipedInputStream/PipedOutputStream
 * 
 * 1.单线程 2.多线程
 * 
 * @author gaotingping
 *
 *         2017年1月19日 下午3:02:40
 */
public class ThreadProducerConsumer {

	public static void main(String[] args) {

		// 仓库对象
		Storage storage = new StorageA();

		// 生产者对象
		new Producer(storage).start();
		new Producer(storage).start();

		// 消费者对象
		new Consumer(storage).start();
		new Consumer(storage).start();
	}
}

// await()/signal()方法
class StorageA implements Storage {
	
	private Boolean kc=false;

	private final Lock lock = new ReentrantLock();
	private final Condition full = lock.newCondition();
	private final Condition empty = lock.newCondition();

	public void produce() {
		
		while(true){
		
			lock.lock();
			
			System.out.println(Thread.currentThread().getName());
			if(kc){
				System.out.println("有-生产等待");
				try {
					full.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			kc=true;
			System.out.println("已-生成");
			empty.signalAll();
			
			lock.unlock();
		}
	}

	public void consume() {
		
		while(true){
		
		lock.lock();
		
		System.out.println(Thread.currentThread().getName());
		if(kc==false){
			System.out.println("无-消费等待");
			try {
				empty.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		kc=false;//改变了引用，就不能获得锁对象了
		System.out.println("已-消费");
		full.signalAll();
		
		lock.unlock();
		}
	}
}

// wait()/notify()方法
class StorageB implements Storage {

	private Boolean kc=false;

	private Object lock=new Object();
	
	public void produce() {
		
		synchronized (lock) {
			
			/*
			 *多线程
			 *	1.while()//因为醒来的时候，也可能被其它消费了，加while是正确的
			 *	2.唤醒时唤醒一个
			 */
			if(kc){
				System.out.println("生产-等待");
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			kc=true;
			System.out.println("生产");
			lock.notifyAll();
		}
	}

	public void consume() {
		
		synchronized (lock) {
			
			if(!kc){
				System.out.println("消费-等待");
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			kc=false;
			System.out.println("消费");
			lock.notifyAll();
		}
	}
}

interface Storage {

	void produce();

	void consume();

}

class Producer extends Thread { // 每次生产的产品数量

	// 所在放置的仓库
	private Storage storage;

	// 构造函数，设置仓库
	public Producer(Storage storage) {
		this.storage = storage;
	}

	// 线程run函数
	public void run() {
		produce();
	}

	// 调用仓库Storage的生产函数
	public void produce() {
		storage.produce();
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}
}

class Consumer extends Thread { // 每次消费的产品数量

	// 所在放置的仓库
	private Storage storage;

	// 构造函数，设置仓库
	public Consumer(Storage storage) {
		this.storage = storage;
	}

	// 线程run函数
	public void run() {
		consume();
	}

	// 调用仓库Storage的生产函数
	public void consume() {
		storage.consume();
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}
}