package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calc")
public class Calc extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//요게 HTML에 한글이 나오게 만드는 코드... 
		resp.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String[] num_ = req.getParameterValues("num");
		String operator = req.getParameter("operator");
		
		int result = 0;
		for (int i = 0; i < num_.length; i++) {
			int num = 0;
			if (!num_[i].equals("")) num = Integer.parseInt(num_[i]);
			if (operator.equals("덧셈")) {
				result += num;
				out.printf("%s, %d, %s, %d\n", operator, result, num_[i], num);
			} else {
				result -= num;
			}
		}
		out.printf("%s 결과는 %d입니다.\n", operator, result);
	}
}
