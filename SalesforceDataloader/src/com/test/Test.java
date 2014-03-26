package com.test;

public class Test {
	public static void main(String[] args) {
		String s="Client ID-IMS_Client_Id__c,Client Program Id-IMS_Client_Program_Id__c,First Name-FirstName,Last Name-LastName,Title-Title,Title Type-IMS_Title_Type__c,Company-Company,Contact Purchasing Role-IMS_Contact_Role__c,Phone-Phone,Direct Phone-IMS_Direct_Phone__c,Fax-Fax,Mobile-MobilePhone,Email-Email,Address1,Address2,City-City,State-State,Postal-PostalCode,Country-Country,SIC CODE-IMS_SIC_Code__c,IC Description-IMS_SIC_Description__c,DUNS No-IMS_DUNS_No__c,NAICS Codes-IMS_NAICS_Codes__c,Employee Size-IMS_Employee_Size__c,Revenue Size-IMS_Revenue_Size__c,Lead Source-LeadSource,Record Source-IMS_Record_Source__c,Record Source Detail-IMS_Record_Source_Detail__c,Website-Website,Company Type-IMS_Company_Type__c,Company Location Type-IMS_Location_Type__c,Industry-Industry,Description-Description";
		 String [] pairs=s.toString().split(",");
         for(String pair:pairs)
         {
         	
         	if(pair.contains("-")&&!pair.trim().equals(""))
         	{
         	String[] key_val=pair.split("-");
         	if(key_val.length==2)
         	{
         	System.out.println(key_val[0]+"--"+ key_val[1]);
         	}
         	}
         	
         }
}
}
