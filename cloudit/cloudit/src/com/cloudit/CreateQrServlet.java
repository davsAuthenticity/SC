package com.cloudit;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateQrServlet extends HttpServlet {
	
	BufferedImage bi = null;

	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			HttpSession session = request.getSession(false);
			
			String userId = session.getAttribute("id").toString();
			String textToConvert = request.getParameter("textToConvert");
//			String userName = session.getAttribute("userName").toString();
//			String profileImg = session.getAttribute("profileImg").toString();
//			String userEmail = session.getAttribute("email").toString();
//			String idToken = session.getAttribute("idToken").toString();
			
			
			
//			String userId = request.getParameter("userid");
//			String userName = request.getParameter("username");
//			String userEmail = request.getParameter("useremail");
//			String userAdd = request.getParameter("useradd");
			
			CreateQrModel qrmodel = new CreateQrModel(userId, textToConvert);
		
			qrmodel.createQrFile();
						
			session.setAttribute("id", userId);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			dispatcher.forward(request, response);
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}*/
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = null;
		
		try {
			
			String userId = request.getParameter("userId");
			String textToConvert = request.getParameter("qrCodeText");
			
			session = request.getSession();

			CreateQrModel qrmodel = new CreateQrModel(userId, textToConvert);
		
			qrmodel.createQrFile();
			
			AddDataModel adm = new AddDataModel();
			adm.showData(userId);
			ArrayList<User> ulist = adm.getUser();
			
			userId =  ulist.get(0).getId();
						
			session.setAttribute("id", userId);
			
			//RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			//dispatcher.forward(request, response);
			response.getWriter().println("viewqr");
			
		} catch(Exception e)
		{
			//System.out.println(e.getMessage());
			response.getWriter().println("viewqr");
		}
	}
}
