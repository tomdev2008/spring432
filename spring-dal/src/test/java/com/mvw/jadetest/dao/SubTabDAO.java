package com.mvw.jadetest.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.ShardBy;

/**
 * 分表测试:注意表中不要用关键词
 */
@DAO
public interface SubTabDAO {
	
	String tableName="t";
	
	String rFilds="id,name,bb";
	
	String wFilds="name,bb";

	@SQL("select name from $tableName where bb=:1 limit 1")
	String get(@ShardBy Integer by);
	
	@ReturnGeneratedKeys
	@SQL("insert into $tableName($wFilds) values(:1,:2)")
	Integer save(String name,@ShardBy Integer bb);
	
	@SQL("update $tableName set name=:1 where bb=:2")
	Integer up(String name,@ShardBy Integer bb);
}
