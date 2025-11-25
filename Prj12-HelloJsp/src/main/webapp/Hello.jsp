<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String cnt_ = request.getParameter("cnt");
	int cnt = 5;
	if (cnt_ != null && !cnt_.equals("")) cnt = Integer.parseInt(cnt_);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% for (int i = 0; i < cnt; i++) { %>
	헬로우 JSP!!!<br>
	<% } %>
</body>
</html>