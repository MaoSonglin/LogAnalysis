package com.songlin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.songlin.bean.Course;
import com.songlin.mapper.CourseMapper;

@Service("cityCourseService")
public class CityCourseServiceImpl implements CityCourseService {

	@Resource(name="courseMapper")
	private CourseMapper courseMapper;
	@Override
	public List<Course> getTop(Integer n) {
		List<Course> listTop = courseMapper.listTop(n);
		return listTop;
	}

}
