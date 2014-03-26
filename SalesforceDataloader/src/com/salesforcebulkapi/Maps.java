package com.salesforcebulkapi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class Maps {

static Map<String, String> standardfieldmap=new HashMap<String, String>();
static String sf_username,sf_password;

public static Map<String, String> getStandardfieldmap() {
	return standardfieldmap;
}
public static void setStandardfieldmap(Map<String, String> standardfieldmap) {
	Maps.standardfieldmap = standardfieldmap;
}
public static String getSf_username() {
	return sf_username;
}
public static void setSf_username(String sf_username) {
	Maps.sf_username = sf_username;
}
public static String getSf_password() {
	return sf_password;
}
public static void setSf_password(String sf_password) {
	Maps.sf_password = sf_password;
}
public static void initilizeCredentials(String username,String password)
{
sf_username=username;
sf_password=password;
}
private  PartnerConnection getPartnerConnection() throws ConnectionException
{

		    ConnectorConfig partnerConfig = new ConnectorConfig();
		    partnerConfig.setUsername(sf_username);
		    partnerConfig.setPassword(sf_password);
		    partnerConfig.setAuthEndpoint("https://test.salesforce.com/services/Soap/u/17.0");
		    // Creating the connection automatically handles login and stores
		    // the session in partnerConfig
		    PartnerConnection partnerconnection=new PartnerConnection(partnerConfig);
		    return partnerconnection;
}

public  Set<String> getNames(String username,String password) throws ConnectionException
{
	setSf_username(username);
	setSf_password(password);
	Set<String> listofnames=new HashSet<String>();
	PartnerConnection partnerconnection=getPartnerConnection();
	QueryResult result = partnerconnection.query("select Name from ClientColumn__c");
	for (SObject object : result.getRecords()) {
	if(object!=null)
	{
		listofnames.add(object.getField("Name").toString());
	}
		
	}
	return listofnames;
	}

public static void main(String[] args) {
	Maps maps=new Maps();
	try {
		System.out.println(maps.getNames("itstaff@invenio.com.isb", "Th3t@1126"));
	} catch (ConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public List<Map<String,String>> getRecordtypeAndAssignmentId() throws ConnectionException
{
	
	List<Map<String,String>> listofid=new ArrayList<>();
	PartnerConnection partnerconnection=getPartnerConnection();
	 if(partnerconnection!=null)
	  {
	        try {
	        		List<String> fetchidfrom=new ArrayList<>();
	        		fetchidfrom.add("AssignmentRule");
	        		fetchidfrom.add("RecordType");
	        		
	        		for(int i=0;i<fetchidfrom.size();i++)
	        		{
	        		Map<String, String> idmap=new HashMap<>();
	        	    QueryResult result = partnerconnection.query("select Id, Name from " +fetchidfrom.get(i)+ " where SobjectType = 'Lead'");
	        	    
	                for (SObject object : result.getRecords()) {
	                if(object!=null)
	                {
	                idmap.put(object.getField("Name").toString(), object.getField("Id").toString());	
	                	
	                }
	                }
	                
	                listofid.add(idmap);
	        		}
	        }
	        catch(Exception ex)
	        {
	        	
	        }
	  }
	 return listofid;

}


private  Map<String,String> getFieldMap(String username,String password,String mappingname) throws ConnectionException
{
	PartnerConnection partnerconnection=getPartnerConnection();
	Map<String, String> fieldmap=new HashMap<String, String>();
	 if(partnerconnection!=null)
	  {
		 StringBuffer data=new StringBuffer();
	        try {
	        	    QueryResult result = partnerconnection.query("select Column1__c,Column2__c,Column3__c,Column4__c from ClientColumn__c where Name='"+mappingname+"'");
	                for (SObject object : result.getRecords()) {
	                if(object!=null)
	                {
	                	String sf_data=null;
	                	sf_data = object.getField("Column1__c") != null ? object.getField("Column1__c").toString() : null ;
	                  	sf_data = object.getField("Column2__c") != null ?  sf_data + "," + object.getField("Column2__c").toString() : sf_data;
	                	sf_data = object.getField("Column3__c") != null ? sf_data + "," + object.getField("Column3__c").toString() : sf_data;
	                	sf_data = object.getField("Column4__c") != null ?  sf_data + "," + object.getField("Column4__c").toString() : sf_data;
	                	data.append(sf_data);
	                }
	                }
	                
	                //data.deleteCharAt(data.length()-1);
	                 String [] pairs=data.toString().split(",");
	                for(String pair:pairs)
	                {
	                   	if(pair.contains("-")&&!(pair.trim().equals("")))
	                	{
	                	String[] key_val=pair.split("-");
	                	if(key_val.length==2)
	                	{
	                	fieldmap.put(key_val[0].trim(), key_val[1].trim());
	                	}
	                	}
	                	
	                }
	            
	        } catch (Exception e) {
	            System.out.println("error:::" + e);
	        }
	        
	  }
	 return fieldmap;
}

public static Map<String,String> getFieldMapping(String method) throws ConnectionException
{
Maps map=new Maps();
if(getStandardfieldmap()==null||getStandardfieldmap().size()<=0)
{
	setStandardfieldmap(map.getFieldMap(getSf_username(), getSf_password(), "Standard Fields"));
}

Map<String, String> affigient_fields =map.getFieldMap(getSf_username(),getSf_password(),method);
if(getStandardfieldmap()!=null)
{
affigient_fields.putAll(getStandardfieldmap());
}
return affigient_fields;

}


}
