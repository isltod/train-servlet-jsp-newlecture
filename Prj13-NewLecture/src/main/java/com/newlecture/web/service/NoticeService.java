package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;

public class NoticeService {
	
	public int removeNoticeAll() {
		return 0;
	}
	
	public int pubNoticeAll(String[] openIds) {
		return 0;
	}
	
	public int insertNotice(Notice notice) {
		String sql = "INSERT INTO notice(title, content, writer_id, pub) VALUES (?, ?, ?, ?)";
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, notice.getTitle());
			statement.setString(2, notice.getContent());
			statement.setString(3, notice.getWriter_id());
			statement.setBoolean(4, notice.getPub());
			int result = statement.executeUpdate();

			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deleteNotice(int id) {
		return 0;
	}
	
	public int updateNotice(Notice notice) {
		return 0;
	}
	
	public List<Notice> getNoticeNewestList() {
		return null;
	}
	
	public List<NoticeView> getNoticeViewList() {
		return getNoticeViewList("title", "", 1);
	}
	
	public List<NoticeView> getNoticeViewList(int page) {
		return getNoticeViewList("title", "", page);
	}
	
	public List<NoticeView> getNoticeViewList(String field, String query, int page) {
		List<NoticeView> notices = new ArrayList<>();
		String sql = "SELECT * FROM ( "
				+ "    SELECT RowNum Num, n.*  "
				+ "    FROM (SELECT * FROM notice_view WHERE " + field + " LIKE ? ORDER BY regdate DESC) n  "
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
				int cmtCount = rs.getInt("CMT_COUNT");
				boolean pub = rs.getBoolean("PUB");
				
				NoticeView notice = new NoticeView(id, title, regDate, writer_id, hit, files, pub, cmtCount);
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
		int count = 0;
		String sql = "SELECT COUNT(id) COUNT FROM notice WHERE " + field + " LIKE ?";
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + query + "%");
			ResultSet rs = statement.executeQuery();

			if (rs.next()) count = rs.getInt("count");
			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public Notice getNotice(int id) {
		Notice notice = null;
		String sql = "SELECT * FROM notice WHERE id=?";
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int getId = rs.getInt("id");
				String title = rs.getString("title");
				Date regDate = rs.getDate("regDate");
				String writer_id = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				boolean pub = rs.getBoolean("pub");
				notice = new Notice(getId, title, regDate, writer_id, hit, files, content, pub);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notice;
	}
	
	public Notice getNextNotice(int id) {
		Notice notice = null;
		String sql = "SELECT id FROM notice "
				+ "WHERE regdate > ( "
				+ "    SELECT regdate FROM notice WHERE id=? "
				+ ") "
				+ "AND RowNum=1";
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int getId = rs.getInt("id");
				String title = rs.getString("title");
				Date regDate = rs.getDate("regDate");
				String writer_id = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				boolean pub = rs.getBoolean("pub");
				notice = new Notice(getId, title, regDate, writer_id, hit, files, content, pub);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notice;
	}
	
	public Notice getPrevNotice(int id) {
		Notice notice = null;
		String sql = "SELECT id FROM (SELECT id FROM notice ORDER BY regdate DESC) "
				+ "WHERE regdate <= ( "
				+ "    SELECT regdate FROM notice WHERE id=? "
				+ ") "
				+ "AND RowNum=1";
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int getId = rs.getInt("id");
				String title = rs.getString("title");
				Date regDate = rs.getDate("regDate");
				String writer_id = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				boolean pub = rs.getBoolean("pub");
				notice = new Notice(getId, title, regDate, writer_id, hit, files, content, pub);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notice;
	}

	public int deleteNoticeAll(String[] delIds) {
		String params = "";
		for (int i = 0; i < delIds.length; i++) {
			params += delIds[i];
			if (i < delIds.length - 1) params += ", ";
		}
		String sql = "DELETE notice WHERE id IN (" + params + ")";
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(url, "newlec", "1111");
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(sql);

			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
