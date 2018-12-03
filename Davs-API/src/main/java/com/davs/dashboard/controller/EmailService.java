package com.davs.dashboard.controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService extends Thread {

	String reciepientAddress, emailSubject, emailContent;
	public EmailService(String content) {
		emailSubject = "New Notification "+System.currentTimeMillis();
		emailContent = content;
	}

	public void run() {
		prepareAndSendEmail(emailSubject, emailContent);
		
	}

	public void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("DAVSNotification@gmail.com", "Davs Admin"));

	      msg.setReplyTo(InternetAddress.parse("DAVSNotification@gmail.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setContent(body, "text/html; charset=utf-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
    	  Transport.send(msg);  
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	public void prepareAndSendEmail(String subject, String body) {
		final String fromEmail = "DAVSNotification@gmail.com"; //requires valid gmail id
		final String password = "np%E3xR8vkGssq-R"; // correct password for gmail id
		final String toEmail = fromEmail; // can be any email id 

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
		props.put("mail.smtp.port", "465"); //SMTP Port

		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		Session session = Session.getDefaultInstance(props, auth);
		sendEmail(session, toEmail,subject, body);
	}

}
