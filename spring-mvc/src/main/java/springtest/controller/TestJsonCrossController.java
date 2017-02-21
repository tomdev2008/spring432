package springtest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * json测试
 * 
 * @author gaotingping
 *
 *         2016年11月24日 下午2:07:00
 */
@Controller
@RequestMapping("/json")
public class TestJsonCrossController {

	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String index(@RequestBody(required = false) String body) {

		System.out.println("body=" + body);

		List<String> list = new ArrayList<String>();
		list.add("电视");
		list.add("洗衣机");
		list.add("冰箱");
		list.add("电脑");
		list.add("汽车");
		list.add("空调");
		list.add("自行车");
		list.add("饮水机");
		list.add("热水器");

		JSONObject json = new JSONObject();
		json.put("name", "123");
		json.put("data", list);

		return json.toString();
	}

	@RequestMapping(value = "/index2", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public JSONObject index2(@RequestBody(required = false) String body) {

		System.out.println("body=" + body);

		List<String> list = new ArrayList<String>();
		list.add("电视");
		list.add("洗衣机");
		list.add("冰箱");
		list.add("电脑");
		list.add("汽车");
		list.add("空调");
		list.add("自行车");
		list.add("饮水机");
		list.add("热水器");

		JSONObject json = new JSONObject();
		json.put("name", "123");
		json.put("data", list);
		System.out.println(JSON.toJSONString(json));
		return json;
	}
}
