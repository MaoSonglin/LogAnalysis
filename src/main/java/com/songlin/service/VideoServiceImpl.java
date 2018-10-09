package com.songlin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.songlin.bean.Video;
import com.songlin.mapper.VideoMapper;

@Service("videoService")
public class VideoServiceImpl implements VideoService {

	@Resource(name="videoMapper")
	private VideoMapper videoMapper;
	@Override
	public List<Video> list(Integer top) {
		List<Video> list = videoMapper.list(top);
		return list;
	}

}
