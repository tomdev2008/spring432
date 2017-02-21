package jadetest;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


//ResourceLoaderAware
public class TestResource {

	@Test
	public void test1(){
		 ResourceLoader loader = new DefaultResourceLoader();
		 Resource resource = loader.getResource("classpath:config.properties");
		 System.out.println(resource);
	}
}
