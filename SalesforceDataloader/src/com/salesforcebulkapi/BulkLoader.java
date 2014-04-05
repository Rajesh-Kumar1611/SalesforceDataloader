package com.salesforcebulkapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.sforce.async.AsyncApiException;
import com.sforce.async.BatchInfo;
import com.sforce.async.BatchStateEnum;
import com.sforce.async.CSVReader;
import com.sforce.async.ConcurrencyMode;
import com.sforce.async.ContentType;
import com.sforce.async.JobInfo;
import com.sforce.async.JobStateEnum;
import com.sforce.async.OperationEnum;
import com.sforce.async.RestConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
public class BulkLoader {

static Map<String,String> displayname_to_salesforceapi_fieldsMap=new TreeMap<String,String>();
static Map<String, String> displayname_to_csvfileheader_fieldsMap = new TreeMap<String,String>();
public static void main(String[] args) throws AsyncApiException,
      ConnectionException, IOException {
   	  BulkLoader bl=new BulkLoader();
	  bl.getRestConnection("itstaff@invenio.com.isb", "Th3t@1126");
    //example.run();
  }
  
  public static String getMap(Map map,String file,String username,String password,String method) throws ConnectionException
  {
	  String jobId=null;
	  Maps.setSf_username(username);
	  Maps.setSf_password(password);
	  displayname_to_salesforceapi_fieldsMap.putAll(Maps.getFieldMapping(method));
	  displayname_to_csvfileheader_fieldsMap=map;
	  BulkLoader example = new BulkLoader();
	  try {
		jobId=example.runJob("Lead", username,password ,file);
	} catch (AsyncApiException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return jobId;
  }
  
  /**
   * Creates a Bulk API job and uploads batches for a CSV file.
   */
  public String runJob(String sobjectType, String userName, String password, String sampleFileName) throws AsyncApiException, ConnectionException, IOException {
    RestConnection connection = getRestConnection(userName, password);
    JobInfo job = createJob(sobjectType, connection);
    List<BatchInfo> batchInfoList = createBatchesFromCSVFile(connection, job,sampleFileName);
    closeJob(connection, job.getId());
    String jobId=awaitCompletion(connection, job, batchInfoList);
    checkResults(connection, job, batchInfoList);
    return jobId;
  }

  /**
   * Gets the results of the operation and checks for errors.
   */
  private String checkResults(RestConnection connection, JobInfo job,
      List<BatchInfo> batchInfoList) throws AsyncApiException, IOException {
      
      StringBuffer log=new StringBuffer();
    // batchInfoList was populated when batches were created and submitted
    for (BatchInfo b : batchInfoList) {
      CSVReader rdr = new CSVReader(connection.getBatchResultStream(
          job.getId(), b.getId()));
      List<String> resultHeader = rdr.nextRecord();
      int resultCols = resultHeader.size();

      List<String> row;
      while ((row = rdr.nextRecord()) != null) {
        Map<String, String> resultInfo = new TreeMap<String, String>();
        for (int i = 0; i < resultCols; i++) {
          resultInfo.put(resultHeader.get(i), row.get(i));
        }
        boolean success = Boolean.valueOf(resultInfo.get("Success"));
        boolean created = Boolean.valueOf(resultInfo.get("Created"));
        String id = resultInfo.get("Id");
        String error = resultInfo.get("Error");
        if (success && created) {
            log.append("Created row with id " + id+"\n");
          System.out.println("Created row with id " + id);
        } else if (!success) {
            log.append("Failed with error: " + error+"\n");
          System.out.println("Failed with error: " + error);
        }
      }
    }
    return log.toString();
  }

  /**
   * Closes the job
   */
  private void closeJob(RestConnection connection, String jobId)
      throws AsyncApiException {
    JobInfo job = new JobInfo();
    job.setId(jobId);
    job.setState(JobStateEnum.Closed);
    connection.updateJob(job);
  }

  /**
   * Wait for a job to complete by polling the Bulk API.
   */
  private String awaitCompletion(RestConnection connection, JobInfo job,
    List<BatchInfo> batchInfoList) throws AsyncApiException {
	  String JobId=null;
    long sleepTime = 0L;
    Set<String> incomplete = new TreeSet<String>();
    for (BatchInfo bi : batchInfoList) {
      incomplete.add(bi.getId());
    }
    while (!incomplete.isEmpty()) {
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
      }
      System.out.println("Awaiting results..." + incomplete.size());
      sleepTime = 10000L;
      BatchInfo[] statusList = connection.getBatchInfoList(job.getId())
          .getBatchInfo();
      for (BatchInfo b : statusList) {
        if (b.getState() == BatchStateEnum.Completed
            || b.getState() == BatchStateEnum.Failed) {
          if (incomplete.remove(b.getId())) {
        	  JobId=b.getJobId();
            System.out.println("BATCH STATUS:\n" + b);
            
          }
        }
      }
    }
    return JobId;
  }

  /**
   * Create a new job using the Bulk API.
   */
  private JobInfo createJob(String sobjectType, RestConnection connection)
      throws AsyncApiException {
    JobInfo job = new JobInfo();
    job.setObject(sobjectType);
    job.setOperation(OperationEnum.insert);
    job.setContentType(ContentType.CSV);
    job.setConcurrencyMode(ConcurrencyMode.Serial);
    job = connection.createJob(job);
     
    System.out.println(job);
    return job;
  }

  /**
   * Create the RestConnection used to call Bulk API operations.
   */
  
  private RestConnection getRestConnection(String userName, String password)
  	
      throws ConnectionException, AsyncApiException {
	  
	 System.out.println("username"+userName+" Password"+password); 
    ConnectorConfig partnerConfig = new ConnectorConfig();
    partnerConfig.setUsername(userName);
    partnerConfig.setPassword(password);
    partnerConfig.setAuthEndpoint("https://test.salesforce.com/services/Soap/u/17.0");
    // Creating the connection automatically handles login and stores
    // the session in partnerConfig
    new PartnerConnection(partnerConfig);
    
    // When PartnerConnection is instantiated, a login is implicitly
    // executed and, if successful,
    // a valid session is stored in the ConnectorConfig instance.
    // Use this key to initialize a RestConnection:
    ConnectorConfig config = new ConnectorConfig();
    config.setSessionId(partnerConfig.getSessionId());
    // The endpoint for the Bulk API service is the same as for the normal
    // SOAP uri until the /Soap/ part. From here it's '/async/versionNumber'
    String soapEndpoint = partnerConfig.getServiceEndpoint();
    String apiVersion = "17.0";
    String restEndpoint = soapEndpoint.substring(0, soapEndpoint
        .indexOf("Soap/"))
        + "async/" + apiVersion;
    config.setRestEndpoint(restEndpoint);
    // This should only be false when doing debugging.
    config.setCompression(true);
    // Set this to true to see HTTP requests and responses on stdout
    config.setTraceMessage(false);
    RestConnection connection = new RestConnection(config);
    return connection;
  }
  /**
   * Create and upload batches using a CSV file. The file into the appropriate
   * size batch files.
 * @throws ConnectionException 
   */
  private List<BatchInfo> createBatchesFromCSVFile(RestConnection connection,
      JobInfo jobInfo, String csvFileName) throws IOException,
      AsyncApiException, ConnectionException {
	  
	  String assignmentkey=null;
	  String recordtypekey=null;
	  String assignmentId=null;
	  String recordtypeid=null;
	  
    List<BatchInfo> batchInfos = new ArrayList<BatchInfo>();
    // read the CSV header row
     Set<String> csvfields=new TreeSet<String>();
	 Set s = displayname_to_csvfileheader_fieldsMap.entrySet();
	 Map<String,String> newmap= new TreeMap<String,String>();
	 Map<String,String> csvfieldtodisplaymap= new TreeMap<String,String>();
	  
	 Iterator it = s.iterator();
	 while(it.hasNext()){
	 Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();
	 String key=entry.getKey();
	 String[] value=entry.getValue();
	 if(key.equals("assignmentruleid"))
	 {
		 assignmentkey=value[0].toString();
	 }else if(key.equals("recordtypeid"))
	 {
		 recordtypekey=value[0].toString();
	 }
	 else
	 {
		 if(!(value[0].equals("None")))
		 {
		 csvfieldtodisplaymap.put(value[0],key); 
		 newmap.put(value[0], displayname_to_salesforceapi_fieldsMap.get(key));
		 csvfields.add(value[0]);
		 }
	 }
	

	 }
	 Maps map=new Maps();
	 List<Map<String,String>> ids=map.getRecordtypeAndAssignmentId();
	 assignmentId=ids.get(0).get(assignmentkey);
	 recordtypeid=ids.get(1).get(recordtypekey);

		ICsvMapReader mapReader = null;
		//FileWriter writer=null;
		FileOutputStream writer=null;
		File tmpFile = File.createTempFile("SFBulkCSV", ".csv");	
     try {
    	 int maxBytesPerBatch = 10000000; // 10 million bytes per batch
         int maxRowsPerBatch = 10000; // 10 thousand rows per batch	
         int currentBytes = 0;
         int currentLines = 0;
         writer=new FileOutputStream(tmpFile);
         mapReader = new CsvMapReader(new FileReader(csvFileName), CsvPreference.STANDARD_PREFERENCE);
     	StringBuffer fileheader=new StringBuffer();
     	for(String field:csvfields)
     	 {
     			fileheader.append(newmap.get(field));
     			fileheader.append(",");
     	 }
     		fileheader.append("AssignmentRuleId__c,RecordTypeId__c");
     		fileheader.append("\n");
     		byte[] headerBytes =  fileheader.toString().getBytes("UTF-8");
     	    int headerBytesLength = headerBytes.length;
     		
             // the header columns are used as the keys to the Map
             String[] header = mapReader.getHeader(true);

             Map<String, String> iMap;
             while( (iMap = mapReader.read(header)) != null) {
            	 StringBuffer sbdata=new StringBuffer();
            	 //code to create a new csv file
            	 for(String data:csvfields)
            	 {
            		String celldata=iMap.get(data);
            		if(celldata!=null)
            		{
            			celldata=celldata.replaceAll("\"", "'");
            			sbdata.append(celldata.contains(",")?"\""+celldata+"\"":celldata);
            			sbdata.append(",");
            			
            		}
            		else
            		{
            			sbdata.append("");
                		sbdata.append(","); 
            		}
            		
            	 }
            	 sbdata.append(assignmentId);
            	 sbdata.append(",");
            	 sbdata.append(recordtypeid);
            	 sbdata.append("\n");
            	 byte[] bytes=sbdata.toString().getBytes("UTF-8");
                 if (currentBytes + bytes.length > maxBytesPerBatch|| currentLines > maxRowsPerBatch) {
                	 createBatch(tmpFile, batchInfos, connection, jobInfo);
                	 currentBytes = 0;
                     currentLines = 0;
                 }
                 
                 if (currentBytes == 0) {
                	 writer.write(fileheader.toString().getBytes("UTF-8"));
                     currentBytes = headerBytesLength;
                     currentLines = 1;
                   }
            	 
            	 writer.write(sbdata.toString().getBytes("UTF-8"));
            	 currentBytes += bytes.length;
                 currentLines++;
             }
             // Finished processing all rows
             // Create a final batch for any remaining data
             if (currentLines > 1) {
            	 createBatch(tmpFile, batchInfos, connection, jobInfo);
             }
             
     }finally {
             if( mapReader != null ) {
                     mapReader.close();
             }
             
             if(writer!=null)
             {
             	writer.close();
             	tmpFile.delete();
             	File f=new File(csvFileName);
             	System.out.println(f.getAbsolutePath());
             	f.delete();
             }
             
     }

    return batchInfos;
  }

  /**
   * Create a batch by uploading the contents of the file. This closes the
   * output stream.
   */
  private void createBatch(File tmpFile,
      List<BatchInfo> batchInfos, RestConnection connection, JobInfo jobInfo)
      throws IOException, AsyncApiException {
    FileInputStream tmpInputStream = new FileInputStream(tmpFile);
    try {
      BatchInfo batchInfo = connection.createBatchFromStream(jobInfo,tmpInputStream);
      System.out.println(batchInfo);
      batchInfos.add(batchInfo);

    } finally {
      tmpInputStream.close();
    }
  }
 
}


