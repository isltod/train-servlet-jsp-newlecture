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

@WebServlet("/calc2")
public class Calc2 extends HttpServlet {
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
			int second_v = (Integer) session.getAttribute("2nd_value");
//			String first_op = (String) application.getAttribute("1st_operator");
			String first_op = (String) session.getAttribute("1st_operator");
			String second_op = (String) session.getAttribute("2nd_operator");
			out.printf("%s, %d, %s, %d\n", first_op, first_v, second_op, second_v);
		} else {
//			application.setAttribute("1st_value", v);
//			application.setAttribute("1st_operator", operator);
			if (operator.equals("+")) {
				session.setAttribute("1st_value", v);
				session.setAttribute("1st_operator", operator);
			} else {
				session.setAttribute("2nd_value", v);
				session.setAttribute("2nd_operator", operator);
			}
		}
	}
}
