package com.cloudit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobGetOption;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Servlet implementation class GoogleCloudUploadServlet
 */
public class GoogleCloudUploadServlet extends HttpServlet {
	
	private static Storage storage = null;
	//HttpSession session = null;
	
	String uId = null;
	
	static {
	    storage = StorageOptions.getDefaultInstance().getService();
	  }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		//session = request.getSession();
		
		uId = request.getParameter("userId");
		Part filePart =  request.getPart("myFile");
		String bucket = "avsfilestorage";
		
		//String link = uploadFile(filePart, bucket);
		UploadFileData ufd = uploadFile(filePart, bucket);
		
		AddDriveDataModel adm = new AddDriveDataModel(ufd.getUserId(), ufd.getMediaLink(), ufd.getSelfLink(), ufd.getDirectUrl());
		
		try {
			int updatedRows = adm.addGoogleCloudData(uId);
			
			if(updatedRows > 0)
			{
				request.setAttribute("directLink", ufd.getDirectUrl());
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
				dispatcher.forward(request, response);
			}
			
		} catch (ClassNotFoundException e) {
			
			//e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			
			//e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	public UploadFileData uploadFile(Part filePart, final String bucketName) throws IOException {
	    DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
	    DateTime dt = DateTime.now(DateTimeZone.UTC);
	    String dtString = dt.toString(dtf);
	    //final String fileName = filePart.getSubmittedFileName() + dtString;
	    
	    final String fileName = filePart.getSubmittedFileName();
	    final String fileType = filePart.getContentType();

	    // the inputstream is closed by default, so we don't need to close it here
	    BlobId blobId = BlobId.of(bucketName, fileName);
	    
	    BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(blobId).setContentType(fileType).build(),filePart.getInputStream());
	    
	    /*BlobInfo blobInfo =
	        storage.create(
	            BlobInfo
	                .newBuilder(bucketName, fileName)
	                // Modify access list to allow all users with link to read file
	                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
	                .build(),
	            filePart.getInputStream());*/

	    String mediaLink = blobInfo.getMediaLink();
	    String selfLink = blobInfo.getSelfLink();
	    String directUrl = "https://storage.googleapis.com/avsfilestorage/"+fileName;
	    
	    UploadFileData ufd = new UploadFileData(this.uId, mediaLink, selfLink, directUrl);
	    
	    return ufd;
	 }

	/*public String uploadFile(Part filePart, final String bucketName) throws IOException {
	    DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
	    DateTime dt = DateTime.now(DateTimeZone.UTC);
	    String dtString = dt.toString(dtf);
	    //final String fileName = filePart.getSubmittedFileName() + dtString;
	    
	    final String fileName = filePart.getSubmittedFileName();

	    // the inputstream is closed by default, so we don't need to close it here
	    BlobInfo blobInfo =
	        storage.create(
	            BlobInfo
	                .newBuilder(bucketName, fileName)
	                // Modify access list to allow all users with link to read file
	                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
	                .build(),
	            filePart.getInputStream());
	    // return the public download link
	    
	    return blobInfo.getMediaLink();
	 }*/
	
	/*public String getImageUrl(HttpServletRequest req, HttpServletResponse resp,
            final String bucket) throws IOException, ServletException {
		Part filePart = req.getPart("myFile");
		final String fileName = filePart.getSubmittedFileName();
		String imageUrl = req.getParameter("imageUrl");
		// Check extension of file
		if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
		final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
		String[] allowedExt = {"jpg", "jpeg", "png", "gif"};
		for (String s : allowedExt) {
		if (extension.equals(s)) {
		return this.uploadFile(filePart, bucket);
		}
		}
		throw new ServletException("file must be an image");
		}
		return imageUrl;
	}*/
		// [END getImageUrl]
	
	public URL signUrl(String bucketName, String blobName) {
	    // [START signUrl]
	    URL signedUrl =
	        storage.signUrl(BlobInfo.newBuilder(bucketName, blobName).build(), 5, TimeUnit.MINUTES);
	    // [END signUrl]
	    return signedUrl;
	}
	
	public byte[] readBlobFromId(BlobId bid) {
	    // [START readBlobFromId]
	    byte[] content = storage.readAllBytes(bid);
	    // [END readBlobFromId]
	    return content;
	  }
}
