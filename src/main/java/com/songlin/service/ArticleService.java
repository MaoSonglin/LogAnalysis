package com.songlin.service;

import java.util.List;

import com.songlin.bean.Article;

public interface ArticleService {
	
	
	List<Article> getTopOf(Integer top);
	
}
