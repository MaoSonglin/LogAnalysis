package com.songlin.test;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.songlin.utils.Constants;

public class ConstantsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void run(){
		String string = Constants.getString("hive.table");
		System.out.println(string);
	}
	@Test
	public void test() {
		InputStream resource = Constants.class.getClassLoader().getResourceAsStream("constant.properties");
	
		System.out.println(resource);
	}

}
