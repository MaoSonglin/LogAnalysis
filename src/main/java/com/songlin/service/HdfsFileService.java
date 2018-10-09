package com.songlin.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface HdfsFileService extends Serializable {
	String uploadFile(String filename);
	
	
	/**
	 * 日志文件清洗，将上传的日志文件使用MapReduce清洗
	 * @param hdfsfilename	保存日志文件目录或者日志文件
	 * @param outputdir		保存结果的目录
	 * @return
	 */
	String extract(String hdfsfilename,String outputdir);




	/**
	 * 将文件file上传到hdfs中的savepath路径下，如果路劲savepath下包含一个与file同名的文件，
	 * 默认删除该文件，该方法已过时，请使用uploadFile(CommonsMultipartFile file,String SavePath,boolean deleteOld)
	 * @param file		springmvc中接收到的文件
	 * @param savepath	hdfs路径
	 * @return			保存是否成功
	 */
	@Deprecated
	String uploadFile(CommonsMultipartFile file, String savepath);

	String uploadFile(CommonsMultipartFile file,String savepath,boolean deleteOld);
	
	
	/**
	 * 获取hdfs系统中指定目录下的所有文件夹信息
	 * @param root	hdfs上的文件目录
	 * @return
	 */
	List<String> getDirs(String root);
}
