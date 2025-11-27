package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;

public class NoticeService {
	public List<Notice> getNoticeList() {
		return getNoticeList("title", "", 1);
	}
	public List<Notice> getNoticeList(int page) {
		return getNoticeList("title", "", page);
	}
	public List<Notice> getNoticeList(String field, String query, int page) {
		List<Notice> notices = new ArrayList<Notice>();
		String sql = "SELECT * FROM ( "
				+ "    SELECT RowNum Num, n.*  "
				+ "    FROM (SELECT * FROM notice WHERE " + field + " LIKE ? ORDER BY regdate DESC) n  "
				+ ") "
				+ "WHERE Num BETWEEN ? AND ?";
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + query + "%");
			statement.setInt(2, 1 + (page - 1) * 3);
			statement.setInt(3, page * 3);
			ResultSet rs = statement.executeQuery();

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
		return notices;
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}
	public int getNoticeCount(String field, String query) {
		return 0;
	}
	
	public Notice getNotice(int id) {
		String sql = "SELECT * FROM notice WHERE id=?";
		return null;
	}
	public Notice getNextNotice(int id) {
		String sql = "SELECT id FROM notice "
				+ "WHERE regdate > ( "
				+ "    SELECT regdate FROM notice WHERE id=? "
				+ ") "
				+ "AND RowNum=1";
		return null;
	}
	public Notice getPrevNotice(int id) {
		String sql = "SELECT id FROM (SELECT id FROM notice ORDER BY regdate DESC) "
				+ "WHERE regdate <= ( "
				+ "    SELECT regdate FROM notice WHERE id=3 "
				+ ") "
				+ "AND RowNum=1";
		return null;
	}
}
