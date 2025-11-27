package com.newlecture.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeService service = new NoticeService();
		List<Notice> notices = new ArrayList<Notice>();
		notices = service.getNoticeList();
		req.setAttribute("notices", notices);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/notice/list.jsp");
		dispatcher.forward(req, resp);
	}
}
