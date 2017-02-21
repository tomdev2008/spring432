package jadetest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.mvw.jadetest.dao.CacheDAO;
import com.mvw.jadetest.model.Person;

@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class CacheTest1 extends AbstractJUnit4SpringContextTests {

	@Autowired
	private CacheDAO cacheDAO;

	@Test
	public void test1() {
		// 查询
		System.out.println(cacheDAO.getName(8));
		System.out.println(cacheDAO.getName(8));
	}

	@Test
	public void test2() {
		// 查询
		Person p = new Person();
		p.setId(8);
		p.setName("nameTest");
		
		System.out.println(cacheDAO.getName(p));
		System.out.println(cacheDAO.getName(p));
	}

	@Test
	public void test3() {

		String str = ":1.id_:1.name_789";

		Pattern PATTERN = Pattern.compile("(\\:[a-zA-Z0-9\\.]*)");

		Matcher matcher = PATTERN.matcher(str);

		if (matcher.find()) {
			do {
				final String name = matcher.group(1).trim();
				System.out.println(name);
			} while (matcher.find());
		}
	}
}
