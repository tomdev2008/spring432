<mvc:annotation-driven content-negotiation-manager="contentNegotiationManager"/>    
<bean id="contentNegotiationManager" class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">        
	<property name="favorPathExtension" value="false" />    
</bean>    
<bean id="jsonConverter" class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/><bean>      
<bean id="stringConverter" class="org.springframework.http.converter.StringHttpMessageConverter">          
	<property name="supportedMediaTypes">              
		<list>                  
			<value>text/plain;charset=UTF-8</value>             
		</list>          
	</property>      
</bean>     
<bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter">          
	<property name="messageConverters">              
		<list>                  
			<ref bean="stringConverter" />                  
			<ref bean="jsonConverter" />              
		</list>          
	</property>      
</bean> 


#spring 4.x支持jdk8,最低要求jdk6
#删除了弃用的包和方法
#要求servlet3.0 +

1)mvc
2)jdbc
3)文件
4)国际化
5)事务

#spring mvc
http://docs.spring.io/spring/docs/
http://docs.spring.io/spring/docs/4.2.1.RELEASE/spring-framework-reference/html/
http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/
http://velocity.apache.org/engine/devel/user-guide.html

人若不往高处走，灰尘会覆盖掉你！

#注意的问题，不建议用commons-logging
#官方推荐是用slf4j(Simple logging facade for Java)
#抽象层，可以有不同的实现，如log4j,logback
#SLF4J使你的代码独立于任意一个特定的日志API

#可靠，阅读性强，易维护
https://spring.io/guides

#4.x的特性
使用这个特性，我们可以开发REST服务的时候不需要使用@Controller而专门的@RestController。
当你实现一个RESTful web services的时候，response将一直通过response body发送。为了简化开发，
Spring 4.0提供了一个专门版本的controller。下面我们来看看@RestController实现的定义：

RestController =  Controller + ResponseBody

@Controller
@RequestMapping 
@RequestParam

#关于spring的上下文的概念
ContextLoaderListener  -->父上下文(SSH中是使用的)
DispatcherServlet      -->子上下文,对于spring的一般项目，只使用这个就足够了
关系：子上下文可以访问父上下文中的bean，但是父上下文不可以访问子上下文中的bean


Java--大项目能做好--按传统方式做，规规矩矩的做，好扩展，好维护。
Java--小项目能做快--按激进方式做，一周时间就可以出一个版本，先上线接受市场(用户)的反馈，再改进，再反馈，时间就是生命(成本)。
                  小项目上只用一个子容器，服务层直接实现类，不要借口。
                  
mvc各司其职:大项目必须

1.代码不能放任自流
2.c-s-d不允许跨层调用



方案一:传统型：
父上下文容器中保存数据源、服务层、DAO层、事务的Bean。
子上下文容器中保存Mvc相关的Action的Bean.
事务控制在服务层。
由于父上下文容器不能访问子上下文容器中内容，事务的Bean在父上下文容器中，无法访问子上下文容器中内容，就无法对子上下文容器中Action进行AOP（事务）。
当然，做为“传统型”方案，也没有必要这要做。

方案二：激进派
           全局只使用子上下文

<!-- 自己测试servlet -->
<!--
<servlet>
    <servlet-name>testServlet</servlet-name>
    <servlet-class>servlettest.TestServlet</servlet-class>
    <init-param>
        <param-name>123</param-name>
        <param-value>456</param-value>
    </init-param>
</servlet>
<servlet-mapping>
     <servlet-name>testServlet</servlet-name>
     <url-pattern>/svt/*</url-pattern>
</servlet-mapping>
<filter>
   <filter-name>testFilter</filter-name>
   <filter-class>servlettest.TestFilter</filter-class>
   <init-param>
        <param-name>123</param-name>
        <param-value>456</param-value>
    </init-param>
</filter>
<filter-mapping>
   <filter-name>testFilter</filter-name>
   <url-pattern>/fvt/*</url-pattern>
</filter-mapping>
<servlet>
   <servlet-name>delegatingServletProxy</servlet-name>
   <servlet-class>servlettest.DelegatingServletProxy</servlet-class>
   <init-param>
        <param-name>targetBean</param-name>
        <param-value>springServlet</param-value>
    </init-param>
</servlet>
<servlet-mapping>
       <servlet-name>delegatingServletProxy</servlet-name>
       <url-pattern>/py/*</url-pattern>
</servlet-mapping>
-->


#温故而知新
IOC:
	 BeanFactory
	 ApplicationContext(比较好的获得方式ApplicationContextAware)
   
	   应该做到看到任何实例代码都可以配置出来
	   自己实现bean的自动注入
	 <bean id="moreComplexObject" class="example.ComplexObject">
	    <!-- results in a setAdminEmails(java.util.Properties) call -->
	    <property name="adminEmails">
	        <props>
	            <prop key="administrator">administrator@example.org</prop>
	            <prop key="support">support@example.org</prop>
	            <prop key="development">development@example.org</prop>
	        </props>
	    </property>
	    <!-- results in a setSomeList(java.util.List) call -->
	    <property name="someList">
	        <list>
	            <value>a list element followed by a reference</value>
	            <ref bean="myDataSource" />
	        </list>
	    </property>
	    <!-- results in a setSomeMap(java.util.Map) call -->
	    <property name="someMap">
	        <map>
	            <entry key="an entry" value="just some string"/>
	            <entry key ="a ref" value-ref="myDataSource"/>
	        </map>
	    </property>
	    <!-- results in a setSomeSet(java.util.Set) call -->
	    <property name="someSet">
	        <set>
	            <value>just some string</value>
	            <ref bean="myDataSource" />
	        </set>
	    </property>
	 </bean>

     parent="parent" 在配置多个数据源时，比较有效，将公共的抽取为抽象父类
    
     default-lazy-init="true"  一般任意隐藏问题，启动时不能及时发现
     
     bean的活动范围：scope="singleton"  这是默认的  还有prototype，session等等
     
              创建和销毁  init destroy
     InitializingBean DisposableBean
     @PostConstruct and @PreDestroy
     BeanPostProcessor 
     
               指定全局的<beans default-init-method="init">初始化方法
     default-destroy-method
     
	 有三种控制方法：
	     InitializingBean and DisposableBean
	     init() and destroy() methods
	     @PostConstruct and @PreDestroy
	     
	注册一个关闭钩子：
		ConfigurableApplicationContext ctx = null;
		ctx.registerShutdownHook();
     
	    @Autowired+@Qualifier按照id注入
	    
	    @Resource 按照id或名称来

	    @Component, @Service, and @Controller. @Component
	    
	    @RestController=@Controller and @ResponseBody
	    
	    <context:component-scan>   一般是action层放spring mvc 其它层放spring
	        <context:annotation-config>
	    </xxx>  				   可做定制过滤
	    
		@Configuration
		@ImportResource("classpath:/com/acme/properties-config.xml")
		public class AppConfig {
		
		    @Value("${jdbc.url}")
		    private String url;
		}
		
		#配置文件
		profile
		
		#监听系统的状态:ApplicationListener
		
	    
		自动创建app容器：
		<context-param>
		    <param-name>contextConfigLocation</param-name>
		    <param-value>/WEB-INF/daoContext.xml /WEB-INF/applicationContext.xml</param-value>
		</context-param>
		
		<listener>
		    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
		</listener>
		
		有默认的 /WEB-INF/applicationContext.xml
		
		对spring来说，ApplicationContext是个至关重要的东西	    