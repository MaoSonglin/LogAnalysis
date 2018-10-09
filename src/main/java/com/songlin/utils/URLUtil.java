package com.songlin.utils;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class URLUtil {
	private String url;
	int curPos = 0;
	private String schame = null;
	private String host = null;
	//jdbc:hive2://192.168.204.112:10000/default
	//"http://www.neusoft.com/course/list?cat_type=3&is_easy=2%C2%A0&page=20"
	public URLUtil(String url){
		this.url = url;
		int indexOf = url.indexOf("://");
		if(indexOf>0)
		 schame = url.substring(0, indexOf-1);
		curPos = indexOf+3;
		indexOf = url.indexOf("/", curPos);
		if(indexOf<0){
			host = url.substring(curPos);
		}else{
			host = url.substring(curPos,indexOf-1);
			curPos = indexOf +1;
		}
	}
	
	public String getPort(){
		return "8080";
	}
	
	public String getNextFiledir(){
		return null;
	}
	
	
	public static Map<String,String> getParameterMap(String url){
		Map<String,String> map = new HashMap<>();
		String[] split = url.split("\\?");
		if(split.length==2){
			String[] split2 = split[1].split("&");
			for (String string : split2) {
				String[] split3 = string.split("=");
				if(split3.length==2){
					map.put(split3[0], split3[1]);
				}
			}
		}
		return map;
	}
}
