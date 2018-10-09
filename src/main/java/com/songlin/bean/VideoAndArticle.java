package com.songlin.bean;

import java.io.Serializable;

public class VideoAndArticle implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long count;
	private String type;
	
	private String id;

	public VideoAndArticle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VideoAndArticle(Long count, String type, String id) {
		super();
		this.count = count;
		this.type = type;
		this.id = id;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "VideoAndArticle [count=" + count + ", type=" + type + ", id=" + id + "]";
	}
	
}
