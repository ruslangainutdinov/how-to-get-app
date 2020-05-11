package com.ruslanproject.howtoget.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

public class MailSenderClass {

	private Session session;

	private static final String username = "how-to-get@mail.ru";

	private static final String password = "password_2020";

	private Properties properties = new Properties();

	public MailSenderClass() {
		initializeProperties();
		initializeSession();
	}

	private void initializeSession() {
		session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	private void initializeProperties() {
		properties.put("mail.smtp.host", "smtp.mail.ru");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	}

	public  boolean sendMessage(String receiver, String name, Long confirmationCode, String messageBody, boolean registrationFlag) {
		boolean success=false;
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiver));
			message.setSubject("Registration confirmation");
			if(registrationFlag) {
				System.out.println("So what's up");
				message.setText("Hello, "+ name + "\n\n\n Here is your confirmation code: "+confirmationCode
						+"\n\n\nPlease enter above confirmation code to complete registration.");
			}else {
				message.setText(messageBody);
			}
			Transport.send(message);
			System.out.println("Done");
			success=true;
		} catch (MessagingException e) {

		}
		return success;
	}
}
