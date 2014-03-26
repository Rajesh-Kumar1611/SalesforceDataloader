<%@page import="java.util.TreeMap"%>
<%@page import="com.salesforcebulkapi.BulkLoader"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
  <title>Login</title>
  <link rel="stylesheet" type="text/css" media="all" href="style.css">
  <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" charset="utf-8" src="js/jquery.leanModal.min.js"></script>
  <!-- jQuery plugin leanModal under MIT License http://leanmodal.finelysliced.com.au/ -->
<title>Insert title here</title>
</head>
<body>
<%
	Map<String,String[]> m=request.getParameterMap();
//	System.out.print("Param Map"+m);
BulkLoader.getMap(m,session.getAttribute("filename").toString(),session.getAttribute("username").toString(),session.getAttribute("password").toString(),session.getAttribute("method").toString());
%>
</body>
</html>