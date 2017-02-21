package com.mvw.controller;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvw.beanpostprocessor.AutoLogger;


@Controller
public class TestController implements ApplicationContextAware{
	
	@AutoLogger
	private  Logger logger=null;
	
	//private ApplicationContext applicationContext;
	
	/**
	 * 实现容器的BeanFactoryPostProcessor，来自动注入自己设置的对象
	 */
//	@Autowired
//	private TestAuto testAuto;

	@RequestMapping(value="test",method=RequestMethod.GET)
	public String test1(Model model,String t){
		
		logger.info("hello t="+t);
		model.addAttribute("name","name123");
		
//		//动态注入的对象如法直接@Autowired注入
//	    TestAuto testAuto = applicationContext.getBean(TestAuto.class);
		//testAuto.test();
		//logger.info("testAuto="+testAuto);
		
		return "index";
	}
	
	@RequestMapping(value="test2",method=RequestMethod.GET)
	@ResponseBody
	public String test2(String t){
		logger.info("hello t="+t);
		return "ok";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		//this.applicationContext=applicationContext;
	}

}
