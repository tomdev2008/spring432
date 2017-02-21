package springtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 多视图解析器测试
 * 
 * @author gaotingping
 *
 *         2016年11月24日 下午2:06:05
 */
@Controller
@RequestMapping("/viewresolver")
public class TestViewResolverController {

	@RequestMapping("jsp")
	public String toJsp() {
		return "1.jsp";
	}

	@RequestMapping("vm")
	public String toVm() {
		return "1.vm";
	}

	@RequestMapping("ftl")
	public String toFtl() {
		return "1.ftl";
	}
}
