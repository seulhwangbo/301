<%@page import="och11.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="../error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String id =request.getParameter("id");
	String password = request.getParameter("password");
	
	MemberDao md = new MemberDao();
	int result = md.check(id, password);
	if(result==1){
		session.setAttribute("id", id);
		response.sendRedirect("sessionMain.jsp");
	}else if (result == 0){
%>
	<script type="text/javascript">
		alert("암호가 틀려! 누구냐 ! ");
		history.go(-1);
	</script>
<%} else {%>
	<script type="text/javascript">
		alert("너 누구냐! 20년간 만두만 주고 ! ");
		history.back();
	</script>
<% } %>
</body>
</html>