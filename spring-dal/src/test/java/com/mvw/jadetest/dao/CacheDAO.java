package com.mvw.jadetest.dao;

import java.util.List;

import com.mvw.jadetest.model.Person;

import net.paoding.rose.jade.annotation.Cache;
import net.paoding.rose.jade.annotation.CacheDelete;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * 缓存测试 
 */
@DAO
public interface CacheDAO {
       
		// 缓存  放在写方法上无效
        // key的组成规则是  xxx_:name/:number这样的串,参数一定要按照各式来，多个参数用"_"隔开
        @Cache(key="k_:1",pool="test",expiry=60)
        @SQL("select name from t where id<:1")
        List<String> getName(int id);

        
        @Cache(key="k_:1.id_:1.name_789",pool="test",expiry=60)
        @SQL("select name from t where :1.id<:1")
        List<String> getName(Person p);
        
        //删除缓存：放在读方法上无效
        @CacheDelete(key="k_:1",pool="test")
        @SQL("delete from t where id=:1")
        Integer delName(int id);     
}
