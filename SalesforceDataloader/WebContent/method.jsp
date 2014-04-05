<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.salesforcebulkapi.Maps"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Upload CSV File</title>
  <link rel="stylesheet" type="text/css" media="all" href="style.css">
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
  <script src="http://code.jquery.com/jquery.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <style type="text/css">
  #box {
  width: 390px;
  height: 220px;
  padding: 15px 20px;
  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
  -webkit-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
  -moz-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
}

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
<div>
<div id="box" style="position: absolute;
    top:0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    margin-top: 30px;
    ">
    <form action="UploadFile" enctype="multipart/form-data" method="post">
    <label for="selection" style="font-weight: bold;">Client</label>
    <select name="selection" class="btn-block" style="display: initial;" >
    <%
    request.getSession().setAttribute("username","itstaff@invenio.com.isb");
	request.getSession().setAttribute("password","Th3t@1126");
    Maps map=new Maps();
    		
    Set<String> listofnames=map.getNames(session.getAttribute("username").toString(),session.getAttribute("password").toString());
	listofnames.remove("Standard Fields");   
    for(String name:listofnames)
    {
    %>
    <option><%=name %></option>
   <%  	
    }
    %>
    </select>
    <br><label for="filename" style="font-weight: bold;">Upload File:</label><input type="file" name="filename"/>
    <br><input type="submit" class="btn btn-success btn-block" value="proceed" onclick="ShowProgressAnimation();" style="margin-top: 30px;"/>
    </form>
    <div id="loading-div-background">
    <div id="loading-div" class="ui-corner-all" >
      <img src="images/loading.gif" alt="Please Wait..."/>
     </div>
	</div>
  </div>
</div>

</body>
</html>