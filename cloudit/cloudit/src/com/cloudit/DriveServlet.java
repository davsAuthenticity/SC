package com.cloudit;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DriveServlet extends HttpServlet {
	
	HttpSession session = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setAttribute("drive", 1);
		
		//RequestDispatcher dispatcher = request.getRequestDispatcher("/views/drive.jsp");
		//dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession();
		
		String uid = request.getParameter("uId");
		session.setAttribute("uid", uid);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/cloud.jsp");
		dispatcher.forward(request, response);
	}

}
