package com.salesforcebulkapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

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
	    InputStream filecontent = filePart.getInputStream();
	    StringBuilder fileData = new StringBuilder();
	    BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(filecontent));
	    try {
	    	String headers=bufferedReader.readLine();
	     System.out.println(headers);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    System.out.println(fileData.toString()); 
	    }
	    else
	    {
	    	System.out.println("Filepart null");
	    }
	    
		
	}

}
