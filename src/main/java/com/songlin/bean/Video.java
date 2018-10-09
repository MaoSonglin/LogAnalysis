package com.songlin.bean;

public class Video {
	Integer count;
	String id;
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
	public Video(Integer count, String id) {
		super();
		this.count = count;
		this.id = id;
	}
	public Video() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Video [count=" + count + ", id=" + id + "]";
	}
	
}
