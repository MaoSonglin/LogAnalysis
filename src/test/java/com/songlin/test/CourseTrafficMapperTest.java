package com.songlin.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.songlin.bean.CourseTraffic;
import com.songlin.mapper.CourseTrafficMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CourseTrafficMapperTest {

	@Autowired
	private CourseTrafficMapper mapper;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsert() {
		mapper.insert(new CourseTraffic("sdfsdfsd",2l));
	}

}
