package com.songlin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songlin.service.HdfsFileService;
import com.songlin.service.JobService;

@Controller
public class HdfsController {

	@Resource(name="hdfsFileService")
	private HdfsFileService hdfsFileService;
	
	@Resource(name="jobService")
	private JobService jobService;
	
	
	@RequestMapping("/upload.do")
	@ResponseBody
	public String uploadFile(@RequestParam("file") CommonsMultipartFile  file,String savepath){
		// 将文件保存到hdfs中
		String msg = hdfsFileService.uploadFile(file,savepath,true);
		
		if("success".equals(msg)){
			jobService.startJob();
		}
		return msg;
	}
	
	@RequestMapping("/savepath")
	@ResponseBody
	public String getSavePath(){
		List<String> dirs = hdfsFileService.getDirs("/");
		String res = JSON.toJSONString(dirs);
		return res;
	}
	
	@RequestMapping("/jobshedule")
	@ResponseBody
	public String getShedule(){
		double schedule = jobService.getSchedule();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("schedule", schedule);
		return jsonObject.toJSONString();
	}
}
