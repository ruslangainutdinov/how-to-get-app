package com.ruslanproject.howtoget.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*Class responsible for sending email
 * During registration to confirm email 
 * Notify user about cancellation of booking*/
@Component
public class MailSenderClass {

	private static final Logger logger = LoggerFactory.getLogger(MailSenderClass.class);
	
	//TODO extract username&password to external properties file?
	//maybe extract also PROPERTIES to this file as well
	//==fields==
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
 
	/*boolean flag conditions:
	*true - registration message
	*false - messageBody used*/
	public  boolean sendMessage(String receiver, String name, String subject, Long confirmationCode, String messageBody, boolean registrationFlag) {
		boolean success=false;
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiver));
			message.setSubject(subject);
			if(registrationFlag) {
				message.setText("Hello, "+ name + "\n\n\n Here is your confirmation code: "+confirmationCode
						+"\n\n\nPlease enter above confirmation code to complete registration.");
			}else {
				message.setText(messageBody);
			}
			Transport.send(message);
			logger.info("Message was sent to " + receiver + " Subject: " +subject);
			success=true;
		} catch (MessagingException e) {

		}
		return success;
	}
}
