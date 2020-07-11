package com.ruslanproject.howtoget.utils;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/*Class responsible for sending email
 * During registration to confirm email 
 * Notify user about cancellation of booking*/

/**
 * Class is responsible for sending emails to users
 * 
 * @author Ruslan Gainutdinov
 *
 */
@Component
public class MailSenderClass {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderClass.class);
	
	//TODO extract username&password to external properties file?
	//maybe extract also PROPERTIES to this file as well
	//==fields==
	private Session session;

	private final String username;

	@Autowired
	private Environment env;
	
	private final String password;
	
	private Properties properties = new Properties();

	public MailSenderClass(@Value("${mail.sender.email}")	String username,
						   @Value("${mail.sender.password}")String password) {
		this.username=username;
		this.password=password;
		initializeSession();
	}

	private void initializeSession() {
		session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	@PostConstruct
	private void initializeProperties() {
		properties.put("mail.smtp.host", env.getProperty("mail.smtp.host"));
		properties.put("mail.smtp.port", env.getProperty("mail.smtp.port"));
		properties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
		properties.put("mail.smtp.socketFactory.port", env.getProperty("mail.smtp.socketFactory.port"));
		properties.put("mail.smtp.socketFactory.class", env.getProperty("mail.smtp.socketFactory.class"));
	}
 
	/*boolean flag conditions:
	*true - registration message
	*false - messageBody used*/
	public  boolean sendMessage(String receiver, String name, String subject, String confirmationCode, String messageBody, boolean registrationFlag) {
		boolean success=false;
		String s=env.getProperty("mail.sender.email");
		System.out.println("sssssssssssssssssssssssss"+s);
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
			LOGGER.info("Message was sent to " + receiver + " Subject: " +subject);
			success=true;
		} catch (MessagingException e) {

		}
		return success;
	}
}
