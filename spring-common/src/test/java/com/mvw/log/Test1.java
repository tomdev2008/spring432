package com.mvw.log;

import java.text.MessageFormat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test1 {
	
	private static Logger log = LoggerFactory.getLogger(Test1.class);
	
	public static void main(String[] args) {
		
		log.trace("======trace");
		
		log.debug("======debug");
		
		log.info("======info");
		
		log.warn("======warn");
		
		log.error("======error");
        //String name = "Aub";
		//String message = "3Q";
		//String[] fruits = { "apple", "banana" };
		// logback提供的可以使用变量的打印方式，结果为"Hello,Aub!"
		//log.info("Hello,{}!", name);
		// 可以有多个参数,结果为“Hello,Aub! 3Q!”
		//log.info("Hello,{}!   {}!", name, message);
		// 可以传入一个数组，结果为"Fruit:  apple,banana"
		//log.info("Fruit:  {},{}", fruits); 
	}
	
	@Test
	public void test1(){
		System.out.println(MessageFormat.format("该域名{0}被访问了 {1} 次.", 12 , 13));
	}
}
