package com.newlecture.web.controller;

import java.io.IOException;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeService service = new NoticeService();
		int id = Integer.parseInt(request.getParameter("id"));
		Notice notice = service.getNotice(id);
		
		request.setAttribute("n", notice);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp");
		dispatcher.forward(request, response);
	}
}
