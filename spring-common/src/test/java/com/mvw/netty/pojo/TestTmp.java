package com.mvw.netty.pojo;

import org.junit.Test;

public class TestTmp {

	@Test
	public void test1(){
		byte[] b = KryoUtil.serialize(new BeanDTO());
		System.out.println(b.length);
	}
}
