package com.mvw.beanpostprocessor;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * spring bean后置处理器
 * 
 * @author gaotingping
 *
 * 2016年8月3日 下午5:15:34
 */
public class MyBeanPostProcessor implements BeanPostProcessor{
	
	private Pattern   pattern   =   Pattern.compile("\\w*Controller");      

	@Override
	// Bean 实例化之前执行该方法,因为实例化之后返回的就是个代理句柄，无法操作
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		
		 /*处理所有控制器类*/
		 Matcher m = pattern.matcher(beanName);
			if (beanName != null &&  m.matches()) {
				System.out.println(beanName + ":" + bean);
				initializeLog(bean, bean.getClass());
			}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//放这里不对，因为返回的是一个代理对象,获取不到真实的field		
		return bean;
	}

	//实现对注入了AutoLogger注解的类，自动注入org.slf4j.Logger对象
	private void initializeLog(Object bean, Class<? extends Object> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(AutoLogger.class) != null) {
				//私有可访问
				field.setAccessible(true);
				try {
					field.set(bean,LoggerFactory.getLogger(clazz));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
