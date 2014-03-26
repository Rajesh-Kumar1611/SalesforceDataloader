package com.salesforcebulkapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sforce.async.AsyncApiException;
import com.sforce.async.BatchInfo;
import com.sforce.async.BatchStateEnum;
import com.sforce.async.CSVReader;
import com.sforce.async.ContentType;
import com.sforce.async.JobInfo;
import com.sforce.async.JobStateEnum;
import com.sforce.async.OperationEnum;
import com.sforce.async.RestConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class BulkLoader {

static Map<String,String> salesforcecols=new HashMap<String,String>();
static Map<String, String> bl_fieldsMap = new HashMap<String,String>();
	
  public static void main(String[] args) throws AsyncApiException,
      ConnectionException, IOException {
   
	  BulkLoader bl=new BulkLoader();
	  bl.getRestConnection("itstaff@invenio.com.isb", "Th3t@1126");
    //example.run();
  }
  
  public static void getMap(Map map,String file,String username,String password,String method) throws ConnectionException
  {
	  Maps.setSf_username(username);
	  Maps.setSf_password(password);
	  salesforcecols.putAll(Maps.getFieldMapping(method));
	  bl_fieldsMap=map;
	  
	  System.out.println("salesforcemap"+salesforcecols);
	  System.out.println("blmap"+bl_fieldsMap);
	  
	  BulkLoader example = new BulkLoader();
	  try {
		example.runJob("Lead", username,password , file);
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
  }
  
  /**
   * Creates a Bulk API job and uploads batches for a CSV file.
   */
  public String runJob(String sobjectType, String userName, String password, String sampleFileName) throws AsyncApiException, ConnectionException, IOException {
    RestConnection connection = getRestConnection(userName, password);
    JobInfo job = createJob(sobjectType, connection);
    List<BatchInfo> batchInfoList = createBatchesFromCSVFile(connection, job,sampleFileName);
    closeJob(connection, job.getId());
    awaitCompletion(connection, job, batchInfoList);
    String result=checkResults(connection, job, batchInfoList);
    return result;
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
        Map<String, String> resultInfo = new HashMap<String, String>();
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
  private void awaitCompletion(RestConnection connection, JobInfo job,
    List<BatchInfo> batchInfoList) throws AsyncApiException {
    long sleepTime = 0L;
    Set<String> incomplete = new HashSet<String>();
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
            System.out.println("BATCH STATUS:\n" + b);
          }
        }
      }
    }
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
    job = connection.createJob(job);
    System.out.println(job);
    return job;
  }

  /**
   * Create the RestConnection used to call Bulk API operations.
   */
  
  private RestConnection getRestConnection(String userName, String password)
      throws ConnectionException, AsyncApiException {
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
    BufferedReader rdr = new BufferedReader(new InputStreamReader(
        new FileInputStream(csvFileName)));
    // read the CSV header row
     String headername=rdr.readLine();
	 Set s = bl_fieldsMap.entrySet();
	 Iterator it = s.iterator();
	 
	 while(it.hasNext()){
	 Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();
	 String key=entry.getKey();
	 String[] value=entry.getValue();
	 if(key.equals("assignmentruleid"))
	 {
		 assignmentkey=value[0].toString();
	 }
	 
	 if(key.equals("recordtypeid"))
	 {
		 recordtypekey=value[0].toString();
	 }
	 
	 try {
		 
		 headername=headername.replaceFirst(value[0].toString(),salesforcecols.get(key.trim()));
	} catch (Exception e) {
		System.out.println(e);
	}
	
	 Maps map=new Maps();
	 List<Map<String,String>> ids=map.getRecordtypeAndAssignmentId();
	 assignmentId=ids.get(0).get(assignmentkey);
	 recordtypeid=ids.get(1).get(recordtypekey);
	
	 }
	 System.out.println(headername);
	 headername=headername+",AssignmentRuleId__c,RecordTypeId__c";
    byte[] headerBytes = ( headername+ "\n").getBytes("UTF-8");
    int headerBytesLength = headerBytes.length;
    File tmpFile = File.createTempFile("bulkAPIInsert", ".csv");
    // Split the CSV file into multiple batches
    try {
      FileOutputStream tmpOut = new FileOutputStream(tmpFile);
      
      int maxBytesPerBatch = 10000000; // 10 million bytes per batch
      int maxRowsPerBatch = 10000; // 10 thousand rows per batch
      int currentBytes = 0;
      int currentLines = 0;
      String nextLine;
      while ((nextLine = rdr.readLine()) != null) {
        byte[] bytes = (nextLine + ","+assignmentId+","+recordtypeid+ "\n").getBytes("UTF-8");
        // Create a new batch when our batch size limit is reached
        if (currentBytes + bytes.length > maxBytesPerBatch
            || currentLines > maxRowsPerBatch) {
          createBatch(tmpOut, tmpFile, batchInfos, connection, jobInfo);
          currentBytes = 0;
          currentLines = 0;
        }
        if (currentBytes == 0) {
          tmpOut = new FileOutputStream(tmpFile);
          tmpOut.write(headerBytes);
          currentBytes = headerBytesLength;
          currentLines = 1;
        }
        tmpOut.write(bytes);
        currentBytes += bytes.length;
        currentLines++;
      }
      // Finished processing all rows
      // Create a final batch for any remaining data
      if (currentLines > 1) {
        createBatch(tmpOut, tmpFile, batchInfos, connection, jobInfo);
      }
    } finally {
      tmpFile.delete();
      rdr.close();
    }
    return batchInfos;
  }

  /**
   * Create a batch by uploading the contents of the file. This closes the
   * output stream.
   */
  private void createBatch(FileOutputStream tmpOut, File tmpFile,
      List<BatchInfo> batchInfos, RestConnection connection, JobInfo jobInfo)
      throws IOException, AsyncApiException {
    tmpOut.flush();
    tmpOut.close();
    FileInputStream tmpInputStream = new FileInputStream(tmpFile);
    try {
      BatchInfo batchInfo = connection.createBatchFromStream(jobInfo,
          tmpInputStream);
      System.out.println(batchInfo);
      batchInfos.add(batchInfo);

    } finally {
      tmpInputStream.close();
    }
  }
}