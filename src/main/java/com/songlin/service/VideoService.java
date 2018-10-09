package com.songlin.service;

import java.util.List;

import com.songlin.bean.Video;

public interface VideoService {

	List<Video> list(Integer top);

}
