package springtest.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springtest.argumentresolver.SessionValue;
import springtest.beanpostprocessor.AutoLogger;
import springtest.interceptor.Roles;

@Controller
@Roles({"A","B"})
public class TestMVCController {

	
	@AutoLogger
	protected  Logger logger;
	
	@Roles({"C","D"})//可以有简单权限控制
	@RequestMapping(value="test",method=RequestMethod.GET)
	public String test(Model model,@SessionValue("tt001") String t){
		model.addAttribute("name","name123");
		
		System.out.println("logger="+logger);
		
		System.out.println("t="+t);
		
		System.out.println("logger="+logger);
		logger.debug("它大爷的");
		
		return "index";
	}

	//REST风格的参数 ,并且支持正则表达式
	@RequestMapping("/spring-web/{symbolicName:[a-z-]}-{version:\\d\\.\\d\\.\\d}{extension:\\.[a-z]}")
	public String handle(@PathVariable String version, @PathVariable String extension) {
		return null;
	}

	@RequestMapping("/pets/{petId}")
	public String findPet(@PathVariable String ownerId, @PathVariable String petId, Model model) {
		return null;
	}
	
	// @RequestHeader("Accept-Encoding") String encoding
	@RequestMapping("cookie")
	public String test2(@CookieValue("JSESSIONID") String cookie) {
		return null;
	}
}
