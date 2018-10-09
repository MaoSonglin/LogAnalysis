package com.songlin.service;

import java.util.List;

import com.songlin.bean.CourseTraffic;

public interface CourseTrafficService {
	
	List<CourseTraffic> getTop(Integer n);
}
