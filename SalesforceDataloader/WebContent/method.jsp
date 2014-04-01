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
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
  <title>Upload CSV File</title>
  <link rel="stylesheet" type="text/css" media="all" href="style.css">
  <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" charset="utf-8" src="js/jquery.leanModal.min.js"></script>
  <!-- jQuery plugin leanModal under MIT License http://leanmodal.finelysliced.com.au/ -->
  <style type="text/css">
  #box {
  width: 350px;
  height: 180px;
  padding: 15px 20px;
/*   background: #F1F1F1; */
  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
  -webkit-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
  -moz-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
}

 
  </style>
  
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
<div>
<div id="box" style="margin-left: 40%;margin-top: 10%;">
    <form action="UploadFile" enctype="multipart/form-data" method="post">
    <label for="selection" style="font-weight: bold;">Client</label>
    <select name="selection" style="margin-left: 33px;">
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
    <br><input type="submit" value="proceed" onclick="ShowProgressAnimation();" style=" margin-top: 77px;margin-left: 118px;"/>
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