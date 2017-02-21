package test_conf;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class TestDOM4J {

	/**
	 * 初步构想是:xml配置数据依赖关系 或json配置,这样解析会很方便的
	 */
	@Test
	public void test1() {
		long starttime = System.currentTimeMillis();

		try {
			InputStream in = getXmlFileStream();

			Reader reader = new InputStreamReader(in, "utf-8"); // 注意编码问题

			SAXReader SaxReader = new SAXReader();
			
			Document doc = SaxReader.read(reader);
			
			Element root = doc.getRootElement();//根节点
			
			List<Element> list = root.elements();
			
			for(Element e:list){
				System.out.println(e.attributeValue("id"));
				System.out.println(e.getTextTrim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("运行时间：" + (System.currentTimeMillis() - starttime) + " 毫秒");
	}

	private InputStream getXmlFileStream() {
		return this.getClass().getClassLoader().getResourceAsStream("test.xml");
	}
}
