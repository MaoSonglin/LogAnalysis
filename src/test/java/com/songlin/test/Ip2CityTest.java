package com.songlin.test;

import org.junit.Before;
import org.junit.Test;

import com.ggstar.util.ip.IpHelper;
import com.songlin.utils.Ip2City;

public class Ip2CityTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test4(){
		String region = IpHelper.findRegionByIp("183.162.52.7");
		System.out.println(region);
	}
	@Test
	public void test2() {
		String city = Ip2City.getFrom("183.162.52.7");
		System.out.println(city);
	}
	@Test
	public void test() {
		String city = Ip2City.getCity("183.162.52.7");
		System.out.println(city);
	}

}
