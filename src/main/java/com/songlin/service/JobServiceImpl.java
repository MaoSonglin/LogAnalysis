package com.songlin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.songlin.utils.Constants;
import com.songlin.utils.TimeMeter;

@Service("jobService")
public class JobServiceImpl extends Thread implements JobService {

	@Resource(name="hiveService")
	private HiverSevice hiveService;
	
	@Resource(name="hdfsFileService")
	private HdfsFileService hdfsFileService;
	
	private int stage = 0;

	private TimeMeter timeMeter=new TimeMeter();
	
	@Override
	public void run() {
		stage = 0;
		timeMeter.reset();
		// 使用MapReduce清洗日志信息
		hdfsFileService.extract(Constants.getString(Constants.HDFS_FILE_DIR) , Constants.getString(Constants.MAPREDUCE_OUTPUT_DIR));
		timeMeter.info();
		stage++;
		// 将清洗结果保存到hive数据表
		hiveService.loadToHive(Constants.getString(Constants.MAPREDUCE_OUTPUT_DIR)+"part-r-00000", Constants.getString(Constants.HIVE_TABLE));
		timeMeter.info();
		
		stage++;
		// 从hive数据表中查询最受欢迎的文章保存到MySQL中
		hiveService.countArticle();
		timeMeter.info();
		
		stage++;
		// 从hive中查询统计最受欢迎的视频保存到MySQL中
		hiveService.countVide();
		timeMeter.info();
		
		stage++;
		// 从hive中安城市统计最受欢迎的课程保存到MySQL中
		hiveService.countCourseByCity();
		timeMeter.info();
		
		stage++;
		// 从hive中按照流量统计最受欢迎的课程保存到MySQL中
		hiveService.countCourseByTraffic();
		timeMeter.info();
		stage++;
	}
	@Override
	public void startJob() {
		start();
	}
	@Override
	public double getSchedule() {
		if(stage < 6){
			
			double s = stage/6.0;
			//long total = timeMeter.usingTime();
			//s += 6.0/total * 1000;
			return s;
		}
		else{
			return 1.0;
		}
	}
}
