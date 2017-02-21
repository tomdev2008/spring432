//package springtest.controller;
//
//import java.util.Locale;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.i18n.CookieLocaleResolver;
//
///**
// *	语言管理
// */
//@Controller
//public class LanguageController {
//
//	@Autowired 
//	private CookieLocaleResolver resolver;
//	
//	@RequestMapping("language")
//	public ModelAndView language(HttpServletRequest request,
//		HttpServletResponse response,
//	    @RequestParam(value="language",required=false)	String language,
//	    @RequestParam(value="refer",required=false)	String refer){
//		if("us".equalsIgnoreCase(language)){
//			resolver.setLocale(request, response,Locale.US);
//		}else{
//			resolver.setLocale(request, response, Locale.CHINA);
//		}
//		return new ModelAndView("redirect:"+refer);
//	}
//}
