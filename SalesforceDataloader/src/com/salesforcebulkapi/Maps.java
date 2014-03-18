package com.salesforcebulkapi;

import java.util.HashMap;
import java.util.Map;

public class Maps {

public static Map<String,String> getStandardFieldMapping()
{
	Map<String, String> standardfields = new HashMap<String,String>();
	//Standard Fields
	standardfields.put(	"Client ID", "IMS_Client_Id__c");
	standardfields.put(	"Client Program Id","IMS_Client_Program_Id__c"); 
	standardfields.put( "First Name","FirstName"  );
	standardfields.put( "Last Name","LastName" );
	standardfields.put( "Title", "Title");
	standardfields.put( "Title Type", "IMS_Title_Type__c");
	standardfields.put( "Company","Company"); 
	standardfields.put( "Contact Purchasing Role", "IMS_Contact_Role__c");
	standardfields.put( "Phone", "Phone");
	standardfields.put( "Direct Phone", "IMS_Direct_Phone__c");
	standardfields.put( "Fax", "Fax");
	standardfields.put( "Mobile","MobilePhone" );
	standardfields.put( "Email", "Email");
	//standardfields.put( "Address1", "Address1");
	//standardfields.put( "Address2","Address2"); 
	standardfields.put( "City", "City");
	standardfields.put( "State", "State");
	standardfields.put( "Postal", "PostalCode");
	standardfields.put( "Country", "Country");
	standardfields.put( "SIC CODE", "IMS_SIC_Code__c");
	standardfields.put( "SIC Description" ,"IMS_SIC_Description__c");
	standardfields.put( "DUNS No", "IMS_DUNS_No__c");
	standardfields.put( "NAICS Codes", "IMS_NAICS_Codes__c");
	standardfields.put( "Employee Size", "IMS_Employee_Size__c");
	standardfields.put(  "Revenue Size", "IMS_Revenue_Size__c");
	standardfields.put(  "Lead Source","LeadSource");
	standardfields.put(  "Record Source", "IMS_Record_Source__c");
	standardfields.put(  "Record Source Detail","IMS_Record_Source_Detail__c"); 
	standardfields.put( "Website", "Website");
	standardfields.put( "Company Type","IMS_Company_Type__c"); 
	standardfields.put( "Company Location Type","IMS_Location_Type__c"); 
	standardfields.put( "Industry", "Industry");
	standardfields.put( "Description","Description");

	return standardfields;
	
}

public static Map<String,String> getAffigientFieldMapping()
{
		//Affigient fields
		Map<String, String> affigient_fields = new HashMap<String,String>();
		affigient_fields.putAll(Maps.getStandardFieldMapping());
		affigient_fields.put("AF Database Size","AF_Database_Size__c");
		affigient_fields.put("AF Retention Requirement","AF_Retention_Requirement__c");
		affigient_fields.put("Backup Software","IMS_Backup_Software__c");
		affigient_fields.put("Govt Agency","IMS_Govt_Agency__c");
		affigient_fields.put("Govt.Office","IMS_Govt_Office__c");
		affigient_fields.put("AF Contractor","AF_Contractor__c");
		affigient_fields.put("Network","Network__c");
		affigient_fields.put("VISN","VISN__c");
		return affigient_fields;
}

public static void main(String[] args) {
	System.out.println(Maps.getAffigientFieldMapping());
}

}
