package com.cloudit;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddNotesServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = null;
		
		try {
			session = request.getSession(false);
			
			//String userId = (String) session.getAttribute("id");
			String userId = request.getParameter("userId");
			String textNotes = request.getParameter("notesText");
			
			AddDataModel adm = new AddDataModel();
			adm.updateNotes(userId, textNotes);
			
			ProductModel pdm = new ProductModel();
			ArrayList<Product> plist = pdm.getProducts(userId);
			
			ArrayList<User> ulist = adm.getUser();
			
			session.setAttribute("id", ulist.get(0).getId());
			session.setAttribute("userName", ulist.get(0).getName());
			session.setAttribute("profileImg", ulist.get(0).getImageUrl());
			session.setAttribute("email", ulist.get(0).getEmail());
			session.setAttribute("idToken", ulist.get(0).getIdToken());
			session.setAttribute("title", ulist.get(0).getTitle());
			session.setAttribute("notes", ulist.get(0).getNotes());
			
			session.setAttribute("size", plist.size());
			session.setAttribute("products", plist);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			dispatcher.forward(request, response);
			
		} catch(Exception e)
		{
			session = request.getSession(true);
			String conResult = e.getMessage();
			
			session.setAttribute("result", conResult);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			dispatcher.forward(request, response);
		}
	}
}