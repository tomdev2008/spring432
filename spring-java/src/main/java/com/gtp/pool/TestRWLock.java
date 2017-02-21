package com.gtp.pool;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁:适合多读少写的共享资源
 * 
 * @author gaotingping
 *
 * 2016年11月25日 上午11:03:53
 */
public class TestRWLock {
	
	/*
	 * synchronized
	 *    1)太死板，等待无法中断等
	 *    2)读写无法分别对待
	 *    
	 *    lock
	 *    1)比synchronized有更多的功能
	 *    2)自己管理锁，使用不当如没有释放锁等问题
	 *    使用Lock必须在try{}catch{}块中进行，并且将释放锁的操作放在finally块中进行
	 *    
			lock()
			lockInterruptibly()  
				当两个线程同时通过lock.lockInterruptibly()想获取某个锁时，假若此时线程A获取到了锁，而线程B只有在等待
				那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程
			
			tryLock()//尝试获取锁
			tryLock(long, TimeUnit)
			unlock()
			newCondition()
			
			
			实现类：
			   　　lock
			      |-- ReentrantLock
			   
			注意:局部变量每个方法会保存一个副本
			
			
			　　ReadWriteLock  读写分离，单写多读
							|--- ReentrantReadWriteLock (readLock()和writeLock()用来获取读锁和写锁)
							
							
	  选择建议：
		   Lock和synchronized的选择
		　　总结来说，Lock和synchronized有以下几点不同：
		
		　　1）Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；
		
		　　2）synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，
			   如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
		
		　　3）Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；
		
		　　4）通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
		
		　　5）Lock可以提高多个线程进行读操作的效率。
		
		　　在性能上来说，如果竞争资源不激烈，两者的性能是差不多的，而当竞争资源非常激烈时（即有大量线程同时竞争），
		    此时Lock的性能要远远优于synchronized。所以说，在具体使用时要根据适当情况选择。	
		
						
	锁的可重入性：
	  	分配机制：基于线程的分配，而不是基于方法调用的分配。举个简单的例子，当一个线程执行到某个synchronized方法时，比如说method1，
	  	而在method1中会调用另外一个synchronized方法method2，此时线程不必重新去申请锁，而是可以直接执行方法method2。
	  		
	  					
	公平锁：
	       * 非公平性能大于公平
	       * 原因：在恢复一个被挂起的线程与该线程真正运行之间存在着严重的延迟
	       *     假设线程A持有一个锁，并且线程B请求这个锁。由于锁被A持有，因此B将被挂起。
	       *     当A释放锁时，B将被唤醒，因此B会再次尝试获取这个锁。与此同时，如果线程C也请求这个锁，
	       *     那么C很可能会在B被完全唤醒之前获得、使用以及释放这个锁。这样就是一种双赢的局面：
	       *     B获得锁的时刻并没有推迟，C更早的获得了锁
	        
	        公平锁即尽量以请求锁的顺序来获取锁  	
		        在Java中，synchronized就是非公平锁，它无法保证等待的线程获取锁的顺序。
	　　            而对于ReentrantLock和ReentrantReadWriteLock，它默认情况下是非公平锁，但是可以设置为公平锁	
	
		   //fair true if this lock should use a fair ordering policy
		   ReentrantLock lock = new ReentrantLock(true);
		   
		   　另外在ReentrantLock类中定义了很多方法，比如：
			　　isFair()        			  //判断锁是否是公平锁			
			　　isLocked()    			 //判断锁是否被任何线程获取了			
			　　isHeldByCurrentThread()   //判断锁是否被当前线程获取了			
			　　hasQueuedThreads()   		//判断是否有线程在等待该锁
	 */
	/*
	 * synchronized:串行(阻塞)
	 * 
	 * 可重入锁：
	 *    ReentrantLock 实现了标准的互斥操作，也就是一次只能有一个线程持有锁，也即所谓独占锁的概念。
	 *    可重入的解释:它有一个与锁相关的获取计数器，如果拥有锁的某个线程再次得到锁，那么获取计数器就加1，
	 *    然后锁需要被释放两次才能获得真正释放(性能更好，争用处理更好)，可选公平与否
	 *    新特性:比如时间锁等候、可中断锁等候、无块结构锁、多个条件变量或者锁投票
	 *    
	 *    模版代码:
	 *      Lock lock = new ReentrantLock();
			lock.lock();
			try { 
			  // update object state
			}
			finally {
			  lock.unlock(); //这特别重要，必须自己释放锁
			}
	
	 *    
	 *
	 * 读写锁ReentrantReadWriteLock
	 * ReentrantReadWriteLock实现了ReadWriteLock接口，并没
	 * 有实现Lock接口，是其内部类ReadLock和WriteLock实现了Lock的接口
	 * 适合：某些资源需要并发访问，并且大部分时间是用来进行读操作的，写操作比较少(读写分离)
	 * ReadWriteLock描述的是：一个资源能够被多个读线程访问，或者被一个写线程访问，但是
	 * 不能同时存在读写线程，读写锁使用的场合是一个共享资源被大量读取操作，而只有少量的写操作
	 */
	class Queue3{
		//共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
	    private Object data = null;
	    
	    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	    
	    public void get(){
	        rwl.readLock().lock();//上读锁
	        try {
	        	
	        	System.out.println(Thread.currentThread().getName() + "去读数据"+data);
	            Thread.sleep((long)(Math.random()*1000));
	            System.out.println(Thread.currentThread().getName() + "已读数据"+data); 
	            
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }finally{
	        	rwl.readLock().unlock(); //释放读锁
	        }     
	    }
	    
	    public void put(Object data){

	        rwl.writeLock().lock();//上写锁，不允许其他线程读也不允许写
	                  
	        try {
	        	
	        	System.out.println(Thread.currentThread().getName() + "去写数据"+data);
	            Thread.sleep((long)(Math.random()*1000));
	            this.data = data;        
	            System.out.println(Thread.currentThread().getName() + "已写数据"+data);   
	            
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }finally{
	        	 rwl.writeLock().unlock();//释放写锁   
	        }  
	    }
	}
}
