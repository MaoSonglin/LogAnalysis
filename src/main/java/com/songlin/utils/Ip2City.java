package com.songlin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.DatabaseReader.Builder;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;

public class Ip2City {

	private static DatabaseReader reader;
	static{
		try {
			Builder builder = new DatabaseReader.Builder(Ip2City.class.getClassLoader().getResourceAsStream("GeoLite2-City.mmdb"));
			reader = builder.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCity(String ip){
		String res=null;
        try {
			InetAddress ipAddress = InetAddress.getByName(ip);

			// 获取查询结果
			CityResponse response = null;
			response = reader.city(ipAddress);
			City city = response.getCity();
			//System.out.println(city.getNames().get("zh-CN"));
			res=city.getNames().get("zh-CN");
		} catch (IOException | GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        return res;
	}
	
	public static String getFrom(String ip){
		String city = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream inputStream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer sb = new StringBuffer();
			String tmp = null;
			while((tmp=reader.readLine())!=null){
				sb.append(tmp);
			}
			System.out.println(sb);
			reader.close();
			JSONObject jsonObject = JSONObject.parseObject(sb.toString());
			JSONObject jsonObject2 = jsonObject.getJSONObject("data");
			if(jsonObject2!=null)
			{
				String string = jsonObject2.getString("city");
				return string;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn!=null)
				conn.disconnect();
		}
		return city;
	}
}
