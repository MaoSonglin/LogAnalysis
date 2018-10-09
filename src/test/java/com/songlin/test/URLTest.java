package com.songlin.test;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Before;
import org.junit.Test;

import com.songlin.utils.URLUtil;

public class URLTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
	
	}
	@Test
	public void test2(){
		List<NameValuePair> parse = URLEncodedUtils.parse("list?c=be&sort=last", Charset.forName("UTF-8"));
		for (NameValuePair nameValuePair : parse) {
			String name = nameValuePair.getName();
			String value = nameValuePair.getValue();
			System.out.println(name+":"+value);
		}
	}
	@Test
	public void test3(){
		Map<String, String> parameterMap = URLUtil.getParameterMap("http://sdfsd/list?c=be&sort=last");
		Set<Entry<String,String>> entrySet = parameterMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key+":"+value);
		}
	}

}
