package com.mvw.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.cache.decorators.LruCache;
import org.springframework.stereotype.Repository;

import com.mvw.mybatis.cache.MyCacheProvider;
import com.mvw.mybatis.model.MyBean;

/**
 * 整合方式4:写sql
 * 
 * 不需要xml配置文件，直接在注解上写sql
 * 阅读性更好，也更加轻量和简单，对于日常处理
 * 注解+sql的形式几乎可以满足所有需要。
 * 若写注解，还不如用自己的jade呢!
 * 
 * CRUD注解只能解决简单的映射问题，对于复杂
 * 的映射问题，它也提供了一种新的解决方法，以满足
 * 你的特别嗜好:
 * 
 *  <pre>
	    @InsertProvider
	    @UpdateProvider
	    @DeleteProvider
	    @SelectProvider
    </pre>
    
          结合Sql Builder的可以完成一些复杂sql的
          构建工作.
          具体见org.apache.ibatis.jdbc.SQL
          
         杂必然会乱，看似多种方案，实则促襟见肘，被动应对罢了      
 */
@Repository
@CacheNamespace(implementation=MyCacheProvider.class,eviction=LruCache.class,flushInterval=0,size=1024,readWrite=false)
/*
 * 1)命令空间内有效,无法具体到具体的方法，不灵活 
 * 2)并且缓存的key比较大(对象信息以及sql，参数等)，浪费空间
 * 3)有更新操作会删除所有的key,这非常不合理
 */
public interface SqlDao {
	/*
	 * 对于简单的(满足日常业务的90%-100%)事情建议这样做，较xml来说阅读性更好
	 * 不好的一点是:insert无法返回主键,可以修改配置改变这一默认行为
	 * 注解select,delete,update,insert类型需要开发人员自己维护好，程序不做验证的
	 * select中也可以指定修改类型的，照样可以执行成功
	 */
	@Select("select * from t1 where id=#{0}")
	MyBean getUserById(Integer id);
	
	@Update("update user set name=#{0} where id=#{1}")
	Integer updateNameById(String name,Integer id);
	
	//可以查询别的表   名称不一样时，这里加as指定别名即可
	@Select("select _id as id,_name as name from t2")
	List<MyBean> getUsers();
	
	//特别注意:方法重载是很危险的，不建议使用
	//sql已经执行了，但是结果封装错误了(这种危险啊)
	@Insert("insert into user(name) values(#{0})")
	Integer save(String name);
	
	//批量查询不可用
	@Options(useGeneratedKeys=false)
	@Insert("insert into user(name) values(#{list[0]})")
	Integer saves(List<String> list);
	
	/*
	 * 可实现方式
	 * public interface UserDAO {
    		 @InsertProvider(type = UserDAOProvider.class, method = "insertAll")
    		 void insertAll(List<User> users);
		}
		//UserDAOProvider.java
		public class UserDAOProvider {
		    public String insertAll(Map map) {
		        List<User> users = (List<User>) map.get("list");
		        StringBuilder sb = new StringBuilder();
		        sb.append("INSERT INTO User ");
		        sb.append("(id, name) ");
		        sb.append("VALUES ");
		        MessageFormat mf = new MessageFormat("(null, #'{'list[{0}].name})");
		        for (int i = 0; i < users.size(); i++) {
		            sb.append(mf.format(new Object[]{i}));
		            if (i < users.size() - 1) {
		                sb.append(",");
		            }
		        }
		        return sb.toString();
		    }
		}
	 */
	
	//注解必须是insert & useGeneratedKeys=true才会设置主键
	@Insert("insert into user(name) values(#{name})")
	Integer save2(MyBean b);
}
