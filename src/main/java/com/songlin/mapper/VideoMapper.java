package com.songlin.mapper;

import java.util.List;

import com.songlin.bean.Video;

public interface VideoMapper {
	void insert(Video v);
	void deleteAll();
	List<Video> list(Integer top);
}
