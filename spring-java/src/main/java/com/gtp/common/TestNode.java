package com.gtp.common;

public class TestNode {

	private int id;

	private String data;

	private TestNode hasNext;
	

	public TestNode() {
		super();
	}

	public TestNode(int id, String data) {
		super();
		this.id = id;
		this.data = data;
	}

	public TestNode(int id, String data, TestNode hasNext) {
		super();
		this.id = id;
		this.data = data;
		this.hasNext = hasNext;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public TestNode getHasNext() {
		return hasNext;
	}

	public void setHasNext(TestNode hasNext) {
		this.hasNext = hasNext;
	}
}
