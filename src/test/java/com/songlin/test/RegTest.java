package com.songlin.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegTest {

	@Test
	public void testSeatch(){
		String s = "http://www.neusoft.com/code/2268";
		/*System.out.println("(\\w+:\\/\\/)?(\\w+\\.)*\\w+");
		Pattern compile = Pattern.compile("(\\w+:\\/\\/)?(\\w+\\.)+\\w+");
		Matcher matcher = compile.matcher(s);
		if(matcher.find()){
			String group = matcher.group();
			System.out.println(group);
			int start = matcher.start();
			int end = matcher.end();
			System.out.printf("开始位置%d,结束位置%d\n",start,end);
			String sub = s.substring(end);
			System.out.println(sub);
			sub.matches("\\/\\w+\\/\\d+");
		}*/
		Pattern compile2 = Pattern.compile("\\w+\\/\\d+");
		Matcher m = compile2.matcher(s);
		if(m.find()){
			String group = m.group();
			System.out.println(group);
			
		}
	}
}
