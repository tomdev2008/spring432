package com.mvw.scanning.test;

import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvw.scanning.ScannerUtil;
import com.mvw.scanning.annotation.ServiceCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-scan.xml"})
public class Test1 {

	@Autowired
	private ScannerUtil scanner;
	
	/**
	 * 想法就是将主要类都解析出来
	 * 缓存在map中，再根据方面编码来进行
	 * 获取通过反射执行
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception{
		
		Set<Class<?>> list = scanner.getEntry();
		
		for (Class<?> clazz : list) {
			for (Method method : clazz.getDeclaredMethods()) {
				ServiceCode serviceCode = method.getAnnotation(ServiceCode.class);
				if(serviceCode!=null){
					/**
					 * 有标注的方法全部缓存
					 * 包括类或方法
					 */
					System.out.println(method.getDeclaringClass());
					//flyweights.put(serviceCode.value(), applicationContext.getBean(method.getDeclaringClass()));
					System.out.println(serviceCode.value()+"="+method);
				}
			}
		}
	}
	
}
