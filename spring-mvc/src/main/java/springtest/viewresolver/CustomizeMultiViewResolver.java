package springtest.viewresolver;

import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * 多视图解析器:可以配置不同的模板，以实现不同请求返回json,xml等的支持(非改变源代码的方式)
 * 根据后缀或指定参数，来响应不同的数据格式
 * 
 * 配置使用示例:
 */
public class CustomizeMultiViewResolver implements ViewResolver {

	private Map<String, ViewResolver> resolvers;/*支持的所有格式*/

	private String defaultSuffix = ".jsp";/*默认格式*/

	/**
	 * 按照后缀来指定解析器
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		
		int pos = viewName.lastIndexOf(".");
		
		String suffix=null;
		
		if (pos < 0) {
			viewName += defaultSuffix;
			suffix=defaultSuffix;
		}else{
			suffix = viewName.substring(pos + 1);
		}
		
		ViewResolver resolver = resolvers.get(suffix);
		
		return resolver.resolveViewName(viewName, locale);
	}

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public void setDefaultSuffix(String defaultSuffix) {
		this.defaultSuffix = defaultSuffix;
	}
}