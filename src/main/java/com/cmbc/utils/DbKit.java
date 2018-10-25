package com.cmbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class DbKit {

	public static void batchMysql(String tableName, String[] fields, List<Map> listResult){
		List<String> fieldsList = Arrays.asList(fields);
		StringBuilder sb = new StringBuilder("insert into ");
		//需要插入的表名称
		sb.append(tableName).append("(");
		//拼接需要插入的字段
		for (int i = 0; i < fieldsList.size(); i++) {
			if(i < fieldsList.size()-1){
				sb.append(fieldsList.get(i).trim()).append(",");
				continue;
			}
			sb.append(fieldsList.get(i));
		}
		sb.append(") values(");
		//拼接占位符
		for (int i = 0; i < fieldsList.size(); i++) {
			if(i < fieldsList.size()-1){
				sb.append("?,");
				continue;
			}
			sb.append("?");
		}
		sb.append(")");
		System.out.println("sql-->>>>>>>>>>>>>>>>"+sb.toString());
		DbKit.batchMysql(sb.toString(), listResult, fieldsList);

	}
	private static void batchMysql(String sql, List<Map> listResult, List<String> fields){
		System.out.println("批量处理数据大小----"+listResult.size());
		long pre_time = System.currentTimeMillis();
		String url = PropertyUtil.getMysqlProperties("datasource.url");
		String username = PropertyUtil.getMysqlProperties("datasource.username");
		String password = PropertyUtil.getMysqlProperties("datasource.password");
		Connection conn = getConnection(url, username, password);
		try {
			conn.setAutoCommit(false);//取消自动提交
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i = 0; i < listResult.size(); i++){
				Map record = listResult.get(i);
				for (int j = 0; j < fields.size(); j++){
					ps.setObject(j+1, record.get(fields.get(j)));
				}
				ps.addBatch();
			}
			int[] size = ps.executeBatch();
			conn.commit();
			long now_time = System.currentTimeMillis();
			System.out.println("batch completed ["+size.length+"]  in "+((now_time - pre_time)/1000)+"s内完成");
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static Connection getConnection(String url, String username, String pwd){
		Connection conn =null;
		String driverName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
