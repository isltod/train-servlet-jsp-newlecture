package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;

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
		List<Notice> notices = new ArrayList<Notice>();
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		String sql = "SELECT * FROM NOTICE";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getDate("REGDATE");
				String writer_id = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				Notice notice = new Notice(id, title, regDate, writer_id, hit, files, content);
				notices.add(notice);
			}

			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("notices", notices);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/notice/list.jsp");
		dispatcher.forward(req, resp);
	}
}
