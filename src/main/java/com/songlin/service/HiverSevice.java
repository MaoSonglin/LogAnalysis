package com.songlin.service;

public interface HiverSevice {

	/**
	 * 将hdfs中的文件filename中的数据存到hive的数据表tablename中
	 * @param filename	hdfs中的文件名
	 * @param tablename	hive中的数据表
	 * @return
	 */
	String loadToHive(String filename,String tablename);
	
	/**
	 * 统计最受欢迎的文章或者视频，将结果保存到MySQL数据库中
	 * @return
	 */
	String videoAndArticle();
	
	/**
	 * 按照地市统计最受欢迎的课程，将结果保存到MySQL数据库中
	 * @return
	 */
	String countCourseByCity();
	
	/**
	 * 统计最受欢迎的视频
	 * @return
	 */
	String countVide();
	
	/**
	 * 统计最受欢迎的文章
	 * @return
	 */
	String countArticle();
	
	/**
	 * 统计课程流量
	 * @return
	 */
	String countCourseByTraffic();
}
