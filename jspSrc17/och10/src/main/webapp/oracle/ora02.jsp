<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String deptno = request.getParameter("deptno");
	String driver = "oracle.jdbc.driver.OracleDriver";	
	String url    = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String sql     = "select * from dept where deptno = " +deptno;
	Class.forName(driver);
	Connection conn = DriverManager.getConnection(url,"scott","tiger");
	Statement  stmt = conn.createStatement();
	ResultSet  rs   = stmt.executeQuery(sql);	
	System.out.println("sql->" + sql);
	
	if(rs.next()){
		String dname = rs.getString("dname");
		String loc   = rs.getString(3);
		out.println("부서코드: " + deptno + "<p>");
		out.println("부서명: " + dname + "<p>");
		out.println("근무지 : " + loc + "<p>");
	}else out.println("그게 무슨 부서야 없는데");
	rs.close();
	stmt.close();
	conn.close();
%>
</body>
</html>