package com.gtp.pool;

/**
 * 温故而知新
 * 
 * @author gaotingping
 *
 *         2017年1月19日 上午10:35:43
 */

/*
 * 总之，notify(), wait()依赖于“同步锁”，而“同步锁”是对象锁持有，
 * 并且每个对象有且仅有一个！这就是为什么notify(), wait()
 * 等函数定义在Object类，而不是Thread类中的原因
 */

public class ThreadOperation {

	public static void main(String[] args) throws Exception {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (this) {
					System.out.println(Thread.currentThread().getName() + " call notify()");
					// 唤醒当前的wait线程
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					notify();//当前线程被唤醒(进入“就绪状态”)
					//唤醒在此"对象监视器"上等待的单个线程
					System.out.println(Thread.currentThread().getName() + " end t1");
				}
			}
		});

		synchronized (t1) {
			try {
				// 启动“线程t1”
				System.out.println(Thread.currentThread().getName() + " start t1");
				t1.start();
				System.out.println(Thread.currentThread().getName() + " start join");
				//t1.join();//异步执行的线程变同步，使用join后直到这个线程退出，程序才会往下执行

				// 主线程等待t1通过notify()唤醒。
				System.out.println(Thread.currentThread().getName() + " wait()");
				t1.wait();//why 主线程等待了？？ 引起“当前线程”等待，直到另外一个线程调用notify()或notifyAll()唤醒该线程
				//而“当前线程”是指正在cpu上运行的线程

				System.out.println(Thread.currentThread().getName() + " continue");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 这是一个非常糟糕的东西
	 * 
	 * 需求: 1.实现一个线程每秒输入一个字符串 2.过三秒后进入wait 5.过5秒后notify
	 */
	public static void testWait() throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						System.out.println("线程2");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		t1.start();// 启动
	}

	/*
	 * 方法回顾 1.Thread.sleep()/sleep(long millis)
	 * 1)millis指定睡眠时间是其最小的不执行时间，因为sleep(millis)休眠到达后，无法保证会被JVM立即调度
	 * 2)线程sleep()时不会失去拥有的对象锁
	 * 
	 * 2.object.wait() 1)进入到一个和该对象相关的等待池(Waiting Pool) 2)失去了对象的锁
	 * 3)IllegalMonitorStateException 当前线程不是此锁的拥有者
	 * 
	 * 3.object.notify()/notifyAll() 1)唤醒在当前对象等待池中等待的第一个线程/所有线程
	 * 2)IllegalMonitorStateException 当前线程不是此锁的拥有者
	 * 
	 * 4.JVM基于多线程，默认情况下不能保证运行时线程的时序性
	 * 
	 * 5.join
	 * 等待该方法的线程执行完毕后再往下继续执行
	 * 
	 * 6.协调
	 * condition提供三个方法来达到同步
		await(),类似wait()
		sginal() 类似notify
		signalAll()类似notifyAll
	 */
}