package com.kh.water.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.water.model.vo.Water;

/**
 * Servlet implementation class InsertWaterController
 */
@WebServlet("/insert.wt")
public class InsertWaterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public InsertWaterController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 인코딩 작업
		request.setCharacterEncoding("utf-8");
		
		// 2) 요청시 전달값 뽑아서 변수 및 객체에 기록
		String brand = request.getParameter("brand");
		int price = Integer.parseInt(request.getParameter("price"));
		
		Water w = new Water();
		w.setBrand(brand);
		w.setPrice(price);
		
		int result = new WaterService().insertWater(w);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
