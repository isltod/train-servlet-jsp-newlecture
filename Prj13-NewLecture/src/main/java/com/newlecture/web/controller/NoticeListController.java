package com.newlecture.web.controller;

import java.io.IOException;
import java.util.List;

import com.newlecture.web.entity.NoticeView;
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
		String field = "title";
		String field_ = req.getParameter("f");
		if (field_ != null && !field_.equals("")) field = field_;
		String query = "";
		String query_ = req.getParameter("q");
		if (query_ != null && !query_.equals("")) query = query_;
		int page = 1;
		String page_ = req.getParameter("p");
		if (page_ != null && !page_.equals("")) page = Integer.parseInt(page_);
		
		NoticeService service = new NoticeService();
		List<NoticeView> notices = service.getNoticeList(field, query, page);
		int count = service.getNoticeCount(field, query);
		
		req.setAttribute("notices", notices);
		req.setAttribute("count", count);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/notice/list.jsp");
		dispatcher.forward(req, resp);
	}
}
