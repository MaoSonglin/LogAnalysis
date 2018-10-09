package com.songlin.service;

import java.util.List;

import com.songlin.bean.Course;

public interface CityCourseService {
	
	List<Course> getTop(Integer n);
}
