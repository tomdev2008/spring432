package springtest.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * ApplicationEvent 可以监听到servlet的处理 ApplicationContextEvent
 * 注意父子上下文的影响
 */
public class MyApplicationListener implements ApplicationListener<ApplicationContextEvent>, ApplicationContextAware {

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		System.out.println("==============1==================");
		System.out.println(event);
		/*
		 * 容器启动完成或关闭
		 * ContextRefreshedEvent
		 * ContextClosedEvent
		 * 
		 * ConfigurableApplicationContext方法触发 
		 * ContextStartedEvent
		 * ContextStoppedEvent
		 */
		System.out.println("===============1=================");
	}

	@Override
	public void setApplicationContext(ApplicationContext app) throws BeansException {

		System.out.println("=================2===============");
		System.out.println(app);
	}
}
