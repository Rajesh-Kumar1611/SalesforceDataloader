<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
  <title>Login</title>
  <link rel="stylesheet" type="text/css" media="all" href="style.css">
  <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" charset="utf-8" src="js/jquery.leanModal.min.js"></script>
  <!-- jQuery plugin leanModal under MIT License http://leanmodal.finelysliced.com.au/ -->
  <style type="text/css">
  #box {
  width: 350px;
  padding: 15px 20px;
  background: ##F1F1F1;
  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
  -webkit-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
  -moz-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.5);
}
  </style>
</head>
<body>
<div>
<div id="box" style="margin-left: 40%;margin-top: 10%;">
    <form action="ConvertCSV" enctype="multipart/form-data" method="post">
    <label for="selection">Method :</label>
    <select name="selection">
    <option>Insert</option>
    <option>Update</option>
    <option>Upsert</option>
    </select>
    <br><label for="filename">Upload File:</label><input type="file" name="filename"/>
    <br><input type="submit" value="proceed"/>
    </form>
  </div>
</div>
</body>
</html>