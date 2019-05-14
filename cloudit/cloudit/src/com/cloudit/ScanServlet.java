package com.cloudit;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ScanServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = null;
		String userName = null;
		String profileImg = null;
		String email = null;
		String idToken = null;
		
		HttpSession session = null;
		
		try {
		
			id = request.getParameter("uId");
			
			session = request.getSession();
			
			AddDataModel adm = new AddDataModel();
			adm.showData(id);
			ArrayList<User> ulist = adm.getUser();
			
			id =  ulist.get(0).getId();
			
			session.setAttribute("id", id);
			
			/*id = session.getAttribute("id").toString();
			userName = session.getAttribute("userName").toString();
			profileImg = session.getAttribute("profileImg").toString();
			email = session.getAttribute("email").toString();
			idToken = session.getAttribute("idToken").toString();*/
			
			/*session.setAttribute("userName", userName);
			session.setAttribute("profileImg", profileImg);
			session.setAttribute("email", email);
			session.setAttribute("idToken", idToken);*/
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/scan.jsp");
			dispatcher.forward(request, response);
		
		//System.out.println(id+" "+userName+" "+email+" "+idToken);
		} catch (Exception e) {

			//System.out.println(e.getMessage());
			String conResult = e.getMessage();
			
			request.setAttribute("result", conResult);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/scan.jsp");
			dispatcher.forward(request, response);
		}
	}

}
