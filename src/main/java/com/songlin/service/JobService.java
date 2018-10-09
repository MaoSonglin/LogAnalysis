package com.songlin.service;

public interface JobService {
	
	/**
	 * 开始是清洗工作
	 */
	void startJob();
	
	/**
	 * 获取任务当前的进度
	 * @return
	 */
	double getSchedule();
}
