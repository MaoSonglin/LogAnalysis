package com.songlin.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 

public class QueryRunner {
	
	private Connection connection;
	
	public QueryRunner(Connection connection) {
		super();
		this.connection = connection;
	}


	public int update(String sql,Object...params){
		System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps=null;
		int result=0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int length = params.length;
			for (int i = 0; i < length; i++) {
				ps.setObject(i+1, params[i]);
			}
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
    		DbUtil.close(conn,ps,null);
        }
		return result;
	}
	
	
	public <T> List<T> query(String sql, RawHandler<T> rawMap, Object...params){
		System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet result=null;
		List<T> list = new ArrayList<>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int length = params.length;
			for (int i = 0; i < length; i++) {
				ps.setObject(i+1, params[i]);
			}
			result = ps.executeQuery();
			while(result.next()){
				if(rawMap!=null){
					T resut = rawMap.doResut(result);
					if(result!=null)
						list.add(resut);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DbUtil.close(conn,ps,result);
		}
		return list;
	}
	
	
	
	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}



	public  interface RawHandler<T>{
		T doResut(ResultSet resultSet) throws SQLException;
	}
}
