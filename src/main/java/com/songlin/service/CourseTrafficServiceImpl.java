package com.songlin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.songlin.bean.CourseTraffic;
import com.songlin.mapper.CourseTrafficMapper;

@Service("courseTrafficService")
public class CourseTrafficServiceImpl implements CourseTrafficService {

	@Resource(name="courseTrafficMapper")
	private CourseTrafficMapper courseTrafficMapper;
	@Override
	public List<CourseTraffic> getTop(Integer n) {
		if(n==null)
			n = 5;
		List<CourseTraffic> toplist = courseTrafficMapper.getTop(n);
		return toplist;
	}

}
