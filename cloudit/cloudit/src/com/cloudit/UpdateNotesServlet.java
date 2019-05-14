package com.cloudit;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateNotesServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = null;
		String notes = null;
		String updatedNotes = "N/A";
		HttpSession session = null;
		ArrayList<User> ulist = null;
		
		try {
			
			notes = request.getParameter("newNotes");
			userId = request.getParameter("userId");
			
			//session = request.getSession(false);
			session = request.getSession();
			
			//userId = session.getAttribute("id").toString();
			
			AddDataModel adm = new AddDataModel();
			adm.updateNotes(userId, notes);
			adm.showData(userId);
			
			ulist = adm.getUser();
			
			ProductModel pdm = new ProductModel();
			ArrayList<Product> plist = pdm.getProducts(userId);
			
			/*if(ulist != null)
			{
				userId =  ulist.get(0).getId();
				updatedNotes = ulist.get(0).getNotes();
				
				session.setAttribute("id", userId);
				
				session.setAttribute("userName", ulist.get(0).getName());
				session.setAttribute("profileImg", ulist.get(0).getImageUrl());
				session.setAttribute("email", ulist.get(0).getEmail());
				session.setAttribute("idToken", ulist.get(0).getIdToken());
				session.setAttribute("title", ulist.get(0).getTitle());
				
				session.setAttribute("notes", updatedNotes);
				
				session.setAttribute("size", plist.size());
				session.setAttribute("products", plist);
			}
			else
			{
				updatedNotes = "User List is Empty !";
			}*/
			
			session.setAttribute("id", userId);
			session.setAttribute("notes", notes);
			
			session.setAttribute("size", plist.size());
			session.setAttribute("products", plist);
			
			//response.getWriter().println("User Notes retrieved from the database as follows : " +updatedNotes);
			response.getWriter().println(notes);
			
		}catch(Exception e)
		{
			response.getWriter().println(userId+" "+e.getMessage()+" "+updatedNotes);
		}
		
	}

}