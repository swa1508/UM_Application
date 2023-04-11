package com.umapp.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.umapp.core.model.User;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Component
public class EmailService {
	
	Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender  javaMailSender;

	@Autowired
	private Configuration configuration;

	private Properties getPropertyForMailSession() {
		logger.info("Inside @class EmailService @method getPropertyForMailSession");
		Properties props = new Properties();
		props.put("mail.smtp.auth", BaseConstants.BOOLEAN_TRUE);
		props.put("mail.smtp.starttls.enable",BaseConstants.BOOLEAN_TRUE);
		props.put("mail.smtp.host", BaseConstants.MAIL_HOST);
		props.put("mail.smtp.port", BaseConstants.MAIL_PORT);
		props.put("mail.smtp.ssl",BaseConstants.BOOLEAN_TRUE);
		props.put("mail.transport.protocol", BaseConstants.MAIL_PROTOCOL);
		return props;
	}
	
	private Session getSessionForMail() {
		logger.info("Inside @class EmailService @method getSessionForMail");
		return Session.getInstance( getPropertyForMailSession(),
				new jakarta.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(BaseConstants.MAIL_USERNAME, BaseConstants.MAIL_PASSWORD);
			}
		});
	}

	public String sendMailBySessionAndPasswordAuthentication() {
		logger.info("Inside @class EmailService @method sendMailBySessionAndPasswordAuthentication");
		
		//provide recipient's email ID
		String to = "namdev15swatantra@gmail.com";
		
		try {
			//create a MimeMessage object
			Message message = new MimeMessage(getSessionForMail());
			//set From email field
			message.setFrom(new InternetAddress(BaseConstants.MAIL_USERNAME));
			//set To email field
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			//set email subject field
			message.setSubject("Email-sendMailBySessionAndPasswordAuthentication-Test");
			//set the content of the email message
			message.setText("Just discovered that Jakarta Mail is fun and easy to use");
			//send the email message
			Transport.send(message);
			System.out.println("Email By sendMailBySessionAndPasswordAuthentication Sent Successfully");
			return "Email Message Sent Successfully";
		} catch (MessagingException e) {
			throw new RuntimeException("Exception in sendMailBySessionAndPasswordAuthentication :" + e);
		}
	}

	

	public String sendMailBySessionAndPasswordAuthenticationWithAttachment() {
		logger.info("Inside @class EmailService @method sendMailBySessionAndPasswordAuthenticationWithAttachment");
		//provide recipient's email ID
		String to = "namdev15swatantra@gmail.com";

		try {
			//create a MimeMessage object
			Message message = new MimeMessage(getSessionForMail());
			//set From email field
			message.setFrom(new InternetAddress(BaseConstants.MAIL_USERNAME));
			//set To email field
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			//set email subject field
			message.setSubject("Email-sendMailBySessionAndPasswordAuthenticationWithAttachment-Test");
			//set the content of the email message
			message.setText("Just discovered that Jakarta Mail is fun and easy to use");
			//send the email message

			BodyPart messageBodyPart = new MimeBodyPart();
			//set the actual message
			messageBodyPart.setText("Please find the attachment sent using Jakarta Mail");
			//create an instance of multipart object
			Multipart multipart = new MimeMultipart();
			//set the first text message part
			multipart.addBodyPart(messageBodyPart);
			//set the second part, which is the attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "C:\\Users\\Lenovo\\Downloads\\TestMailAttachment.txt";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			//send the entire message parts
			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Email send by sendMailBySessionAndPasswordAuthenticationWithAttachment Successfully");
			return "Email Message Sent Successfully";
		} catch (MessagingException e) {
			throw new RuntimeException("Exception in sendMailBySessionAndPasswordAuthenticationWithAttachment :" + e);
		}
	}



	public String sendMimeMessageSimpleMail() {
		logger.info("Inside @class EmailService @method sendMimeMessageSimpleMail");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
			mimeMessageHelper.setFrom(BaseConstants.MAIL_USERNAME);
			mimeMessageHelper.setTo("namdev15swatantra@gmail.com");
			mimeMessageHelper.setText("Welcome to Email Application");
			mimeMessageHelper.setSubject("Email-sendMimeMessageSimpleMail-Test");
			javaMailSender.send(mimeMessage);
			return "Email by sendMimeMessageSimpleMail Send Successfully";
		} catch (Exception e) {
			throw new RuntimeException("Exception in sendMimeMessageSimpleMail: "+e);
		}

	}

	public String sendMimeMessageMailWithAttachment() {
		logger.info("Inside @class EmailService @method sendMimeMessageMailWithAttachment");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(BaseConstants.MAIL_USERNAME);
			mimeMessageHelper.setTo("namdev15swatantra@gmail.com");
			mimeMessageHelper.setText("Welcome to Email Application");
			mimeMessageHelper.setSubject("Email-sendMimeMessageMailWithAttachment-Test");

			// Adding the attachment
			FileSystemResource file = new FileSystemResource(new File("C:\\Users\\Lenovo\\Downloads\\TestMailAttachment.txt"));

			mimeMessageHelper.addAttachment(file.getFilename(), file);
			javaMailSender.send(mimeMessage);
			return "Email sendMimeMessageMailWithAttachment with attachment  Send Successfully";
		} catch (Exception e) {
			throw new RuntimeException("Exception in sendMimeMessageSimpleMail: "+e);
		}

	}


	public String sendSimpleMailMessageMail() {
		logger.info("Inside @class EmailService @method sendSimpleMailMessageMail");
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			// Setting up necessary details
			mailMessage.setFrom(BaseConstants.MAIL_USERNAME);
			mailMessage.setTo("namdev15swatantra@gmail.com");
			mailMessage.setText("Welcome to Sample Email Application");
			mailMessage.setSubject("Email-sendSimpleMailMessageMail-Test");

			// Sending the mail
			javaMailSender.send(mailMessage);
			System.out.println("Welecome to  sendSimpleEmail method ends");
			return "Email by sendSimpleMailMessageMail Send Successfully";
		} catch (Exception e) {
			throw new RuntimeException("Exception  in sendSimpleMailMessageMail: "+e);
		}
	}


	public String sendMimeMessageMailByTemplate() {
		logger.info("Inside @class EmailService @method sendSimpleMailMessageMail");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
			mimeMessageHelper.setFrom(BaseConstants.MAIL_USERNAME);
			mimeMessageHelper.setTo("namdev15swatantra@gmail.com");
			mimeMessageHelper.setSubject("Email-sendMimeMessageMailByTemplate-Test");
			String emailContent = getEmailContent();
			mimeMessageHelper.setText(emailContent, true);
			javaMailSender.send(mimeMessage);
			return "Email by sendMimeMessageMailByTemplate Send Successfully";
		} catch (Exception e) {
			throw new RuntimeException("Exception in sendMimeMessageMailByTemplate: "+e);
		}

	}

	private String getEmailContent() throws IOException, TemplateException{
		logger.info("Inside @class EmailService @method getEmailContent");
		User user = new User();
		user.setFirstName("Admin");
		user.setUsername("Admin@123");

		List<String> courses = new ArrayList<>();
		courses.add("BCA");
		courses.add("MCA");
		courses.add("BBA");

		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("courses", courses);
		configuration.getTemplate("User.ftlh").process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}
	
	
	public String sendMimeMessageMailWithAttachmentAndTemplate() {
		logger.info("Inside @class EmailService @method sendMimeMessageMailWithAttachmentAndTemplate");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(BaseConstants.MAIL_USERNAME);
			mimeMessageHelper.setTo("namdev15swatantra@gmail.com");
			mimeMessageHelper.setSubject("Email-sendMimeMessageMailWithAttachmentAndTemplate-Test");

			// Adding the attachment
			FileSystemResource file = new FileSystemResource(new File("C:\\Users\\Lenovo\\Downloads\\TestMailAttachment.txt"));
			mimeMessageHelper.addAttachment(file.getFilename(), file);
			
			//Adding Template
			String emailContent = getEmailContent();
			mimeMessageHelper.setText(emailContent, true);
			javaMailSender.send(mimeMessage);
			return " Mime Message Mail With Attachment And Template  Send Successfully";
		} catch (Exception e) {
			throw new RuntimeException("Exception in sendMimeMessageMailWithAttachmentAndTemplate: "+e);
		}

	}

	

}
