package com.songlin.test;

import static org.junit.Assert.fail;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;

import com.songlin.bean.LogInfo;
import com.songlin.service.HdfsFileServiceImpl;
import com.songlin.utils.Constants;

public class HdfsFileServiceImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUploadFile() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetDirs(){
		HdfsFileServiceImpl service = new HdfsFileServiceImpl();
		List<String> dirs = service.getDirs("/user/root");
		for (String dir : dirs) {
			System.out.println(dir);
		}
	}
	
	@Test
	public void testExtract() {
		// 测试数据清洗
		HdfsFileServiceImpl service = new HdfsFileServiceImpl();
		service.extract("E:\\东软实训\\day14", "D:\\Users\\Administrator\\workspace\\LogAnalysis\\src\\test\\resources\\out2");
	}
	@Test
	public void testExtract2() {
		HdfsFileServiceImpl service = new HdfsFileServiceImpl();
		service.extract(Constants.getString("hdfs.file.dir"), Constants.getString("mapreduce.output.dir"));
	}

	@Test
	public void testPaseLog() {
		Frame frame = new Frame();
		FileDialog fileDialog = new FileDialog(frame,"选择文件",FileDialog.LOAD);
		fileDialog.setVisible(true);
		String directory = fileDialog.getDirectory();
		String file = fileDialog.getFile();
		frame.dispose();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(directory+file));
			String tmp = null;
			while((tmp=reader.readLine())!=null){
				LogInfo paseLog = HdfsFileServiceImpl.paseLog(tmp);
				System.out.println(paseLog);
			}
			reader.close();
		} catch ( IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testStringTokenzier(){
		StringTokenizer stringTokenizer = new StringTokenizer("absdfsdofsd#水力发电胡搜东#第三方后的那个#地方上当");
		String nextToken = stringTokenizer.nextToken("#");
		System.out.println(nextToken);
		String nextToken2 = stringTokenizer.nextToken("发电");
		System.out.println(nextToken2);
	}
}
