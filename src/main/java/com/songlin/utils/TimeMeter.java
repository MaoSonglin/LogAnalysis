package com.songlin.utils;

public class TimeMeter {
	private long startTime;
	public TimeMeter(){
		startTime = System.currentTimeMillis();
	}
	
	public void reset(){
		startTime = System.currentTimeMillis();
	}
	
	public void info(){
		long ms = System.currentTimeMillis()-startTime;
		long h = ms / 3600000;
		ms %= 3600000;
		long min = ms / 60000;
		ms %= 60000;
		long s = ms / 1000;
		ms %= 1000;
		System.out.printf("耗时：%d时%d分%d秒%d毫秒\n",h,min,s,ms);
	}
	
	public long usingTime(){
		return System.currentTimeMillis() - startTime;
	}
}
