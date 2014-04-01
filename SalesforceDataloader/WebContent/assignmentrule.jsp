
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
  <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" charset="utf-8" src="js/jquery.leanModal.min.js"></script>
  <!-- jQuery plugin leanModal under MIT License http://leanmodal.finelysliced.com.au/ -->
  <style>
  #loading-div-background 
    {
        display:none;
        position:fixed;
        top:0;
        left:0;
        width:100%;
        height:100%;
     }
     
 #loading-div
    {
         width: 300px;
         height: 200px;
         text-align:center;
         position:absolute;
         left: 50%;
         top: 50%;
         margin-left:-150px;
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


<form action="viewreport.jsp" method="post">
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
			<center><input type='submit' value='insert' onclick="ShowProgressAnimation();"></center>
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
	    <div id="loading-div-background">
    <div id="loading-div" class="ui-corner-all" >
      <img src="images/loading.gif" alt="Please Wait..."/>
     </div>
	</div>
</body>
</html>