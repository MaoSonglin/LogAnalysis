package com.songlin.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.songlin.utils.DateUtil;

public class DateUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParse() {
		Date date = DateUtil.parse("10/Nov/2016:00:01:02 +0800");
		System.out.println(date);
	}

}
