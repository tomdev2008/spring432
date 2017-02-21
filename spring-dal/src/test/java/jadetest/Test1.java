package jadetest;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;


public class Test1 {
	
	@Test
	public void test1(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put(":1", 111);
		map.put(":name", 222);
		
		System.out.println(buildKey("kkk_:name", map));

	}
	
	
	// 参数的模板:原模式无法拿出:name的值，获取到的都是null,特此修改
    private static final Pattern PATTERN = Pattern.compile("(\\:[a-zA-Z0-9_\\.]*)");

    //将xxx_:name_xxx_:name这样的值用参数替换掉
    private static String buildKey(String key, Map<String, Object> parameters) {
        // 匹配符合  :name 格式的参数
        Matcher matcher = PATTERN.matcher(key);
        if (matcher.find()) {

            StringBuilder builder = new StringBuilder();

            int index = 0;

            do {
                // 提取参数名称
                final String name = matcher.group(1).trim();

                Object value = null;

                // 解析  a.b.c 类型的名称 
                int find = name.indexOf('.');
                if (find >= 0) {

                    // 用  BeanWrapper 获取属性值
                    Object bean = parameters.get(name.substring(0, find));
                    if (bean != null) {
                        value = new BeanWrapperImpl(bean)
                                .getPropertyValue(name.substring(find + 1));
                    }
                } else {
                    // 获取参数值
                    value = parameters.get(name);
                }
                // 拼装参数值
                builder.append(key.substring(index, matcher.start()));
                builder.append(value);
                index = matcher.end();
            } while (matcher.find());
            // 拼装最后一段
            builder.append(key.substring(index));
            return builder.toString();
        }
        return key;
    }
}
