package com.songlin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.songlin.bean.Article;
import com.songlin.mapper.ArticleMapper;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Resource(name="articleMapper")
	private ArticleMapper articleMapper;
	@Override
	public List<Article> getTopOf(Integer top) {
		List<Article> list = articleMapper.listTop(top);
		return list;
	}

}
