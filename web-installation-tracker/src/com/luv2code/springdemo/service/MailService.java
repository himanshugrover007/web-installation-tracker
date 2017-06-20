package com.luv2code.springdemo.service;

import java.util.HashMap;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.luv2code.springdemo.constants.ActivityEmail;
import com.luv2code.springdemo.dto.EmailMessage;
import com.luv2code.springdemo.entity.Installation;
import com.luv2code.springdemo.entity.InstallationUserDetails;
import com.luv2code.springdemo.entity.LoginForm;

@Service("mailService")
public class MailService {
	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private TaskExecutor taskExecutor;

	private static Log log = LogFactory.getLog(MailService.class);

	/**
	 * @param text
	 *            - message
	 * @param from
	 *            - sender email
	 * @param to
	 *            - receiver email
	 * @param subject
	 *            - subject
	 * @param filePath
	 *            - file to attach, could be null if file not required
	 * @throws Exception
	 */
	public void sendMail(final LoginForm loginForm, final EmailMessage emailMessage) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendMailSimple(loginForm, emailMessage);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("Failed to send email to: " + " reason: " + e.getMessage());
				}
			}
		});
	}

	// private void sendMailSimple(String text, String from, String to, String
	// subject, String filePath) throws Exception {
	// MimeMessage message = mailSender.createMimeMessage();
	// try {
	// MimeMessageHelper helper = new MimeMessageHelper(message, true);
	// helper.setFrom(from);
	// helper.setTo(to);
	// helper.setSubject(subject);
	// helper.setText(text);
	// if(filePath != null){
	// FileSystemResource file = new FileSystemResource(filePath);
	// helper.addAttachment(file.getFilename(), file);
	// }
	// } catch (MessagingException e) {
	// throw new MailParseException(e);
	// }
	// mailSender.send(message);
	//
	// if(log.isDebugEnabled()){
	// log.debug("Mail was sent successfully to: " + to + " with file:
	// "+filePath);
	// }
	// }
	//
	private void sendMailSimple(LoginForm loginForm, EmailMessage emailMessage) throws Exception {

		mailSender.setUsername(loginForm.getUsername());
		mailSender.setPassword(loginForm.getPassword());
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailMessage.getFrom());
			helper.setTo(emailMessage.getTo());
			helper.setSubject(emailMessage.getSubject());
			helper.setText(emailMessage.getBody());
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		mailSender.send(message);
	}

	public EmailMessage getMessage(LoginForm loginForm, Installation installation, HashMap<String, String> hashMapUserDetailsForEachInstallation, ActivityEmail activityEmail)

	{
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFrom(loginForm.getUsername());
		emailMessage.setTo(loginForm.getUsername());
		emailMessage.setSubject(getSubject(loginForm,installation,hashMapUserDetailsForEachInstallation, activityEmail));
		emailMessage.setBody(getMessageBody(loginForm,installation, hashMapUserDetailsForEachInstallation, activityEmail));
		return emailMessage;
	}
	

	private String getSubject(LoginForm loginForm, Installation installation, HashMap<String, String> hashMapUserDetailsForEachInstallation, ActivityEmail activityEmail) {

		StringBuffer subject = new StringBuffer();
		switch (activityEmail) {
		case CREATE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " [Created] by "+loginForm.getUsername());
			break;

		case UPDATE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " [Updated] by "+loginForm.getUsername());
			break;
		
		case DELETE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " [Deleted] by "+loginForm.getUsername());
			break;
		
		case INUSE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " is being used by "+ loginForm.getUsername());
			break;
			
		case RELEASE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " has been released by "+ loginForm.getUsername());
			break;
		}		
	return subject.toString();
	}
	
	private String getMessageBody(LoginForm loginForm, Installation installation, HashMap<String, String> hashMapUserDetailsForEachInstallation, ActivityEmail activityEmail) {

		StringBuffer messageBody = new StringBuffer("Hi Team,\n\n");
		String users="";
		switch (activityEmail) {
		case CREATE:
			messageBody.append("The following environment has been created by "+loginForm.getUsername()+"\n\n");			
			break;

		case UPDATE:
			messageBody.append("The following environment has been updated by "+loginForm.getUsername()+"\n\n");
			messageBody.append("Environment is being used by following members :\n");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);
			messageBody.append("\n\n");	
			break;
			
		case DELETE:
			messageBody.append("The following environment has been deleted by "+loginForm.getUsername()+"\n\n");	
			messageBody.append("Environment was being used by following members :\n");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);	
			messageBody.append("\n\n");	
			break;

		case INUSE:
			messageBody.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " is being used by "+ loginForm.getUsername()+"\n\n");
			messageBody.append("Environment is now being used by following members :\n");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users!=null)
			{
				users=users+","+loginForm.getUsername();
			}
			else
			{
				users=loginForm.getUsername();
			}			
			messageBody.append(users);
			messageBody.append("\n\n");	
			break;
			
		case RELEASE:
			messageBody.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " has been released by "+ loginForm.getUsername()+"\n\n");
			messageBody.append("Environment is now being used by following members :\n");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users!=null)
			{
				users=users.replace(loginForm.getUsername(), "");
				if(users!=null && !users.isEmpty())
				{
					if(users.charAt(0)==',')
					{
						users= users.substring(1, users.length());
					}
					else if(users.charAt(users.length()-1)==',')
					{
						users= users.substring(0, users.length()-1);
					}
					else
					users=users.replace(",,", ",");
				}
			}
			else if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);	
			messageBody.append("\n\n");	
			break;
		}		
				
		messageBody.append("IP : "+ installation.getIp());
		messageBody.append("\n");
		messageBody.append("environment_type : "+ installation.getEnvironmentType());
		messageBody.append("\n");
		messageBody.append("Status : "+ installation.getStatus());
		messageBody.append("\n");
		messageBody.append("Version : "+ installation.getVersion());
		messageBody.append("\n");
		messageBody.append("Middleware Location : "+ installation.getMiddlewareLocation());
		messageBody.append("\n");
		messageBody.append("Schema Prefix : "+ installation.getSchemaPrefix());
		messageBody.append("\n");
		messageBody.append("Admin Server HTTP Port : "+ installation.getAdminServerHTTPPort());
		messageBody.append("\n");
		messageBody.append("Admin Server HTTPS Port : "+ installation.getAdminServerHTTPSPort());
		messageBody.append("\n");
		messageBody.append("Manage Server HTTP Port : "+ installation.getManagedServerHTTPPort());
		messageBody.append("\n");
		messageBody.append("Manage Server HTTPS Port : "+ installation.getManagedServerHTTPSPort());
		messageBody.append("\n");
		messageBody.append("Installed By : "+ installation.getEnvironmentType());
		messageBody.append("\n");
		messageBody.append("VNC port : "+ installation.getVncPort());
		messageBody.append("\n");
		if(installation.getEnvironmentType().equalsIgnoreCase("ICS IC") || installation.getEnvironmentType().equalsIgnoreCase("ICS EC"))
		{
			messageBody.append("ICS Console Link : "+"http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort()+"/ics");
			messageBody.append("\n");
		}
		messageBody.append("Admin Console Link : "+"http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort()+"/console");
		messageBody.append("\n");
		messageBody.append("EM Console Link : "+"http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort()+"/em");
		messageBody.append("\n\n");
		messageBody.append("Thanks and Regards,\n");
		messageBody.append(loginForm.getUsername());
				
		return messageBody.toString();
	}
}