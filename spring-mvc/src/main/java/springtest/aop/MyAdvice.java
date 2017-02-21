package springtest.aop;

/**
Spring提供了4种实现AOP的方式：
	1.经典的基于代理的AOP
	2.@AspectJ注解驱动的切面
	3.纯POJO切面
	4.注入式AspectJ切面
 */
public class MyAdvice {

	/**
	 * 经典AOP:
		Spring支持五种类型的通知：
		Before(前)  org.apringframework.aop.MethodBeforeAdvice
		After-returning(返回后) org.springframework.aop.AfterReturningAdvice
		After-throwing(抛出后) org.springframework.aop.ThrowsAdvice
		Arround(周围) org.aopaliance.intercept.MethodInterceptor
		Introduction(引入) org.springframework.aop.IntroductionInterceptor
		
		实现步骤：
			1.创建通知：实现这几个接口，把其中的方法实现了
			2.定义切点和通知者：在Spring配制文件中配置这些信息
			3.使用ProxyFactoryBean来生成代理
			
			切点：
			<bean id="spleepPointcut" class="org.springframework.aop.support.JdkRegexpMethodPointcut">
			  		<property name="pattern" value=".*sleep"/>
			</bean>
			
			结合切点和通知:切面
			<bean id="sleepHelperAdvisor" class="org.springframework.aop.support.DefaultPointcutAdvisor">
			     <property name="advice" ref="sleepHelper"/>
			     <property name="pointcut" ref="sleepPointcut"/>
			</bean>
			
			实例：
			<pre>
					<bean id="myAdvice" class="testmvc.advice.MyMethodInterceptor"></bean>
					<bean id="myPointcut" class="org.springframework.aop.support.JdkRegexpMethodPointcut">
						<property name="patterns">
					        <list>
					           <value>testmvc.controller.*</value>
					        </list>
					    </property>
					</bean>
					<bean id="aopTest" class="org.springframework.aop.support.DefaultPointcutAdvisor">
				     	<property name="advice" 	ref="myAdvice"/>
				     	<property name="pointcut" 	ref="myPointcut"/>
				     </bean>
			</pre>
			
			这个可以自动代理
			<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"/>
			
	 */
}
