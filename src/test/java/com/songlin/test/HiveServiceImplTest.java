package com.songlin.test;

import java.sql.ResultSetMetaData;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.songlin.bean.VideoAndArticle;
import com.songlin.service.HiverSevice;
import com.songlin.utils.Constants;
import com.songlin.utils.DbUtil;
import com.songlin.utils.QueryRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class HiveServiceImplTest {

	@Resource
	private HiverSevice service;

	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void testJdbc(){
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		runner.query("select * from emp", rs -> {
			System.out.println(rs.getObject(1));
			return null;
		});
	}

	@Test
	public void testLoadToHive() {
		service.loadToHive("/user/root/part-r-00000","data");
	}
	

	
	@Test
	public void testVideoAndArticle(){
		service.videoAndArticle();
	}
	
	@Test
	public void testHiveJdbc(){
		//select count(*) count,type,id from data where type 
		//in('video','article','code','learn') group by type,id order by count desc
		String sql = "select * from data limit 20";
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		runner.query(sql, rs->{
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				System.out.print(rs.getObject(columnCount));
				System.out.print("\t");
			}
			System.out.println();
			return null;});
	}
	@Test
	public void testHiveJdbcSql(){
		String sql = "select num from data limit 20";
//				+ " where type in('video','article','code','learn') group by type,id order by count desc";
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		runner.query(sql, rs->{
			long long1 = rs.getLong("num");
			String type = null;//rs.getString("type");
			String id = null;//rs.getString("id");
			VideoAndArticle videoAndArticle = new VideoAndArticle(long1,type,id);
			return videoAndArticle;
		});
		 
	}
	@Test
	public void testHiveSql(){
		String sql = "select * from emp";
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		runner.query(sql, rs->{
			int empno = rs.getInt("empno");
			String ename = rs.getString("ename");
			String job = rs.getString("job");
			String mgr = rs.getString("mgr");
			String hiredate = rs.getString("hiredate");
			System.out.println(String.format("%d\t%s\t%s\t%s\t%s", empno,ename,job,mgr,hiredate));
			return null;
		});
	}
	@Test
	public void testHiveSql2(){
		String sql = "select count(*) count from emp";
		QueryRunner runner = new QueryRunner(DbUtil.getConnection());
		runner.query(sql, rs->{
			int empno = rs.getInt("count");
			
			System.out.printf("一共%d条数据",empno);
			return null;
		});
	}
	@Resource
	HiverSevice hiveService;
	@Test
	public void testCountCourseByCity(){
		String msg = hiveService.countCourseByCity();
		System.out.println(msg);
	}
	@Test
	public void testCountArticle(){
		String msg = hiveService.countArticle();
		System.out.println(msg);
	}
	@Test
	public void testCountVide(){
		String msg = hiveService.countVide();
		System.out.println(msg);
	}
	
	@Test
	public void testCountTraffic(){
		String msg = hiveService.countCourseByTraffic();
		System.out.println(msg);
	}
}
