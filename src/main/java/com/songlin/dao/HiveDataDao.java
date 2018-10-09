package com.songlin.dao;

import java.util.List;

import com.songlin.bean.Article;
import com.songlin.bean.Course;
import com.songlin.bean.CourseTraffic;
import com.songlin.bean.Video;
import com.songlin.bean.VideoAndArticle;

public interface HiveDataDao {
	
	boolean dropTable(String tablename);
	
	boolean createTable(String tablename);
	
	boolean loadhdfsdata(String filepath,String table);

	List<VideoAndArticle> censusVideoAndArticle();

	List<Course> censusCourseByCity();

	List<Video> countVideo();

	List<Article> countArticle();
	
	List<CourseTraffic> countTrafficOfCourse();
}
