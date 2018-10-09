package com.songlin.mapper;

import java.util.List;

import com.songlin.bean.CourseTraffic;

public interface CourseTrafficMapper {
	void insert(CourseTraffic c);
	/**
	 * 获取n条数据
	 * @param n
	 * @return
	 */
	List<CourseTraffic> getTop(Integer n);
}
