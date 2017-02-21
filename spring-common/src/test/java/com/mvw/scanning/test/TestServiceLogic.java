package com.mvw.scanning.test;

import org.springframework.stereotype.Component;

import com.mvw.scanning.annotation.BiParam;
import com.mvw.scanning.annotation.ServiceCode;
import com.mvw.scanning.annotation.ServiceLogic;

/**
 * 逻辑测试类
 */
@ServiceLogic/*标记这是一个逻辑处理类*/
@Component /*spring扫描标记*/
public class TestServiceLogic {
	
	//注入自己需要的参数:把spring mvc重写一下有什么意思呢
	@ServiceCode("001")
	public String test1(@BiParam("id") String id) {
		System.out.println(id);
		return null;
    }
}
