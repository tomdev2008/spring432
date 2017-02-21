package org.spring.smalltools.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 查找问题
 * 
 * @author gaotingping
 *
 * 2017年1月4日 下午5:33:50
 */
public class Test2 {

	private static int[] a = new int[] { 1, 3, 4, 5, 7, 8 };

	@Before
	public void init() {

	}

	@After
	public void close() {
		System.out.println();
		System.out.println("=======a=========");
		for (Integer i : a) {
			System.out.print(i + "  ");
		}
	}

	@Test //二分查找 有序集合查找
	public void test1() {
		
		System.out.println(binaryFind());
		
		System.out.println(binaryRecursiveFind(a, 0, a.length, 5));
	}
	
	
	public static int binaryFind() {

		int searchKey = 5;// 找5

		int lowerBound = 0;
		int upperBound = a.length - 1;
		int curIn = 0;// 当前索引

		while (lowerBound <= upperBound) {

			curIn = (lowerBound + upperBound) >> 1;//取中间位置

			if (a[curIn] == searchKey) {//找到了
				
				return curIn;
				
			} else if (a[curIn] < searchKey) {//在后半截
				
				lowerBound = curIn + 1;
				
			} else {
				
				upperBound = curIn - 1;//在前半截
				
			}

		}

		return -1;
	}
	
	//这个也适合玩递归
	private static int binaryRecursiveFind(int[] arr,int start,int end,int searchKey){
        if (start <= end) {
            // 中间位置
            int middle = (start + end) >> 1; // (start+end)/2
            if (searchKey == arr[middle]) {
                // 等于中值直接返回
                return middle;
            } else if (searchKey < arr[middle]) {
                // 小于中值时在中值前面找
                return binaryRecursiveFind(arr, start, middle - 1, searchKey);
            } else {
                // 大于中值在中值后面找
                return binaryRecursiveFind(arr, middle + 1, end, searchKey);
            }
        } else {
            // 找不到
            return -1;
        }
    }

}