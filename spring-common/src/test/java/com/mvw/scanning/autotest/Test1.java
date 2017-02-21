package com.mvw.scanning.autotest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.beanpostprocessor.auto_i.TestAuto;
import com.mvw.beanpostprocessor.auto_i.TestAuto2;
import com.mvw.beanpostprocessor.auto_i.TestAuto3;
import com.mvw.beanpostprocessor.auto_i.i2.TestAuto4;
import com.mvw.beanpostprocessor.auto_i2.TestAuto5;
import com.mvw.beanpostprocessor.auto_i2.TestAuto6;

/**
 * 这是一个很重大的突破
 * 
 * @author gaotingping
 *
 *         2016年11月21日 上午11:35:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class Test1 {

	@Autowired
	private TestAuto t1;

	@Autowired
	private TestAuto2 t2;

	@Autowired
	private TestAuto3 t3;

	@Autowired
	private TestAuto4 t4;

	/**
	 * 这又是一个伟大的突破
	 */
	@Autowired
	private TestAuto5 t5;
	
	@Autowired
	private TestAuto6 t6;

	@Test
	public void test2() {
		System.out.println(t5.test());
		System.out.println(t6.test());
	}

	@Test
	public void test1() {

		System.out.println(t1);
		t1.test();

		System.out.println(t2);
		t2.test();

		System.out.println(t3);
		t3.test();

		System.out.println(t4);
		t4.test();
	}
}
