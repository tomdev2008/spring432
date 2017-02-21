package com.mvw.cpm.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name="jpa3")
public class JpaBean2 extends BaseEntity{

    private static final long serialVersionUID = -3315184292982842282L;

	private String name;

	private String content;

	private Integer score;

	@OneToOne
	private JpaBean jpaBean;

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
}
