package com.mvw.cpm.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
#jpa定义日期
@Temporal(TemporalType.DATE)

#枚举
@Enumerated(EnumType.STRING)

#大文本
@Lob

#瞬时的
@Transient

#大文本不查询
@Lob @Basic(fetch=FetchType.LAZY)  4G LongText

7.@Column    该注解表示数据表的映射列，放在属性的getter方法上：
  · length  该属性表示该映射列的长度；
  · nullable  该属性表示该列是否可为空，true表示可为空，false表示不可为空；
  · name   该属性表示为该列起别名，不让实体类的属性名与数据库的列名相同；

8.@Table  该注解表示映射的表；放在该实体类之上：
  · name  该属性表示为表起别名，让实体类与数据表名不相同；
 */
@Entity(name="jpa1")
public class JpaBean extends BaseEntity{

    private static final long serialVersionUID = -3315184292982842282L;

	private String name;/*名称*/

	private String content;/*内容*/

	private Integer score;/*成绩*/
	
	@OneToOne(mappedBy="jpaBean")
	private JpaBean2 jpaBean2;
	
	/*
	 * OneToMany:一对多的配置
	 * cascade=CascadeType.REFRESH 	级联操作
	 * fetch=FetchType.EAGER 		指定立即加载，默认延迟加载(立即加载后会导致父每条记录都会去查相应的子记录)
	 * mappedBy="pid"  				指定由多的一方的pid属性维护关联关系,相当于当前类放弃维护关系
	 * 
	 * 级联是很危险的操作，谨慎
	 */
	@OneToMany(mappedBy="jpaBean")
	private List<JpaSubBean> subBeans;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

    public List<JpaSubBean> getSubBeans() {
    	return subBeans;
    }

	
    public void setSubBeans(List<JpaSubBean> subBeans) {
    	this.subBeans = subBeans;
    }

	public JpaBean2 getJpaBean2() {
		return jpaBean2;
	}

	public void setJpaBean2(JpaBean2 jpaBean2) {
		this.jpaBean2 = jpaBean2;
	}
}
