package com.salesforcebulkapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileUploadException;

/**
 * Servlet implementation class ConvertCSV
 */
@WebServlet("/ConvertCSV")
@MultipartConfig
public class ConvertCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConvertCSV() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IllegalStateException, IOException, ServletException
	{

		
		//String description = request.getParameter("filename"); // Retrieves <input type="text" name="description">
	    Part filePart = request.getPart("filename"); // Retrieves <input type="file" name="file">
	    
	    if(filePart!=null)
	    {
	    	response.getOutputStream().println("<html>");
	    	response.getOutputStream().println("<link href='bootstrap/css/bootstrap.min.css' rel='stylesheet' media='screen'>");
	    	response.getOutputStream().println("<body>");
	    	response.getOutputStream().println("<script src='http://code.jquery.com/jquery.js'></script>");
	    	response.getOutputStream().println("<form method='get' action='aftermap.jsp'> ");
	    	response.getOutputStream().println("<table>");
	    InputStream filecontent = filePart.getInputStream();
	    
	    File f=File.createTempFile("raj", ".csv");
	    OutputStream os = new FileOutputStream(f);  
	    byte[] buffer = new byte[102400];  
	    int bytesRead;  
	    while ((bytesRead = filecontent.read(buffer)) != -1) {  
	      os.write(buffer, 0, bytesRead);  
	    }  
	   
	    os.close();
	    
	    request.getSession().setAttribute("filename", f.getAbsolutePath());
	    StringBuilder fileData = new StringBuilder();
	    BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	    try {
	    	String data=bufferedReader.readLine();
	    	String [] csvheader=data.split(",");
	    	List<String> list_csvheaders=new ArrayList<>();
	    	for(String header:csvheader)
	    	{
	    		list_csvheaders.add(header.trim());
	    	}
	    	
	    	Set<String> fields=fieldsMap.keySet();
	    	
	    	for(String header:fields)
	    	{
	    		response.getOutputStream().println("<tr>");
	    		
	    		header=header.trim();
	    		if(list_csvheaders.contains(header))
	    		{
	    		//	System.out.println(header);
	    			response.getOutputStream().println("<td><label for='"+header+"' name='l-"+header+"'>"+header+"</label></td>"+"<td><select name='"+header+"'>");
	    			for(String s:list_csvheaders)
	    			{
	    				if(s.equals(header))
	    				{
	    				response.getOutputStream().println("<option selected>"+s+"</option>");
	    				}
	    				else
	    				{
	    					response.getOutputStream().println("<option>"+s+"</option>");
	    				}
	    			}
	    			response.getOutputStream().println("</select></td>");
	    			//response.getOutputStream().println("<br>");
	    		}
	    		else
	    		{
	    			response.getOutputStream().println(("<td><label for='"+header+"' name='l-"+header+"'>"+header+"</label></td>"+"<td><select name='"+header+"'>"));
	    			response.getOutputStream().println("<option selected>**None**</option>");
	    			for(String s:list_csvheaders)
	    			{
	    				response.getOutputStream().println("<option>"+s+"</option>");
	    			}
	    			
	    			response.getOutputStream().println("</select></td>"+"<td><font color='red'>Mismatch</font></td>");
	    			//response.getOutputStream().println("<br>");
	    		}
	    		
	    		response.getOutputStream().println("</tr>");
	    		
	    	}
	    
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    System.out.println(fileData.toString()); 
	    response.getOutputStream().println("</table>");
	    response.getOutputStream().println("<input type='submit' class='btn btn-primary'><i class='icon-search icon-white'></i></input>");
	    response.getOutputStream().println("</form>");
	    response.getOutputStream().println("</body>");
	    response.getOutputStream().println("<html>");
	    }
	    else
	    {
	    	System.out.println("Filepart null");
	    }
		
	}

}
