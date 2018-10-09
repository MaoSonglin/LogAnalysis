package com.songlin.utils;

import java.io.InputStream;
import java.util.Properties;

public class Constants {
	public static final String MAPREDUCE_OUTPUT_DIR="mapreduce.output.dir";
	
	public static final String HDFS_FILE_DIR = "hdfs.file.dir";
	
	public static final String HIVE_TABLE="hive.table";
	
	private  static Properties prop ;
	static{
		try {
			prop = new Properties();
			InputStream is = Constants.class.getClassLoader().getResourceAsStream("constant.properties");
			prop.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getString(String key){
		return prop.getProperty(key);
	}
}
