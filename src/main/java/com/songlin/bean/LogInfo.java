package com.songlin.bean;

import java.io.Serializable;

public class LogInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LogInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LogInfo(String ip, String date, String day, Long traffic, String type, String id) {
		super();
		this.ip = ip;
		this.date = date;
		this.day = day;
		this.traffic = traffic;
		this.type = type;
		this.id = id;
	}
	private String ip;
	private String date;
	private String day;
	private Long traffic;
	private String type;
	private String id;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Long getTraffic() {
		return traffic;
	}
	public void setTraffic(Long traffic) {
		this.traffic = traffic;
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
		String format = String.format("%s\t%s\t%s\t%d\t%s\t%s",ip,date,day,traffic,type,id);
		return format;
	}
	
}
