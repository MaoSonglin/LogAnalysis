package com.songlin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.songlin.bean.Article;
import com.songlin.bean.Course;
import com.songlin.bean.CourseTraffic;
import com.songlin.bean.Video;
import com.songlin.mapper.ArticleMapper;
import com.songlin.service.CityCourseService;
import com.songlin.service.CourseTrafficService;
import com.songlin.service.VideoService;

@Controller
public class CharDataController {
	
	@Resource(name="videoService")
	private VideoService videoService;
	
	@Resource(name="articleMapper")
	private ArticleMapper articleMapper;
	
	@Resource(name="cityCourseService")
	private CityCourseService cityCourseService;
	
	@Resource(name="courseTrafficService")
	private CourseTrafficService courseTrafficService;
	
	/**
	 * 发送最受欢迎的视频topN
	 * @param top
	 * @return
	 */
	@RequestMapping("/videotop")
	@ResponseBody
	public String videoTop(Integer top){
		if(top==null)
			top = 5;
		List<Video> list = videoService.list(top);
		String str = JSON.toJSON(list).toString();
		return str;
	}
	/**
	 * 发送最受欢迎的文章
	 * @param top
	 * @return
	 */
	@RequestMapping("/articletop")
	@ResponseBody
	public String articleTop(Integer top){
		if(top == null)
			top = 5;
		List<Article> topList = articleMapper.listTop(top);
		Object json = JSON.toJSON(topList);
		return json.toString();
	}
	
	@RequestMapping("/citytop")
	@ResponseBody
	public String cityCourseTop(Integer count){
		if(count == null)
			count = 5;
		List<Course> top = cityCourseService.getTop(count);
		String str = JSON.toJSON(top).toString();
		return str;
	}
	
	@RequestMapping("/traffictop")
	@ResponseBody
	public String courseTraffic(Integer count){
		List<CourseTraffic> top = this.courseTrafficService.getTop(count);
		return JSON.toJSONString(top);
	}
}
