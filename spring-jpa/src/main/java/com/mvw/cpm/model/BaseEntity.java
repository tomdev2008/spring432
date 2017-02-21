package com.mvw.cpm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/***
 * 实体类的超类
 *
 * 好处:可以将所有实体类共性的东西，统计写在这里
 * 如对于一般实体，如下几个字段可能是必须的
 * 1)   id 主键
 * 2)stime 时间戳
 * 3)isDel 是否删除
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 6487747722674192854L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;/*主键自增*/
	
	protected String stime;/*时间戳*/
	
	protected boolean isDel;/*是否删除，一般用封装类，兼容null,但是判断的是否请注意*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}
}
