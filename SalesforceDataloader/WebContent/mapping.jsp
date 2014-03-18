<%@page import="java.io.File"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.salesforcebulkapi.Maps"%>
<%@page import="java.util.Set"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="aftermap.jsp">
<table>

<%

StringBuilder fileData = new StringBuilder();
File f=new File(session.getAttribute("filename").toString());
BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
try {
	String data=bufferedReader.readLine();
	String [] csvheader=data.split(",");
	java.util.List<String> list_csvheaders=new ArrayList<String>();
	for(String header:csvheader)
	{
		list_csvheaders.add(header.trim());
	}
	
	Set<String> fields=Maps.getAffigientFieldMapping().keySet();
	
	for(String header:fields)
	{
%>		
		<tr>
<% 		
		header=header.trim();
		if(list_csvheaders.contains(header))
		{
%>			
			<td><label><%=header %></label>
			</td><td><select name='<%=header%>'>
<%			
			for(String s:list_csvheaders)
			{
				if(s.equals(header))
				{
%>
				<option selected><%=s %></option>
<% 	
				}
				else
				{
%>					
				<option><%=s %></option>
<%			
				}
			}
%>
			</select>
			</td>
<%	
		}
		else
		{
%>			
			<td><label><%=header%></label></td>
			<td><select name='<%=header%>'>
			<option selected>**None**</option>
<% 			
			for(String s:list_csvheaders)
			{
%>				
				<option><%=s %></option>
<% 			
			}
%>			
			</td>
			<td><font color='red'>Mismatch</font></td>
			</select>
<% 		
			//response.getOutputStream().println("<br>");
		}
%>		
		</tr>
		
<%		
	}

} catch (IOException e) {
  e.printStackTrace();
}
%>
<tr><td colspan='3'><center><input type='submit' value='Upload Data'/></center></td></tr>
</table>

</form>

</body>
</html>