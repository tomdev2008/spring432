package com.gtp.juc;

/**
 * jdk juc包下为并发做了很多有用工具 1.大体结构(atomic原子操作，locks锁对象(可重入锁，读写锁)，常用并发集合) 2.常用类 3.原理
 * CAS等
 * 
 * 
 * 1.volatile volatile在多线程中是用来同步变量的。 线程为了提高效率，将某成员变量(如A)拷贝了一份（如B），
 * 线程中对A的访问其实访问的是B。只在某些动作时才进行A和B的同步。因此存在A和B不一致的情况。
 * volatile就是用来避免这种情况的。volatile告诉jvm， 它所修饰的变量不保留拷贝， 直接访问主内存中的（也就是上面说的A) 变量。
 * 
 * CountdownLatch: 一个线程(或者多个)，等待另外N个线程完成某个事情之后才能执行 CycliBarriar:
 * N个线程相互等待，任何一个线程完成之前，所有的线程都必须等待。
 * 
 * @author gaotingping
 *
 *         2017年1月19日 下午3:15:41
 */
public class JdkJucTest {

}
