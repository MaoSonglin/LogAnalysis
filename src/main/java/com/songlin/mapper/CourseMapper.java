package com.songlin.mapper;

import java.util.List;

import com.songlin.bean.Course;

public interface CourseMapper {
	void insert(Course c);
	void deleteAll();
	List<Course> listTop(Integer n);
}
