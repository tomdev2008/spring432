package com.mvw.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

public class MyApplicationListener implements ApplicationListener<ApplicationContextEvent>,ApplicationContextAware{

	//private ApplicationContext applicationContext;
	
	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		System.out.println("==============1==================");
		System.out.println(event);
		/*
			ContextClosedEvent
			ContextRefreshedEvent
			
			//ConfigurableApplicationContext方法触发
			ContextStartedEvent
			ContextStoppedEvent 
		 */
		//ContextRefreshedEvent
		//ContextClosedEvent
		
		if(event instanceof ContextRefreshedEvent){
			System.out.println("启动完成");
		}
		
		System.out.println("===============1=================");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		System.out.println("=================2===============");
		//this.applicationContext=applicationContext;
		System.out.println(applicationContext);
		
		 System.out.println("================2================");
	}

}
