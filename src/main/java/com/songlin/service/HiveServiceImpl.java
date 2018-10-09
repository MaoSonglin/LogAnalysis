package com.songlin.service;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.songlin.bean.Article;
import com.songlin.bean.Course;
import com.songlin.bean.CourseTraffic;
import com.songlin.bean.Video;
import com.songlin.bean.VideoAndArticle;
import com.songlin.dao.HiveDataDao;
import com.songlin.mapper.ArticleMapper;
import com.songlin.mapper.CourseMapper;
import com.songlin.mapper.CourseTrafficMapper;
import com.songlin.mapper.VideoAndArticleMapper;
import com.songlin.mapper.VideoMapper;
import com.songlin.utils.Constants;
import com.songlin.utils.DbUtil;
import com.songlin.utils.QueryRunner;

@Service("hiveService")
public class HiveServiceImpl implements HiverSevice {

	@Resource(name="hiveDataDao")
	private HiveDataDao hiveDataDao;
	@Resource(name="videoMapper")
	private VideoMapper videoMapper;
	@Resource(name="articleMapper")
	private ArticleMapper articleMapper;
	@Resource(name="videoAndArticleMapper")
	private VideoAndArticleMapper videoAndArticleMapper;
	@Resource(name="courseMapper")
	private CourseMapper courseMapper;
	@Resource(name="courseTrafficMapper")
	private CourseTrafficMapper courseTrafficMapper;
	
	
	@Deprecated
	public String loadToHive1(String filename) {
		final Connection conn = DbUtil.getConnection();
		try {
			
			
			QueryRunner runner;
			runner = new QueryRunner((Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), 
					conn.getClass().getInterfaces(), (obj,method,args)->{
						if(method.getName().equals("close")){
							return null;
						}else{
							return method.invoke(conn, args);
						}
					}));
			runner.update("drop table if exists data");
			runner.update("create table data("
					+ "num bigint,ip string,time string,day string,traffic bigint,type string,id string)  "
					+ "row format delimited fields terminated by '\t'");
			runner.update(String.format("load data inpath '%s' into table data", filename));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			DbUtil.close(conn, null, null);
		}
	}

	@Override
	public String loadToHive(String path,String tablename) {
		System.out.printf("将文件‘%s’导入到hive仓库的数据表“%s”中\n",path,tablename);
		if(hiveDataDao.dropTable(tablename)&&
			hiveDataDao.createTable(tablename)&&
			hiveDataDao.loadhdfsdata(path,tablename)){
			System.out.printf("文件‘%s’导入成功",path);
			return "导入成功";
		}
		return "导入失败";
	}

	@Override
	@Transactional
	public String videoAndArticle() {
		List<VideoAndArticle> list = hiveDataDao.censusVideoAndArticle();
		for (VideoAndArticle va : list) {
			videoAndArticleMapper.insert(va);
		}
		return "success";
	}

	@Override
	@Transactional
	public String countCourseByCity() {
		System.out.printf("按照地市统计hive数据表“%s”中最受欢迎的课程\n",Constants.getString(Constants.HIVE_TABLE));
		List<Course> censusCourseByCity = hiveDataDao.censusCourseByCity();
		courseMapper.deleteAll();
		for (Course course : censusCourseByCity) {
			courseMapper.insert(course);
		}
		return "success";
	}

	@Override
	@Transactional
	public String countVide() {
		System.out.printf("统计hive仓库中数据表“%s”中视频访次数topn\n",Constants.getString(Constants.HIVE_TABLE));
		List<Video> videos = hiveDataDao.countVideo();
		videoMapper.deleteAll();
		for (Video video : videos) {
			videoMapper.insert(video);
		}
		return "success";
	}

	@Override
	@Transactional
	public String countArticle() {
		System.out.printf("统计hive仓库中数据表“%s”中文章访次数topn\n",Constants.getString(Constants.HIVE_TABLE));
		List<Article> articles = hiveDataDao.countArticle();
		articleMapper.deleteAll();
		for (Article a : articles) {
			articleMapper.insert(a);
		}
		return "success";
	}

	@Override
	@Transactional
	public String countCourseByTraffic() {
		System.out.printf("按照流量统计hive数据表“%s”中最受欢迎的课程\n",Constants.getString(Constants.HIVE_TABLE));
		List<CourseTraffic> list = hiveDataDao.countTrafficOfCourse();
		for (CourseTraffic courseTraffic : list) {
			courseTrafficMapper.insert(courseTraffic);
		}
		return "success";
	}

}
