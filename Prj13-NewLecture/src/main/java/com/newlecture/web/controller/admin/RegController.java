package com.newlecture.web.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50,
		maxRequestSize = 1024*1024*50*5
)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Collection<Part> parts = req.getParts();
		StringBuilder builder = new StringBuilder();
		for (Part part : parts) {
			if (!part.getName().equals("file")) continue;
			if (part.getSize() == 0) continue;
			
			String saveDirName = req.getServletContext().getRealPath("/upload");
			File saveDir = new File(saveDirName);
			if (!saveDir.exists()) saveDir.mkdirs();
			
			String fileName = part.getSubmittedFileName();
			builder.append(fileName + ",");
			String filePath = saveDirName + File.separator + fileName;
			System.out.println(filePath);
			
			InputStream is = part.getInputStream();
			FileOutputStream fos = new FileOutputStream(filePath);
			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = is.read(buf)) != -1)
				fos.write(buf, 0, size);
			is.close();
			fos.close();
		}
		builder.deleteCharAt(builder.length() - 1);
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String isOpen = req.getParameter("open");
		boolean pub = false;
		if (isOpen != null) pub = true;
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriter_id("newlec");
		notice.setFiles(builder.toString());
		
		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);
		resp.sendRedirect("list");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp");
		dispatcher.forward(req, resp);
	}
}
