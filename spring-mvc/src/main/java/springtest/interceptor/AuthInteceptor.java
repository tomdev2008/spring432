package springtest.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 * 基于注解和拦截器来管理权限
 * 
 * 1)类上
 * 2)方法上
 * 
 *  规则：
 *    1)方法上为重
 *    2)方法上没有(null)判断类上
 *    
 * 参考:HandlerExecutionChain 多少能知道点其执行过程
 */
public class AuthInteceptor extends HandlerInterceptorAdapter{

	/**
	 * 前置：返回true进入下一步,false阻止继续执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
	    //cookie设置
		Cookie cookie=new Cookie("test", "123456");
	    cookie.setDomain("127.0.0.1");
	    cookie.setPath("/");//根下都可以访问
	    cookie.setMaxAge(10);//单位是秒
	    cookie.setHttpOnly(true);//仅http可获取防止js盗取,仅servlet3.0以上版本可用
		response.addCookie(cookie);
		
		
		 /*
	   	 * 跨域设置：
	   	 *    1) 协议，域名，端口都必须相同，才算在同一个域
	   	 *    2) 跨域时浏览器确实发送了请求的
	   	 *    3) header中包含了 Access-Control-Allow-Origin，并且有自己的域名时才接收数据
	   	 *    4) *是一个很大的范围，在生产环境下不建议用,有多个域则只需要使用逗号分隔开即可
	   	 *    
	   	 *    解决方法：在跨域的服务器上设置header设置为允许跨域请求
			  access-control-allow-origin: *      		//允许跨域
		      access-control-allow-credentials: true	//允许携带cookie,但是同源策略无法访问
			  
			     异步请求的js实现请参考WEB-INF\views\1.html
	   	 */    
	   	 response.setHeader("Access-Control-Allow-Origin", "*");
	   	 //设置携带cookie等凭证也没有用，同源策略你访问不了的
	   	 //response.setHeader("Access-Control-Allow-Credentials", "true");

		//cookie的限制:同域以及path
    	System.out.println("--------------cookie--------------");
    	Cookie[] cs = request.getCookies();
    	if(cs!=null){
    		for(Cookie c:cs){
    			System.out.println(c.getName()+":"+c.getValue());
    		}
    	}
    	
		//获得目标方法对象
    	System.out.println("--------------method--------------");
		if(handler instanceof HandlerMethod){
				HandlerMethod h = (HandlerMethod) handler;
				
				//method
				Method m = h.getMethod();
				//testmvc.controller.FileController  拿到全类名  正则处理配置或获得注解处理
				System.out.println(m.getDeclaringClass().getName());
				System.out.println("class="+m.getClass());
				Annotation[] as = m.getAnnotations();
				for(Annotation a:as){
					System.out.println(a);
				}
				
				System.out.println("====================================");
				
				//输出类上的注解
				Class<?> c = Class.forName(m.getDeclaringClass().getName());
				as = c.getAnnotations();
				for(Annotation a:as){
					System.out.println(a);
				}
		}
		
		System.out.println("--------------url--------------");
		System.out.println("getRequestURL: " + request.getRequestURL());//全路径
		System.out.println("getRequestURI: " + request.getRequestURI());
		System.out.println("getQueryString: " + request.getQueryString());
		System.out.println("getRemoteAddr: " + request.getRemoteAddr());//客户端IP
		System.out.println("getRemoteHost: " + request.getRemoteHost());//可能会查询dns,性能危险，建议禁用
		System.out.println("getRemotePort: " + request.getRemotePort());
		System.out.println("getRemoteUser: " + request.getRemoteUser());
		System.out.println("getLocalAddr: " + request.getLocalAddr());
		System.out.println("getLocalName: " + request.getLocalName());
		System.out.println("getLocalPort: " + request.getLocalPort());
		System.out.println("getMethod: " + request.getMethod());
		
		
		System.out.println("-------request.getParamterMap()-------");
		// 得到请求的参数Map，注意map的value是String数组类型
		Map<String, String[]> map = request.getParameterMap();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
				String[] values = (String[]) map.get(key);
				for (String value : values) {
					System.out.println(key + "=" + value);
				}
		}
			
		System.out.println("--------request.getHeader()--------");
		// 得到请求头的name集合
		Enumeration<String> em = request.getHeaderNames();
		while (em.hasMoreElements()) {
			String name = (String) em.nextElement();
			String value = request.getHeader(name);
			System.out.println(name + "=" + value);
		}
		
		return true;
	}
	
	//生成视图之前执行，可以修改ModelAndView
	@Override //Controller 执行后
	public void postHandle(HttpServletRequest request, 
		HttpServletResponse response, 
		Object handler, 
		ModelAndView modelAndView) throws Exception {
		/**
		 * 无效的原因应该是: 控制器执行后获得了ModelAndView渲染是根据mv来的
		 *              和response关系不大，但构建mv和response有关系
		 *              方法执行后request,response只读
		 *              
		 *              看:RequestMappingHandlerAdapter
		 */
//	    //添加无效
//	    Cookie cookie=new Cookie("test", "123456");
//	    cookie.setDomain("127.0.0.1");
//	    cookie.setPath("/");//根下都可以访问
//	    cookie.setMaxAge(10);//单位是秒
//	    cookie.setHttpOnly(true);//仅http可获取防止js盗取
//		response.addCookie(cookie);

		super.postHandle(request, response, handler, modelAndView);
	}
	
	//生成视图时执行，可以用来处理异常，并记录在日志中
	@Override //页面渲染之后
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		if(ex!=null){
			System.out.println("页面渲染发生了错误");
			ex.printStackTrace();
		}
		
		super.afterCompletion(request, response, handler, ex);
	}
}

/**
	--------------cookie--------------
	JSESSIONID:4E384FED77051E299C47A4947F2FDA3D
	--------------method--------------
	springtest.controller.TestController
	class=class java.lang.reflect.Method
	@springtest.interceptor.Roles(value=[C, D])
	@org.springframework.web.bind.annotation.RequestMapping(headers=[], name=, value=[test], produces=[], method=[], params=[], consumes=[])
	====================================
	@springtest.interceptor.Roles(value=[A, B])
	@org.springframework.stereotype.Controller(value=)
	--------------url--------------
	getRequestURL: http://192.168.8.147:8080/test/
	getRequestURI: /test/   (有用值)
	getQueryString: null
	getRemoteAddr: 192.168.8.147 (有用值,别用host,因为会导致域名解析)
	getRemoteHost: 192.168.8.147
	getRemotePort: 63179
	getRemoteUser: null
	getLocalAddr: 192.168.8.147
	getLocalName: GAOTINGPING.mvwchina.com
	getLocalPort: 8080
	getMethod: GET
	-------request.getParamterMap()-------
	--------request.getHeader()--------
	host=192.168.8.147:8080
	user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0 (有用值,用于分析用户终端)
	accept=text/html,application/xhtml+xml,application/xml;q=0.9,;q=0.8
	accept-language=zh-CN,en-US;q=0.8,zh;q=0.5,en;q=0.3
	accept-encoding=gzip, deflate
	cookie=JSESSIONID=4E384FED77051E299C47A4947F2FDA3D
	connection=keep-alive
	cache-control=max-age=0
 */