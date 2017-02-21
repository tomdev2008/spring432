package org.spring.smalltools.juc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

/*
 * 结构
 * 1.atomic 原子
 * 2.locks
 * 3.support
 * 
 * 2^16次方的限制
 *    1.65535一般是内存地址最大值：1×1111111
 *    2.64k
 *    
 *    二进制：11111111,11111111
	  65535是计算机16位二进制最大数
      65535B=64KB
 */
public class TestMap {

	@Test
	public void test1(){
		/*
		 * 原理:
		 * 1.volatile CAS
		 * 2.Segment
		 */
		Map<String,String> m = new ConcurrentHashMap<String,String>(
				100, //初始化空间，16
				0.75f, //加载因子,0.75f
				5);//并发预估，决定内部分片多少，最大值是 1>>16 65536   默认16
		
		m.get(123);
	}
	
	public static void main(String[] args) {
		System.out.println(1 << 16);
	}
	
//    private int hash(Object k) {
//        int h = hashSeed;
//
//        if ((0 != h) && (k instanceof String)) {
//            return sun.misc.Hashing.stringHash32((String) k);
//        }
//
//        h ^= k.hashCode();
//
//        // Spread bits to regularize both segment and index locations,
//        // using variant of single-word Wang/Jenkins hash.
//        h += (h <<  15) ^ 0xffffcd7d;
//        h ^= (h >>> 10);
//        h += (h <<   3);
//        h ^= (h >>>  6);
//        h += (h <<   2) + (h << 14);
//        return h ^ (h >>> 16);
//    }
//    static final boolean ALTERNATIVE_HASHING;
//    
//    private transient final int hashSeed = randomHashSeed(this);
//
//    private static int randomHashSeed(ConcurrentHashMap instance) {
//        if (sun.misc.VM.isBooted() && Holder.ALTERNATIVE_HASHING) {
//            return sun.misc.Hashing.randomHashSeed(instance);
//        }
//
//        return 0;
//    }
}
