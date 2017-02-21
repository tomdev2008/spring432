package tmp;

import org.junit.Test;

import com.mvw.mybatis.model.MyBean;

import ognl.Ognl;
import ognl.OgnlException;

public class TestOgnl {

	@Test
	public void test1(){
		MyBean m=new MyBean();
		m.setId(1);
		
		//基于反射的话，你也可以完全开发出自己的一套动态语言
		try {
			Object t = Ognl.getValue("id > 1", m);
			System.out.println(t);
			
		} catch (OgnlException e) {
			e.printStackTrace();
		}
	}
}
