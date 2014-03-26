
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.salesforcebulkapi.Maps"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<form action="aftermap.jsp">
<center>
<table>
<%
	Maps maps=new Maps();
	List<Map<String,String>> idmap=maps.getRecordtypeAndAssignmentId();
%>
	<tr>
	<td><label for=recordtypeid>Record Type</label></td><td><select name='recordtypeid' style="width: 230px;" 
}>
	<%
		Set<String> recordtypeidkey=idmap.get(1).keySet();
		for(String s:recordtypeidkey)
		{
	%>
		<option><%=(s)%></option>
	<%
		}
	%>
	</select>
	<td>
	</tr>
	
	<tr>
	<td><label for=assignmentruleid>Assignment Rule</label></td><td><select name='assignmentruleid'>
		<%
			Set<String> asignemntrulekey=idmap.get(0).keySet();
			for(String s:asignemntrulekey)
			{
		%>
		<option><%=(s)%></option>
	<%
		}
		
	%>
	</select>
	<td>
	</tr>
	
		<td colspan="2">
			<center><input type='submit' value='insert'></center>
		</td>
	</tr>
	<% 
			Set<String> keys=request.getParameterMap().keySet();
			for(String s:keys)
			{
		%>		<tr>
				<td><input type="hidden" name='<%=s%>' value='<%=request.getParameter(s)%>'/></td>
				</tr>	
		<%					
			}
		%>
	</table>
	</center>
	</form>
</body>
</html>