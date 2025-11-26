package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.newlecture.web.entity.Notice;

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
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");

			Integer id = Integer.parseInt(request.getParameter("id"));
			String sql = "SELECT * FROM NOTICE WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			
			String title = rs.getString("TITLE");
			Date regDate = rs.getDate("REGDATE");
			String writer_id = rs.getString("WRITER_ID");
			int hit = rs.getInt("HIT");
			String files = rs.getString("FILES");
			String content = rs.getString("CONTENT");
			rs.close();

			Notice notice = new Notice(id, title, regDate, writer_id, hit, files, content);
			request.setAttribute("n", notice);
			
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp");
		dispatcher.forward(request, response);
	}
}
