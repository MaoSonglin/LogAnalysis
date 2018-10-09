package com.songlin.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.songlin.bean.VideoAndArticle;
import com.songlin.mapper.VideoAndArticleMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class VideoAndArticleMapperTest {
	@Resource
	VideoAndArticleMapper mapper;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsert() {
		VideoAndArticle videoAndArticle = new VideoAndArticle(2l, "video", "1234");
		mapper.insert(videoAndArticle);
	}

	@Test
	public void testList() {
		List<VideoAndArticle> list = mapper.list();
		for (VideoAndArticle videoAndArticle : list) {
			System.out.println(videoAndArticle);
		}
	}

}
