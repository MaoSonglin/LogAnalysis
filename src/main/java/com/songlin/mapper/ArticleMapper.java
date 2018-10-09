package com.songlin.mapper;

import java.util.List;

import com.songlin.bean.Article;

public interface ArticleMapper {
	void insert(Article a);
	void deleteAll();
	List<Article> listTop(Integer top);
}
