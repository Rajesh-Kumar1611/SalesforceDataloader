<%@page import="com.util.MyArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Enumeration"%>
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
<style type="text/css">
select.combobox {
    width: 182px;
}
</style>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
  <title>Field Mappings</title>
  <link rel="stylesheet" type="text/css" media="all" href="style.css">
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
  <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
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
        $(document).ready(function () 
		{
            $("#loading-div-background").css({ opacity: 0.8 });
           
        });

        function ShowProgressAnimation() 
		{
            $("#loading-div-background").show();
        }
		var requiredFields = ["Client ID", "Client Program Id", "Last Name", "Company"];
		function changeLabel(lableid, values, names)
		{
			if(values =="None")
			{
				$('#'+lableid).show();
				if (requiredFields.indexOf(names) != -1)
				{
					$('#'+lableid).css('color','red');
				}
				else
				{
					$('#'+lableid).css('color','#CCCC00');
				}
			}
			else
			{
				$('#'+lableid).hide();
			}
			checkValidation();
		}
		
		function checkValidation()
		{
			var selectedValues = [];
			$( "select" ).each(function() {
				
				var column = $( this ).val()
				
				if (column != "None")
				{
					if (selectedValues.indexOf(column) != -1)
					{
						var aaaa;
						alert('Column '+ '\"'+ column+  '\"'+' is already mapped');
					}
					else 
					{
						selectedValues.push(column);
					}
				}
			});
		}
		
    </script>

</head>
<body>
  
<form action="assignmentrule.jsp" method="post">
<center>
<table>

<%

StringBuilder fileData = new StringBuilder();
File f=new File(session.getAttribute("filename").toString());
BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
try {
	String data=bufferedReader.readLine();
	String [] csvheader=data.split(",");
	//List which contains all the headers of CSV file.
	java.util.List<String> list_csvheaders=new MyArrayList();
	for(String header:csvheader)
	{
		list_csvheaders.add(header.trim());
	}
	
	Maps.initilizeCredentials(session.getAttribute("username").toString(),session.getAttribute("password").toString());
	Set<String> fields=Maps.getFieldMapping(session.getAttribute("method").toString()).keySet();
	Integer createdIds = 0;
	for(String header:fields)
	{ createdIds++;
%>		
		<tr>
<% 		header=header.trim();
		if(list_csvheaders.contains(header))
		{
%>			
			<td><label style="font-weight: bold;"><%=header %></label></td>
			<td>
			<select name='<%=header%>' onchange="changeLabel(<%=createdIds%>,this.value,this.name)">
			<option>None</option>
<%			
		for(String s:list_csvheaders)
			{
				
				if(s.equalsIgnoreCase(header))
				{
%>				
				<option selected><%=s%></option>
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
			<td><label id='<%=createdIds%>' style='display:none'>* Mismatch</label></td>
			</select>
		</td>
<%		}
		else
		{
%>			<td><label style="font-weight: bold;"><%=header%></label></td>
			<td><select  onchange="changeLabel(<%=createdIds%>,this.value,this.name);" name='<%=header%>' class="required">
			<option selected>None</option>
<% 			
			for(String s:list_csvheaders)
			{
%>				
				<option><%=s %></option>
<% 			
			}
%>			
			</td>
			<%if(header.equals("Client ID")||header.equals("Client Program Id")||header.equals("Last Name")||header.equals("Company")) 
			{
			%>
			<td><label id='<%=createdIds%>' style="color:red">* Mismatch</label></td>
			<%}
			else
			{
				%>
			<td><label id='<%=createdIds%>' style="color:#CCCC00">* Mismatch</label></td>
			<% 
			}
			%>	
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
<tr><td colspan='3'><center><input type='submit' value='Proceed' onclick="ShowProgressAnimation();" class="btn btn-success btn-block"/></center></td></tr>
</table>
</center>
</form>
    <div id="loading-div-background">
    <div id="loading-div" class="ui-corner-all" >
     </div>
	</div>
</body>
</html>