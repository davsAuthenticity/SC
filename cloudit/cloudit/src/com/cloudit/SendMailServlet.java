package com.cloudit;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SendMailServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uId = request.getParameter("userid");
		String senderName = request.getParameter("sender");
		String senderEmail = request.getParameter("senderemail");
		String recepEmail = request.getParameter("emailadd");
		String emailMsg = request.getParameter("emailMsg");
		
		/*final String username = "gtnadeesha@gmail.com";
        final String password = "";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(recepEmail));
            message.setSubject("Sending Email via gmail SMTP");
            message.setText(emailMsg);

            Transport.send(message);

            //System.out.println("Done");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/scan.jsp");
		    dispatcher.forward(request, response);

        } catch (MessagingException e) {
            //e.printStackTrace();
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
		    dispatcher.forward(request, response);
        }*/
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress(senderEmail, "Test Sender"));
		  msg.addRecipient(Message.RecipientType.TO,
		                   new InternetAddress(recepEmail, "Test Recipient"));
		  msg.setSubject("Testing Emails");
		  msg.setText(emailMsg);
		  Transport.send(msg);
		  
		  RequestDispatcher dispatcher = request.getRequestDispatcher("/views/scan.jsp");
		  dispatcher.forward(request, response);
		  
		} catch (Exception e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
		    dispatcher.forward(request, response);
		} 
		
		/*Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); //I tried disabling this but it not works
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	    Session session = Session.getDefaultInstance(props, null);

	    try {
	    	
	      Message msg = new MimeMessage(session);
	      UserService userService = UserServiceFactory.getUserService();
	      
	      senderEmail = userService.getCurrentUser().getEmail();
	      
	      msg.setFrom(new InternetAddress(senderEmail));
	      msg.addRecipient(Message.RecipientType.TO,
	                       new InternetAddress(recepEmail));
	      msg.setSubject("QR Code - Decoded Value");
	      msg.setText(emailMsg);
	      Transport.send(msg);
	      
	      RequestDispatcher dispatcher = request.getRequestDispatcher("/views/scan.jsp");
	      dispatcher.forward(request, response);
	      
	    } catch (Exception e) {
	    	
	    	request.setAttribute("notes", e.getMessage());
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
		    dispatcher.forward(request, response);
	    } */
		
		
		/*Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); //I tried disabling this but it not works
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587"); //I tried with another port

		//I tried without authentication from my account like this:
		//Session.getDefaultInstance(props, null); 
		//It not works
		Session session = Session.getInstance(props, new Authenticator() {
		     @Override
		     protected PasswordAuthentication getPasswordAuthentication() {
		     return new PasswordAuthentication(senderEmail,upass);
		    }
		});

		String subject = "test";
		
		Message message = new MimeMessage(session);
		
		// Here is the key, sending email not from authenticated account
		try {
			message.setFrom(new InternetAddress(senderEmail));
	
			message.setReplyTo(InternetAddress.parse(senderEmail,false));
		
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepEmail));
		
			message.setSubject(subject);
			
			message.setText(emailMsg);
			
			Transport.send(message);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/scan.jsp");
		    dispatcher.forward(request, response);
			
		}catch(Exception e)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
		    dispatcher.forward(request, response);
		}*/

	}

}
