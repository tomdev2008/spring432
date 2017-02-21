package com.mvw.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import com.mvw.beanpostprocessor.factory_bean.AutoFactoryBean;

/**
 * spring 容器后置处理器:可以接口提供代理实现，动态注入 允许自定义对ApplicationContext的 bean definitions
 * 进行修饰，扩展功能 以实现动态注入. 
 * 
 * 1、实现BeanFactoryPostProcessor 接口，会被Application contexts自动发现
 * 2、BeanFactoryPostProcessor 仅仅对 bean definitions 发生关系，不能对bean instances
 * 交互，对bean instances 的交互， 由BeanPostProcessor的实现来处理 
 * 
 * 3、PropertyResourceConfigurer是一个典型的实现
 * 
 * 如下演示特别典型的动态注入:扫描指定包下的接口，动态注入其代理对象
 * 
 * @author gaotingping
 *
 *         2016年8月11日 上午11:14:54
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

		// GenericApplicationContext DefaultListableBeanFactory
		DefaultListableBeanFactory acf = (DefaultListableBeanFactory) factory;

		/**
		 * 对于单个接口，还是用
		 * implements FactoryBean搞定，直接注入即可
		 */
		// /*
		// * 将对象封装为FactoryBean注入 这里动态注入的对象可以@auto引用
		// * 将AutoFactoryBean多次注册还是无效的，这个如何处理？
		// * 多个注入时，因为AutoFactoryBean是一个，会覆盖
		// */
		// BeanDefinition b = new RootBeanDefinition();
		// MutablePropertyValues m = b.getPropertyValues();
		// m.add("objectType", TestAuto.class);
		// b.setBeanClassName(AutoFactoryBean.class.getName());
		// acf.registerBeanDefinition("com.mvw.listener.TestAuto", b);

		/**
		 * 实际应用中:
		 *   1.约定大于配置,如已DAO结尾等
		 *   2.加注解精确标注
		 */
		try {

			String packageStr = "com.mvw.beanpostprocessor.auto_i";
			
			//classpath*:com/mvw/beanpostprocessor/auto_i/**/*.class
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath(packageStr) + RESOURCE_PATTERN;
			Resource[] resources = this.resourcePatternResolver.getResources(pattern);
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
			for (Resource r : resources) {
				
				/**
				 * 可以根据注解等过滤
				 * 具体参考 @see ScannerUtil
				 */

				MetadataReader metadataReader = readerFactory.getMetadataReader(r);

				ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(metadataReader);
				beanDefinition.setResource(r);
				beanDefinition.setSource(r);

				final String daoClassName = beanDefinition.getBeanClassName();

				MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
				propertyValues.addPropertyValue("objectType", daoClassName);
				// beanDefinition.setPropertyValues(propertyValues);
				beanDefinition.setBeanClass(AutoFactoryBean.class);

				acf.registerBeanDefinition(daoClassName, beanDefinition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String RESOURCE_PATTERN = "/**/*.class";
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
}
