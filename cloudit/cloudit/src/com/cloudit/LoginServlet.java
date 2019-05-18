package com.cloudit;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	      
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String username = request.getParameter("name");
		String imageUrl = request.getParameter("imageUrl");
		String email = request.getParameter("email");
		String idToken = request.getParameter("id_token");	
		String back = request.getParameter("back");
		String viewLink = request.getParameter("viewLink"); 
		String downLink = request.getParameter("downLink"); 
		String upLink = request.getParameter("upLink"); 
		
		HttpSession session = null;
		
		try {
			
			AddDataModel adm = new AddDataModel();
			ArrayList<User> ulist = adm.addData(id, username, imageUrl, email, idToken);
			
			ProductModel pdm = new ProductModel();
			ArrayList<Product> plist = pdm.getProducts(id);
			
			session = request.getSession(true);
			
			//DbUtil ut = new DbUtil();
			//String conResult = ut.testConnection();
			
			//session.setAttribute("result", conResult);
			
			if(ulist.size() != 1) //Check the number of User objects returned
			{
				session.setAttribute("id", "N/A");
				session.setAttribute("userName", "N/A");
				//session.setAttribute("qrcode", "N/A");
				session.setAttribute("profileImg", "N/A");
				session.setAttribute("email", "N/A");
				session.setAttribute("idToken", "N/A");
				session.setAttribute("title", "N/A");
				session.setAttribute("notes", "");
				//session.setAttribute("result", conResult);
				session.setAttribute("viewLink", viewLink);
				session.setAttribute("downLink", downLink);
				session.setAttribute("upLink", upLink);
			}
			else //Ideal scenario
			{
				session.setAttribute("id", ulist.get(0).getId());
				session.setAttribute("userName", ulist.get(0).getName());
				//session.setAttribute("qrcode", ulist.get(0).getQrCode());
				session.setAttribute("profileImg", ulist.get(0).getImageUrl());
				session.setAttribute("email", ulist.get(0).getEmail());
				session.setAttribute("idToken", ulist.get(0).getIdToken());
				session.setAttribute("title", ulist.get(0).getTitle());
				session.setAttribute("notes", ulist.get(0).getNotes());
				session.setAttribute("viewLink", viewLink);
				session.setAttribute("downLink", downLink);
				session.setAttribute("upLink", upLink);
			}
			
			session.setAttribute("size", plist.size());
			session.setAttribute("products", plist);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {

			//System.out.println(e.getMessage());
			String conResult = e.getMessage();
			
			session.setAttribute("result", conResult);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
