package com.kh.water.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.water.model.vo.Water;

public class WaterDao {
	
	private Properties prop = new Properties(); // private 및 위치 *****
	
	public WaterDao() { // 내용 *****
		String filePath = WaterDao.class.getResource("/db/sql/water-mapper.xml").getPath();
		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int insertWater(Connection conn, Water w) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertWater");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, w.getBrand());
			pstmt.setInt(2, w.getPrice());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return result;
	}
	
	public int updateWater(Connection conn, Water w) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateWater");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, w.getBrand());
			pstmt.setInt(2, w.getPrice());
			pstmt.setInt(3, w.getWaterNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return result;
	}
	
	public ArrayList<Water> selectWaterList(Connection conn){
		ArrayList<Water> list = new ArrayList<Water>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectWaterList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Water(rset.getInt("water_no"),rset.getString("brand"),rset.getInt("price"))); // *****
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

}
