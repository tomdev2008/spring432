package springtest.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 异常测试
 */
@Controller
@RequestMapping("/except")
public class TestExceptionController {

	@RequestMapping("")
	public String test(Model model,Integer p) throws Exception{
		
		if(p==0){
			throw new Exception("500");
		}else if(p==1){
			throw new SQLException("sql");
		}else if(p==2){
			throw new IOException("io");
		}
		
		return null;
	}
}
