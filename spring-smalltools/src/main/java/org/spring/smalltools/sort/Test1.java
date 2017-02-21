package org.spring.smalltools.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 1.常见的
 * 2.概念 稳定性:
 * 假定在待排序的记录序列中，存在多个具有相同的关键字的记录，若经过排序，这些记录的相对次序保持不变，即在原序列中，ri=rj，且ri在rj之前，
 * 而在排序后的序列中，ri仍在rj之前，则称这种排序算法是稳定的；否则称为不稳定的
 * 3.Ο(1)＜Ο(log2n)＜Ο(n)＜Ο(nlog2n)＜Ο(n2)＜Ο(n3)＜…＜Ο(2n)＜Ο(n!)
 * 
 * 4.equals hasCode重写原则
 *   1、如果两个对象相同（即用equals比较返回true），那么它们的hashCode值一定要相同；
     2、如果两个对象的hashCode相同，它们并不一定相同(即用equals比较返回false)   
              即：equal为true，hashCode必须为true，equal为false，hashCode也必须 为false
              
               在java的集合中，判断两个对象是否相等的规则是：

	1，判断两个对象的hashCode是否相等
	2，判断两个对象用equals运算是否相等
	
? 对称性：如果x.equals(y)返回是“true”，那么y.equals(x)也应该返回是“true”。

? 反射性：x.equals(x)必须返回是“true”。

? 类推性：如果x.equals(y)返回是“true”，而且y.equals(z)返回是“true”，那么z.equals(x)也应该返回是“true”。

? 还有一致性：如果x.equals(y)返回是“true”，只要x和y内容一直不变，不管你重复x.equals(y)多少次，返回都是“true”。

? 任何情况下，x.equals(null)，永远返回是“false”；x.equals(和x不同类型的对象)永远返回是“false”。 
 * 
 * @author gaotingping
 *
 *         2017年1月4日 下午4:01:09
 */
public class Test1 {

	private static  List<Integer> list = null;

	private static Integer[] a = new Integer[] { 1, 3, 8, 7, 6, 5, 4 };

	@Before
	public void init() {
		list = new ArrayList<>();
		list.add(1);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(7);
		list.add(9);
	}

	@After
	public void close() {
		System.out.println("=======list=========");
		for (Integer i : list) {
			System.out.print(i + "  ");
		}
		System.out.println();
		System.out.println("=======a=========");
		for (Integer i : a) {
			System.out.print(i + "  ");
		}
	}

	/**
	 * 冒泡排序 就是把小的元素往前调或者把大的元素往后调。比较是相邻的两个元素比较，交换也发生在这两个元素之间。
	 * 我们知道，冒泡排序的交换条件是：a[j]>a[j+1]或者a[j]<a[j+1]很明显不包括相等的情况，
	 * 所以如果两个元素相等，他们不会交换；如果两个相等的元素没有相邻，那么即使通过前面的两两交换
	 * 把两个相邻起来，这时候也不会交换，所以相同元素的前后顺序不会改变，所以冒泡排序是一种稳定排序算法。
	 * 
	 * O(N^2) O(1)
	 */
	@Test
	public void bubbleSort() {

		int n = a.length;

		//升序
		for (int i = 0; i < n; i++) {
			//每次提取一个"最"的
			for (int j = i+1; j < n; j++) {
				if (a[i] > a[j]) { //大的往后站
					int temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
	}

	@Test // 排序
	public void collectionsSort() {

		// 一个负整数、零或正整数的第一个参数小于,等于,或大于第二 (归并排序)

		/*
		 * 所谓“归并”，试讲两个或两个以上的有序文件合并成一个新的有序文件。 归并排序是把一个有n个记录的无序文件看成是由n个长度为1的有序子文件
		 * 组成的文件，然后进行两两归并，得到[n/2]个长度为2或1的有序文件，再两
		 * 两归并，如此重复，直至最后形成包含n个记录的有序文件为止。所以，归并排 序也是稳定的排序算法。
		 * 
		 * 时间复杂度:>O(n)
		 */
		Collections.sort(list, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				System.out.println(o1 + "->" + o2);
				// return o1-02;//升序
				return -1;// 降序
			}

		});
	}
}