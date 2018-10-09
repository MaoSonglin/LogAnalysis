package com.songlin.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.songlin.bean.Article;
import com.songlin.bean.Course;
import com.songlin.bean.Video;
import com.songlin.mapper.ArticleMapper;
import com.songlin.mapper.CourseMapper;
import com.songlin.mapper.VideoMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class VideoMapperTest {
	@Autowired
	VideoMapper videoMapper;
	@Autowired
	CourseMapper courseMapper;
	
	@Autowired
	ArticleMapper articleMapper;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsert() {
		videoMapper.insert(new Video(1,"234234"));
	}

	@Test
	public void testDeleteAll() {
		videoMapper.deleteAll();
	}
	@Test
	public void teseCouseMapper(){
		courseMapper.insert(new Course(23,"2342"));
	}
	@Test
	public void testCourseMapperDeleteAll(){
		courseMapper.deleteAll();
	}
	@Test
	public void testArticleMapperInsert(){
		articleMapper.insert(new Article(234,"234234"));
	}
	@Test
	public void testArticleMapperDeleteAll(){
		articleMapper.deleteAll();
	}
}
