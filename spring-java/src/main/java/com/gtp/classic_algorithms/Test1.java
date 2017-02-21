package com.gtp.classic_algorithms;

import org.junit.Test;

public class Test1 {

	@Test
	public void test1(){
		/**
		 * 兔子问题
		 * 有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第四个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
		   
		        程序分析：兔子的规律为数列1,1,2,3,5,8,13,21.... (这个问题的关键是程序分析，从描述中找出规律，抽象出算法)
		     
		        规律是:从第三个数起，后面的数是前2个数之和
		   function  tz(n){
		          if(n<3){
		               return 1;
		          }else{
		             return tz(n-1)+tz(n-2);
		          }
		   }
		 */
	}
	
	@Test
	public void test2(){
		/**
		 * 素数的定义
		 * 判断素数的方法：用一个数分别去除2到sqrt(这个数)，如果能被整除，则表明此数不是素数，反之是素数
		 */
	}
	
	@Test
	public void test3(){
		/**
		 * 水仙花数
		 * 核心:取一个数的个位数
		 * i = x/100;		#百
		   j = (x%100)/10;  #十
		   k = x%10;        #个
		 */
	}
	
	@Test
	public void test4(){
		/**
		 * (a>b)?a:b这是条件运算符,可以嵌套
		 */
	}
	
	@Test
	public void test5(){
		/**
		 * 输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数
		 * 算法:str.toCharArray(); 用char判断即可
		 */
	}
	
	@Test
	public void test6(){
		/**
		 * 求s = a + aa + aaa + aaaa + aa...a的值，其中a是一个数字。例如2 + 22 + 222 + 2222 + 22222(此时共有5个数相加)，几个
		 */
		
		int a=2;
		int n = 5;

		int s = 0,t=0;

		for (int i = 1; i <= n; i++) {
			t += a;
			a = a*10;//临时因子，和球每次落高一半一个意思
			s += t;
		}

		System.out.println(s); 
	}
	
	@Test
	public void test7(){
		/**
		 *  Scanner in=new Scanner(System.in);
			System.out.println("输入年：");
			year=in.nextInt(); 
			
			闰年
			if(year%400==0||year%4==0 && year%100!=0){
				days=29;
			}else{
				days=28;
			} 
		 */
	}
	
}
