package com.songlin.bean;

public class Course {

	private Integer count;
	private String city;
	public Course(Integer count, String city) {
		super();
		this.count = count;
		this.city = city;
	}
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Course [count=" + count + ", city=" + city + "]";
	}
	
}
