package com.gtp.juc;

public class TestCollection {

	/*
	    ConcurrentHashMap	-->Segment(分段锁)
	    ConcurrentLinkedQueue	-->常用
	    ConcurrentSkipListMap	-->代TreeMap
	    CopyOnWriteArrayList	-->代ArrayList，适合读多写少的场景。通过空间换时间的方式来提高读的效率并保证写的安全性
	    LinkedBlockingQueue		
	    PriorityBlockingQueue
	    SynchronousQueue
	    
	    #hashMap
	    ConcurrentHashMap通过N个Segment将数据切分成N块，而每块之间是互不影响的，所以
	            理论上可以同时并行的执行N个需要加锁的操作，这就是ConcurrentHashMap并发的基础
	 */
}
