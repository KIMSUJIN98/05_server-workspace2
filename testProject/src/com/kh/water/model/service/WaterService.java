package com.kh.water.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;

import com.kh.water.model.dao.WaterDao;
import com.kh.water.model.vo.Water;

public class WaterService {
	
	public int insertWater(Water w) {
		Connection conn = getConnection();
		int result = new WaterDao().insertWater(conn, w);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public int updateWater(Water w) {
		Connection conn = getConnection();
		int result = new WaterDao().updateWater(conn, w);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public ArrayList<Water> selectWaterList(){
		Connection conn = getConnection();
		ArrayList<Water> list = new WaterDao().selectWaterList(conn);
		
		close(conn);
		return list;
	}

}
