package com.newlecture.web.controller.admin;

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

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] openIds = req.getParameterValues("open-id");
		String[] delIds = req.getParameterValues("del-id");
		String cmd = req.getParameter("cmd");
		
		NoticeService service = new NoticeService();
		int result = 0;
		switch (cmd) {
		case "일괄공개":
			result = service.pubNoticeAll(openIds);
		case "일괄삭제":
			result = service.deleteNoticeAll(delIds);
		}
		
		resp.sendRedirect("list");
	}
	
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
		List<NoticeView> notices = service.getNoticeViewList(field, query, page);
		int count = service.getNoticeCount(field, query);
		
		req.setAttribute("notices", notices);
		req.setAttribute("count", count);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp");
		dispatcher.forward(req, resp);
	}
}
