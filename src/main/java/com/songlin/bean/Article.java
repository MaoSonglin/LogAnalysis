package com.songlin.bean;

public class Article {
	Integer count;
	String id;
	public Article(Integer count, String id) {
		super();
		this.count = count;
		this.id = id;
	}
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Article [count=" + count + ", id=" + id + "]";
	}
	
}
