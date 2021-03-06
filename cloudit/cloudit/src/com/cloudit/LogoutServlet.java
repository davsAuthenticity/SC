package com.cloudit;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		//session.removeAttribute("id");
		session.invalidate();
		
		response.setHeader("Cache-Control","no-cache"); 
	    response.setHeader("Cache-Control","no-store"); 
	    response.setDateHeader("Expires", 0); 
	    response.setHeader("Pragma","no-cache");
//		
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//		response.setHeader("Expires", "0"); // Proxies.
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
		dispatcher.forward(request, response);
	}
}
