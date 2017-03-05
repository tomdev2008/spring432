package com.mvw.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mvw.mybatis.model.MyBean;
import com.mvw.mybatis.model.User;

@Repository
public interface MyDao{
		
	public List<MyBean> findAll();
	
	public MyBean findById(Integer id);
	
	public Integer add(MyBean bean);
	
	public Integer addBatch(List<MyBean> beans);
	
	//如此只能用${n}序号来引用
	public Integer update(Integer status,Integer id);
	
	public List<MyBean> findByName(String name);
	
	public Integer del(Integer id);
	
	public List<MyBean> findByBean(MyBean bean);
	
	//可以指定名称
	public List<MyBean> findByParams(@Param("id") Integer id,@Param("name") String name);
	
	public List<MyBean> findByIds(Integer ... ids);
	
	//这个联合查询，封装的结果集还是不错的奥
	public List<User> findUser();

	/**
	 * 默认返回是List<Map<字段名称，字段值>>
	 * 
	 * 需求1:
	 *    一行记录一个map，key按照自己指定
	 *    
	 * 	      加注解@MapKey可以返回Map<key,Map<查询结果kv对>
	 *    这样就把一行记录装到map的一个key=value对了
	 *    {137={_id=137, _name=名称}
	 * 
	 * 深层需求：
	 *    查询总返回2个列表，结果集合第一列为key，第二列为value
	 *    
	 *    目前还没有发现怎么支持实现，可以用上面的凑合使用
	 */
	@MapKey("id")
	public Map<Integer,String> getMap();
	
	@MapKey("id")
	public Map<Integer,User>  getMap2();
	
	
	public List<String> findAllName(@Param("id") String id);
	
	//sql注入
	public List<MyBean> sql_injection(@Param("id") String id);
	
	public List<MyBean> sql_injection2(@Param("name") String name);
	
	public List<MyBean> sql_injection3(@Param("name") String name);
}
