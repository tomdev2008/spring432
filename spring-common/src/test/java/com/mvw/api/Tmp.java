/**
 * 
 */
/**
 * @author gaotingping
 *
 * 2016年8月4日 下午2:22:43
 */
package com.mvw.api;

import com.alibaba.fastjson.JSON;

/**
 * 搞api的规范
 * 
 * 1.输入   PO
 * 
 * 2.输出   VO
 * 
 * 3.传递   DTO
 * 
 * 4.持久  DO
 */
public class Tmp{
	public static void main(String[] args) {
		
		ParamB b=new ParamB();
		
		ParamA a=new ParamA();
		a.name="名称";
		
		b.a=a;
		b.age=123;
		
		String s=JSON.toJSONString(b);
		
		b=JSON.parseObject(s,ParamB.class);
		System.out.println(b);
	}
}

class ParamA{
	public String name;
}

class ParamB{
	public int age;
	
	public ParamA a;
}