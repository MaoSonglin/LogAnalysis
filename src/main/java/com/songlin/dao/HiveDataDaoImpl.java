package com.songlin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.songlin.bean.Article;
import com.songlin.bean.Course;
import com.songlin.bean.CourseTraffic;
import com.songlin.bean.Video;
import com.songlin.bean.VideoAndArticle;
import com.songlin.utils.Constants;
import com.songlin.utils.DbUtil;
import com.songlin.utils.QueryRunner;

@Repository("hiveDataDao")
public class HiveDataDaoImpl implements HiveDataDao {

	@Override
	public boolean dropTable(String tablename) {
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		try {
			runner.update("drop table if exists "+tablename);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean createTable(String tablename) {
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		try {
			runner.update("create table "+tablename+"("
					+ "num bigint,ip string,time string,day string,traffic bigint,type string,id string)  "
					+ "row format delimited fields terminated by '\t'");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loadhdfsdata(String filepath,String table) {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		try {
			runner.update(String.format("load data inpath '%s' into table %s", filepath,table));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<VideoAndArticle> censusVideoAndArticle() {
		String sql = "select count(*) count,type,id from "+Constants.getString("hive.table")+" where type in('video','article','code','learn') group by type,id order by count desc";
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		List<VideoAndArticle> list = runner.query(sql, rs->{
			long long1 = rs.getLong("count");
			String type = rs.getString("type");
			String id = rs.getString("id");
			VideoAndArticle videoAndArticle = new VideoAndArticle(long1,type,id);
			return videoAndArticle;
		});
		return list;
	}

	@Override
	public List<Course> censusCourseByCity() {
		String sql = "select count(*) count,ip from "+Constants.getString("hive.table")+" where type='learn' group by ip order by count desc";
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		List<Course> list = runner.query(sql, rs->{
			int count = rs.getInt("count");
			String city = rs.getString("ip");
			Course c = new Course(count,city);
			return c;
		});
		return list;
	}

	@Override
	public List<Video> countVideo() {
		String sql = "select count(*) count,id from "+Constants.getString("hive.table")+" where type in ('video') group by id order by count desc";
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		return runner.query(sql, rs->{
			int count = rs.getInt("count");
			String id = rs.getString("id");
			return new Video(count,id);
		});
	}

	@Override
	public List<Article> countArticle() {
		return new QueryRunner(DbUtil.getConnection())
				.query("select count(*) count,id from "+Constants.getString("hive.table")+" where type in ('article') group by id order by count desc", 
				rs->{
			int count = rs.getInt("count");
			String id = rs.getString("id");
			return new Article(count,id);
		});
	}

	
	public List<CourseTraffic> countTrafficOfCourse(){
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		List<CourseTraffic> list = runner.query("select sum(traffic) t ,id from "+Constants.getString("hive.table")+" where type='learn' group by id order by t desc", rs->{
			long traffic = rs.getLong("t");
			String course = rs.getString("id");
			CourseTraffic courseTraffic = new CourseTraffic(course,traffic);
			return courseTraffic;
		});
		return list;
	}
	

}
