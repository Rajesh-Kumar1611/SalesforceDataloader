package com.salesforcebulkapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
@WebServlet("/ProcessCSV")
@MultipartConfig
public class ProcessCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessCSV() {
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

		
		try {
			//String description = request.getParameter("filename"); // Retrieves <input type="text" name="description">
		    Part filePart = request.getPart("filename"); // Retrieves <input type="file" name="file">
		    if(filePart!=null &&filePart.getSize()>0)
		    {
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
		    response.sendRedirect("mapping.jsp");
		    }
		    else
		    {
		    response.sendRedirect("error.jsp");
		    }
		   
		} catch (Exception e) {
			response.sendRedirect("error.jsp");
		}
		
		
	}

}
