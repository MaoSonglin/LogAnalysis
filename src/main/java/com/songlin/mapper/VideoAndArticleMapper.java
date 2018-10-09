package com.songlin.mapper;

import java.util.List;

import com.songlin.bean.VideoAndArticle;

public interface VideoAndArticleMapper {
	void insert(VideoAndArticle a);
	
	List<VideoAndArticle> list();
}
