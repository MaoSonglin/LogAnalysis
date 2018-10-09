package com.songlin.bean;

public class CourseTraffic {
	
	private String name;
	private Long traffic;
	
	public CourseTraffic(String name, Long traffic) {
		super();
		this.name = name;
		this.traffic = traffic;
	}
	public CourseTraffic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTraffic() {
		return traffic;
	}
	public void setTraffic(Long traffic) {
		this.traffic = traffic;
	}
	@Override
	public String toString() {
		return "CourseTraffic [name=" + name + ", traffic=" + traffic + "]";
	}
	
}
