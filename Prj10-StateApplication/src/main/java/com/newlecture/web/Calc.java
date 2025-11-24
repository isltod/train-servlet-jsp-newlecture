package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/calc")
public class Calc extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//요게 HTML에 한글이 나오게 만드는 코드... 
		resp.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String operator = req.getParameter("operator");
		String v_ = req.getParameter("v");
		
//		ServletContext application = req.getServletContext();
		HttpSession session = req.getSession();
		
		int v = 0;
		if (!v_.equals("")) v = Integer.parseInt(v_);
		int result = 0;
		if (operator.equals("=")) {
//			int first_v = (Integer) application.getAttribute("1st_value");
			int first_v = (Integer) session.getAttribute("1st_value");
//			String first_op = (String) application.getAttribute("1st_operator");
			String first_op = (String) session.getAttribute("1st_operator");
			result = first_op.equals("+") ? first_v + v : first_v - v;
			out.printf("%s 결과는 %d입니다.\n", operator, result);
		} else {
//			application.setAttribute("1st_value", v);
//			application.setAttribute("1st_operator", operator);
			session.setAttribute("1st_value", v);
			session.setAttribute("1st_operator", operator);
		}
	}
}
