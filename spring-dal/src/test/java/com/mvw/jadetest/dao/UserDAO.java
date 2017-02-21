package com.mvw.jadetest.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.mvw.jadetest.model.Person;


/**
 * jade CRUD测试
 */
@DAO
public interface UserDAO {
	
	String tableName="t";
	
	String rFilds="id,name";
	
	String wFilds="name";

	/*
	 * 支持如下表达式，但是太繁杂的不建议用
	 * 多写几个方法呗，保持DAO层整齐，别搞个
	 * 200-300行的sql
	 * 
	 * 1)冒号（:）表示这是一个变量，比如上面的例子里的 :t.id，它会被一个值替换
	 * 
	 * 2)连续的井号（##） 表示后面的变量作字符串连接
	 * 
	 * 3)$tableName 引用字符串
	 * 
	 * 4)井号if（#if{}）用于表示当条件满足时sql拼接
	 * 
	 * ##但是我还是建议，尽量别用动态sql，到sql这层就维护是否简单的功能职责##
	 */
	@SQL("select $rFilds from $tableName limit 10")
	List<Person> getAll();
	
	@ReturnGeneratedKeys
	@SQL("insert into $tableName($wFilds) values(:1.name)")
	Integer save(Person p);
	
	@SQL("update $tableName set name=:1 where id=:2")
	Integer up(String name,Integer id);
}
