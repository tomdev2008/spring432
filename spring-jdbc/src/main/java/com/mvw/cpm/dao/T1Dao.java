package com.mvw.cpm.dao;

import model.MyModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * spring jdbcTemplate 联系例子 
 */
@Repository
public class T1Dao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void test(MyModel m){
		jdbcTemplate.update("INSERT INTO t1(_createTime,_name,_content, _score) " +
			"VALUES(?,?,?,?)",m.getTime(),m.getName(),m.getContent(),m.getScore());
	}
}
