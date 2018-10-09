package com.songlin.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ggstar.util.ip.IpHelper;
import com.songlin.bean.LogInfo;
import com.songlin.utils.DateUtil;

@Service("hdfsFileService")
public class HdfsFileServiceImpl implements HdfsFileService {

	private static final long serialVersionUID = 1L;
	private static Configuration conf = null;
	static {
		// 加载core-site.xml文件
		conf = new Configuration();
		// 设置用户，不过好像这里不管用
		conf.set("user", "root");
	}
	@Override
	public String uploadFile(String filename) {
		try {
			// 获取文件系统对象
			FileSystem fs = FileSystem.get(conf);
			// 本地文件对象
			File file = new File(filename);
			String name = file.getName();
			Path path = new Path("/user/root/log/"+name);
			
			if(fs.exists(path)){
				return "文件已存在";
			}
			
			fs.copyFromLocalFile(new Path(filename), path);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "";
	}

	@Override
	public String extract(String hdfsfilename,String outputdir) {
		System.out.printf("清洗文件'%s'，并将数据保存到目录'%s'\n",hdfsfilename,outputdir);
		try {
			// 获取hdfs文件系统对象
			FileSystem fs = FileSystem.get(conf);
			// 文件在hdfs系统中的路径对象
			Path inputpath = new Path(hdfsfilename);
			// 清洗过后的文件输出目录
			Path outputdirpath = new Path(outputdir);
			// 如果输出目录已经存在，必须删除原目录
			if(fs.exists(outputdirpath)&&fs.isDirectory(outputdirpath)){
				System.err.printf("“%s”已存在,执行删除文件\n",outputdir); 
				fs.delete(outputdirpath,true);
				System.err.printf("“%s”已删除\n",outputdir);
			}
			if(!fs.exists(inputpath)){
				System.err.printf("输入文件“%s”不存在!\n",hdfsfilename);
				return hdfsfilename+"文件不存在";
			}
			// 作业对象
			Job job = Job.getInstance(conf);
			// 设置作业类
			job.setJarByClass(getClass());
			// 设置mapper类
			job.setMapperClass(MyMapper.class);
			// 设置reducer任务处理类
			job.setReducerClass(MyReducer.class);
			// 设置结果输出的key值类型
			job.setOutputKeyClass(LongWritable.class);
			// 设置结果输出类型的值得类型
			job.setOutputValueClass(Text.class);
			// 设置文件输入路径
			FileInputFormat.addInputPath(job, inputpath);
			// 设置文件输出路径
			FileOutputFormat.setOutputPath(job, outputdirpath);
			// 提交作业
			job.waitForCompletion(true);
			
		} catch (IllegalArgumentException | IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	static class MyMapper extends Mapper<LongWritable,Text,LongWritable,Text>{
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, LongWritable, Text>.Context context)
				throws IOException, InterruptedException {
			context.write(key, value);
		}
	}
	static class MyReducer extends Reducer<LongWritable,Text,LongWritable,Text>{
		private static int count = 0;
		@Override
		protected void reduce(LongWritable key, Iterable<Text> values,
				Reducer<LongWritable, Text, LongWritable, Text>.Context context) throws IOException, InterruptedException {
			// 遍历数据
			for (Text text : values) {
				// 获取数据一条记录值
				String value = text.toString();
				// 将记录值解析为对象
				LogInfo log;
				try {
					log = paseLog(value);
					// 如果对象的ID存在，输出该数据，否则不输出该条数据
					if(log.getId()!=null){
						String str = log.toString();
						context.write(new LongWritable(++count), new Text(str)); 
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public static LogInfo paseLog(String value) {
		// 获取ID地址：
		String ip=null;
		String dateStr=null;
		String day=null;
		String traffic=null;
		String type = null;
		String id = null;
		// ip
		int indexOf = value.indexOf(" ");
		ip = value.substring(0, indexOf);
		ip = /*Ip2City.getCity(ip);*/ IpHelper.findRegionByIp(ip);
		value = value.substring(indexOf+1);
		// 日期
		indexOf = value.indexOf("[");
		int indexOf2 = value.indexOf("]");
		dateStr = value.substring(indexOf+1, indexOf2);
		value = value.substring(indexOf2+1);
		Date parse = DateUtil.parse(dateStr);
		dateStr = DateUtil.toLocalString(parse);
		@SuppressWarnings("deprecation")
		int date = parse.getDate();
		day = String.valueOf(date);
		
		indexOf = getCharacterPosition(value,"\"",2);
		value =value.substring(indexOf+1);
		
		// 构造一个字符串解析器
		StringTokenizer tokenizer = new StringTokenizer(value.trim());
		// 跳过一个用空格分割的字符串，该字符串表示请求结果状态
		tokenizer.nextToken();
		// 获取流量
		traffic = tokenizer.nextToken();
		
		// 过滤掉网站
		tokenizer.nextToken();
		// 获取请求路径
		String nextToken = tokenizer.nextToken();
		// 请求路径中的URL被引号包裹，这里去掉字符串首尾的引号
		String url = nextToken.substring(1, nextToken.lastIndexOf("\""));
		if(!"-".equals(url)){
			// 提取中URL最后两级目录
//			String[] split = url.split("\\/+");
			Pattern compile = Pattern.compile("\\w+\\/\\d+");
			Matcher matcher = compile.matcher(url);
			if(matcher.find()){
				String group = matcher.group();
				String[] split2 = group.split("\\/");
				type = split2[0];
				id = split2[1];
			}
			/*// 
			if(split.length>2){
				// 倒数第二级目录表示类型
				type = split[split.length-2];
				// 倒数第一级表示ID
				id = split[split.length-1];
				// 如果类型是Course，需要重新解析URL
				if("course".equalsIgnoreCase(type)&&!id.matches("\\d+")){
					id = URLUtil.getParameterMap(url).get("c");
				}
			}*/
		}
		
		LogInfo logInfo = new LogInfo(ip,dateStr,day,Long.valueOf(traffic),type,id);
		return logInfo;
	}
	/**
     * 获取指定字符串中指定标识符出现的索引位置
     * */
    private static   int  getCharacterPosition(String line,String operation,int index){
        Matcher slashMatcher= Pattern.compile(operation).matcher(line);
        int mIndex=0;
        while(slashMatcher.find()){
            mIndex++;
            if(mIndex==index){
                break;
            }
        }
        return slashMatcher.start();
    }

	@Override
	public List<String> getDirs(String root) {
		List<String> list = new ArrayList<>();
		try {
			FileSystem fs = FileSystem.get(conf);
			FileStatus[] listStatus = fs.listStatus(new Path(root));
			for (FileStatus fileStatus : listStatus) {
				boolean isdir = fileStatus.isDirectory();
				if(isdir){
//					String path = fileStatus.getPath().toString();
					String name = fileStatus.getPath().getName();
					list.add(name);
					list.addAll(getDirs(fileStatus.getPath().toString()));
				}
			}
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String uploadFile(CommonsMultipartFile file, String savepath) {
		return uploadFile(file,savepath,true);
	}

	@Override
	public String uploadFile(CommonsMultipartFile file, String savepath, boolean deleteOld) {
		// 如果把保存路径为空，给一个默认的路径
		if(savepath==null)
			savepath = "/user/root/log/";
		// 获取上传的文件名称
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		try {
			// hdfs文件系统对象
			FileSystem fs = FileSystem.get(conf);
			// 文件上传到hdfs中的路径
			Path path = new Path(savepath+filename);
			// 如果文件已经存在且deleeteold为true，删除原文件
			if(fs.exists(path)){
				if(deleteOld)
					fs.delete(path, true);
				else
					return "文件已存在";
			}
			// 文件输入流
			InputStream is = file.getInputStream();
			// 文件输出流
			FSDataOutputStream os = fs.create(path);
			// 文件流对接
			IOUtils.copyBytes(is, os, 1024*10);
			os.close();
			is.close();
			return "success";
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}


