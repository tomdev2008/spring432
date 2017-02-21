package springtest.viewresolver;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

/*
 * 自定义视图解析器
 * 		文件下载解析器:用户返回文件路径，它负责输出给用户
 * 
 * spring系列ViewResolver是一个链式结构
 * 可以通过ordered的接口参数来进行顺序的调整
 * 一般默认是Integer.MAX_VALUE，值越小
 * 越优先执行，值一样应该是按照配置的先后顺序执行
 * 
 * 可以做到:
 * 1)自己定义实现类，可以自己定规则
 * 2)配置多个ftl,vm,jsp根据不同的视图来处理(多视图解析器)
 */
public class FileViewResolver extends AbstractCachingViewResolver implements Ordered{

	private int order=Integer.MAX_VALUE;
	
	@Override
    public int getOrder() {
	    return order;
    }
	
    public void setOrder(int order) {
    	this.order = order;
    }

    /**
     * the View object, or {@code null} if not found
	 * (optional, to allow for ViewResolver chaining)
     */
	@Override
    protected View loadView(String viewName, Locale locale) throws Exception {
		if(viewName.startsWith(MyFileView.FILE_VIEW_PREFIX)){ 
			return new MyFileView(viewName); 
		} 
		return null;
    }
} 
