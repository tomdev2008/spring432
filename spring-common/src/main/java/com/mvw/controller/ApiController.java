package com.mvw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ApiController{
	
	//JSON.parse(txt); 杜绝eval
	@RequestMapping(value="/t1",method=RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String test1(Model model,String t){
		return "ok";
	}
	
	@RequestMapping(value="/t2",method=RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String test2(String t){
		return "ok";
	}
}
