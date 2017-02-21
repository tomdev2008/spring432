package com.mvw.beanpostprocessor.factory_bean;

import org.springframework.beans.factory.FactoryBean;

import com.mvw.beanpostprocessor.auto_i.TestAuto;
import com.mvw.beanpostprocessor.auto_i.TestAuto2;
import com.mvw.beanpostprocessor.auto_i.TestAuto3;
import com.mvw.beanpostprocessor.auto_i.i2.TestAuto4;
import com.mvw.proxy.support.ProxyJdk;

/**
 * BeanFactory定义了 IOC 容器的最基本形式，并提供了 IOC 容器应遵守的的最基本的
 * 接口，也就是 Spring IOC 所遵守的最底层和最基本的编程规范，如:ApplicationContext
 * 
 * FactoryBean 的工厂类接口，用户可以通过实现该接口定制实例化 Bean 的逻辑,提供一种
 * 复杂对象初始化的扩展方式。“动态注入，占用很重要的位置”。
 */
public class AutoFactoryBean implements FactoryBean<Object>{

	private String id;
	
	protected Class<?> objectType;/*类型通过外部注入*/
	
	@Override
	public Object getObject() throws Exception {
		return createObj();
	}

	private Object createObj() throws Exception{
		if(objectType == TestAuto.class){
			
			TestAuto obj = new TestAuto() {
				@Override
				public void test() {
					System.out.println("我是代理实现1");
				}
				@Override
				public String toString() {
					return "id="+id;
				}
			};
			return (TestAuto)new ProxyJdk().getInstance(obj);
			
		}else if(objectType == TestAuto2.class){
			
			TestAuto2 obj = new TestAuto2() {
				@Override
				public void test() {
					System.out.println("我是代理实现2");
				}
			};
			return (TestAuto2)new ProxyJdk().getInstance(obj);
			
		}else if(objectType == TestAuto3.class){
			TestAuto3 obj = new TestAuto3() {
				@Override
				public void test() {
					System.out.println("我是代理实现3");
				}
			};
			return (TestAuto3)new ProxyJdk().getInstance(obj);
		}else{
			TestAuto4 obj = new TestAuto4() {
				@Override
				public void test() {
					System.out.println("我是代理实现4");
				}
			};
			return (TestAuto4)new ProxyJdk().getInstance(obj);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return objectType;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setObjectType(Class<?> objectType) {
		this.objectType = objectType;
	}
}
