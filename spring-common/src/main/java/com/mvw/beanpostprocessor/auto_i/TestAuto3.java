package com.mvw.beanpostprocessor.auto_i;

/**
 * 测试自动注入：
 * 我的目的是，对于这样一个接口，我自己不单独一一实现
 * 而是通过动态代理和注解提供统一的实现，一般被用来开发
 * 框架，如好多DAO层只需要写接口就可以了。
 */
public interface TestAuto3 {

	void test();
}
