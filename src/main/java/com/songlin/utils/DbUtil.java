package com.songlin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbUtil {
	private static String driver;
	private static String url;
	private static String user;
	private static String pwd;
	static {
		InputStream is = DbUtil.class.getClassLoader().getResourceAsStream("db.properties");
		Properties prop = new Properties();
		try {
			prop.load(is);
			driver = prop.getProperty("jdbc.driver");
			url = prop.getProperty("jdbc.url");
			user = prop.getProperty("jdbc.user");
			pwd = prop.getProperty("jdbc.pwd");
			Class.forName(driver);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static Connection getConnection(){
		Connection conn= null;
		try {
			conn = DriverManager.getConnection(url,user,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conn;
	}
	public static void close(Connection conn,Statement st,ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
