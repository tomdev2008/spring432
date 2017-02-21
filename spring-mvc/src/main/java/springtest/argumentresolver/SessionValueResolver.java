package springtest.argumentresolver;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 参数解析器自定义:自己绑定参数<p>
 * 从这里可以得出一个结论，对于不需要的参数别多次一举让其注入进来<p>
 * 因为注入了就有个解析设置的环节
 */
public class SessionValueResolver implements HandlerMethodArgumentResolver{

	@Override//只处理标注了指定注解的参数
    public boolean supportsParameter(MethodParameter parameter) {
	     return parameter.hasParameterAnnotation(SessionValue.class);
    }

	@Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		showParams(webRequest);
		SessionValue svAnnotation = parameter.getParameterAnnotation(SessionValue.class); 
		if(svAnnotation ==null){ 
			//不知道如何处理
			return WebArgumentResolver.UNRESOLVED; 
		}
		return _resolveArgument(parameter, webRequest);
    }

	private void showParams(NativeWebRequest webRequest) {
		System.out.println("我自己的参数绑定");
		Iterator<String> params = webRequest.getParameterNames();
		while(params.hasNext()){
			System.out.println("参数:"+params.next());
		}
    }

	//绑定参数
	private Object _resolveArgument(MethodParameter parameter, NativeWebRequest webRequest) 
		throws Exception { 
		
			//
			SessionValue sessionValueAnnot = parameter.getParameterAnnotation(SessionValue.class); 
			
			//参数名称:注解中没有写，就去参数实际名称
			String attrName = sessionValueAnnot.value(); 
			if(attrName == null || attrName.equals("")) { 
				attrName = parameter.getParameterName(); 
			}
			
			//先从session中获取
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class); 
			Object value = request.getSession().getAttribute(attrName); 
			
			//再从request中获取
			if(value==null){
				value=request.getParameter(attrName);
			}
			
			//为空的话，再设置个默认值
			if(value==null){
				value="123456";
			}

		    return value; 
	} 
}
