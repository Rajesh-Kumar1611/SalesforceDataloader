
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
<title>Assignment Rules</title>
<link rel="stylesheet" type="text/css" media="all" href="style.css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<style>
#box {
	width: 366px;
	height: 220px;
	padding: 15px 20px;
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
	border-radius: 6px;
	-webkit-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
	-moz-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
	box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
}

#loading-div-background {
	display: none;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}

#loading-div {
	width: 300px;
	height: 200px;
	text-align: center;
	position: absolute;
	left: 50%;
	top: 50%;
	margin-left: -150px;
	margin-top: -100px;
}
</style>
<script type="text/javascript">
        $(document).ready(function () {
            $("#loading-div-background").css({ opacity: 0.8 });
           
        });

        function ShowProgressAnimation() {
        
            $("#loading-div-background").show();

        }

    </script>
</head>

<body>
	<div id="box"
		style="position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto; margin-top: 30px;">

		<form action="viewreport.jsp" method="post">
			<div>
				<table>
					<%
	Maps maps=new Maps();
	List<Map<String,String>> idmap=maps.getRecordtypeAndAssignmentId();
%>
					<tr>
						<td><label for=recordtypeid>Record Type</label></td>
						<td><select name='recordtypeid'>
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
						<td><label for=assignmentruleid>Assignment Rule</label></td>
						<td><select name='assignmentruleid'>
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
						<center>
							<input type='submit' value='Insert Records'
								onclick="ShowProgressAnimation();"
								class="btn btn-primary btn-block" style="margin-top: 80px;"></input>
						</center>
					</td>
					</tr>
					<% 
			Set<String> keys=request.getParameterMap().keySet();
			for(String s:keys)
			{
		%>
					<tr>
						<td><input type="hidden" name='<%=s%>'
							value='<%=request.getParameter(s)%>' /></td>
					</tr>
					<%					
			}
		%>
				</table>

			</div>
		</form>

		<div id="loading-div-background">
			<div id="loading-div" class="ui-corner-all">
				<img src="images/loading.gif" alt="Please Wait..." />
			</div>
		</div>
	</div>

</body>
</html>