package com.mvw.my_appender;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test1 {

	private   Logger logger=LoggerFactory.getLogger(Test1.class);
	
	@Test
	public void test1(){
		logger.error("哈哈哈哈",1111);
		logger.error("哈哈哈{}",123);
		logger.error("哈哈哈",new RuntimeException("123/456"));
	}
}
