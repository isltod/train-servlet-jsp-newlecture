package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add")
public class NoticeReg extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//요게 HTML에 한글이 나오게 만드는 코드... 
		resp.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String x_ = req.getParameter("x");
		String y_ = req.getParameter("y");
		
		// 기본값 주는 방법
		int x = 0;
		int y = 0;
		
		if (!x_.equals("")) x = Integer.parseInt(x_);
		if (!y_.equals("")) y = Integer.parseInt(y_);
		
		int result = x + y;
		out.printf("결과는 %d입니다.\n", result);
	}
}
