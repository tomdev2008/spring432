package org.spring.dubbo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:application-consumer.xml"})
public class TestClient extends AbstractJUnit4SpringContextTests {

	/*自动注入远程接口*/
	@Autowired
	IService iService;
	
	@Test
	public void test1() throws Exception{
		int i=0;
		while(i<10){
			i++;
			System.out.println("client:" + iService.sayHello("hello"));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.in.read(); 
	}
}
