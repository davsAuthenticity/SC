package com.cloudit;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.zxing.WriterException;

public class ScanQrServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = null;
		String decodeVal = null;
		
		String message = "N/A";
		
		HttpSession session = null;
		
		try {
			
			userId = request.getParameter("userid");
			decodeVal = request.getParameter("decodeQr");
			
			CreateQrModel qrmodel = new CreateQrModel(userId);
			
			int insertedRows = qrmodel.createQrHash(decodeVal);
			
			session = request.getSession();
			
			if(insertedRows > 0)
			{
				message = "QR Code has been scanned successfully and added to the database";
			}
			else
			{
				message = "Record adding failed !";
			}
			
			session.setAttribute("id", userId);
			session.setAttribute("decodedVal", decodeVal);
			session.setAttribute("message", message);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/scan.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			
			message = e.getMessage();
			request.setAttribute("message", message);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/scan.jsp");
			dispatcher.forward(request, response);
		}
	}
	

}
