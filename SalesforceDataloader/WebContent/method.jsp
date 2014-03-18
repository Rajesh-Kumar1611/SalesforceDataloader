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
    <label for="selection">Client</label>
    <select name="selection" onchange="">
    <option>Affigient</option>
    <option>Avnet NetApp Healthcare</option>
    <option>Avnet NetApp</option>
    <option>Avnet Compucom</option>
    <option>BNA</option>
    </select>
    <br><label for="filename">Upload File:</label><input type="file" name="filename"/>
    <br><input type="submit" value="proceed"/>
    </form>
  </div>
</div>
</body>
</html>

<% 
Map<String, String> fieldsMap = new HashMap<String,String>();
//Standard Fields
fieldsMap.put("Client ID", "IMS_Client_Id__c");
fieldsMap.put("Client Program Id","IMS_Client_Program_Id__c"); 
fieldsMap.put( "First Name","FirstName"  );
fieldsMap.put( "Last Name","LastName" );
fieldsMap.put( "Title", "Title");
fieldsMap.put( "Title Type", "IMS_Title_Type__c");
fieldsMap.put( "Company","Company"); 
fieldsMap.put( "Contact Purchasing Role", "IMS_Contact_Role__c");
fieldsMap.put( "Phone", "Phone");
fieldsMap.put( "Direct Phone", "IMS_Direct_Phone__c");
fieldsMap.put( "Fax", "Fax");
fieldsMap.put( "Mobile","MobilePhone" );
fieldsMap.put( "Email", "Email");
//fieldsMap.put( "Address1", "Address1");
//fieldsMap.put( "Address2","Address2"); 
fieldsMap.put( "City", "City");
fieldsMap.put( "State", "State");
fieldsMap.put( "Postal", "PostalCode");
fieldsMap.put( "Country", "Country");
fieldsMap.put( "SIC CODE", "IMS_SIC_Code__c");
fieldsMap.put( "SIC Description" ,"IMS_SIC_Description__c");
fieldsMap.put( "DUNS No", "IMS_DUNS_No__c");
fieldsMap.put( "NAICS Codes", "IMS_NAICS_Codes__c");
fieldsMap.put( "Employee Size", "IMS_Employee_Size__c");
fieldsMap.put(  "Revenue Size", "IMS_Revenue_Size__c");
fieldsMap.put(  "Lead Source","LeadSource");
fieldsMap.put(  "Record Source", "IMS_Record_Source__c");
fieldsMap.put(  "Record Source Detail","IMS_Record_Source_Detail__c"); 
fieldsMap.put( "Website", "Website");
fieldsMap.put( "Company Type","IMS_Company_Type__c"); 
fieldsMap.put( "Company Location Type","IMS_Location_Type__c"); 
fieldsMap.put( "Industry", "Industry");
fieldsMap.put( "Description","Description");
//Required fields
fieldsMap.put("Client ID","IMS_Client_Id__c" );
fieldsMap.put("Client Program Id","IMS_Client_Program_Id__c" );
fieldsMap.put("Last Name","LastName" );
fieldsMap.put("Company","Company" );
//Affigient fields
fieldsMap.put("AF Database Size","AF_Database_Size__c");
fieldsMap.put("AF Retention Requirement","AF_Retention_Requirement__c");
fieldsMap.put("Backup Software","IMS_Backup_Software__c");
fieldsMap.put("Govt Agency","IMS_Govt_Agency__c");
fieldsMap.put("Govt.Office","IMS_Govt_Office__c");
fieldsMap.put("AF Contractor","AF_Contractor__c");
fieldsMap.put("Network","Network__c");
fieldsMap.put("VISN","VISN__c");
%>