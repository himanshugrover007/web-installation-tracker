package com.luv2code.springdemo.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.text.StrSubstitutor;
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
			helper.setTo(new String[]{emailMessage.getTo(),"sadanandam.v@bcone.com", "srinidhi.k@bcone.com"});
			helper.setSubject(emailMessage.getSubject());
			helper.setText(emailMessage.getBody(),true);
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		mailSender.send(message);
	}

	public EmailMessage getMessage(LoginForm loginForm, Installation installation, HashMap<String, String> hashMapUserDetailsForEachInstallation, ActivityEmail activityEmail)

	{
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFrom(loginForm.getUsername());
		emailMessage.setTo("osf_team_noida@bcone.com");
		emailMessage.setSubject(getSubject(loginForm,installation,hashMapUserDetailsForEachInstallation, activityEmail));
		emailMessage.setBody(getMessageBodyForMail(loginForm,installation, hashMapUserDetailsForEachInstallation, activityEmail));
		return emailMessage;
	}
	

	private String getSubject(LoginForm loginForm, Installation installation, HashMap<String, String> hashMapUserDetailsForEachInstallation, ActivityEmail activityEmail) {

		StringBuffer subject = new StringBuffer("[WIT] ");
		switch (activityEmail) {
		case CREATE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " [Created] by "+loginForm.getUsername());
			break;

		case UPDATE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " [Updated] by "+loginForm.getUsername());
			break;
		
		case DELETE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " [Marked for Deletion] by "+loginForm.getUsername());
			break;
			
		case PERMANENTDELETE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " [Permanently Deleted] by "+loginForm.getUsername());
			break;
		
		case INUSE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " is being [Used] by "+ loginForm.getUsername());
			break;
			
		case RELEASE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " has been [Released] by "+ loginForm.getUsername());
			break;
			
		case DEMOBLOCK:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " has been BLOCKED FOR DEMO by "+ loginForm.getUsername());
			break;
			
		case DEMORELEASE:
			subject.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " used for DEMO has been RELEASED by "+ loginForm.getUsername());
			break;
		}		
	return subject.toString();
	}
	
	private String getMessageForBeginning(LoginForm loginForm, Installation installation, HashMap<String, String> hashMapUserDetailsForEachInstallation, ActivityEmail activityEmail) {

		StringBuffer messageBody = new StringBuffer();
		String users="";
		switch (activityEmail) {
		case CREATE:
			messageBody.append("The following environment has been created by "+loginForm.getUsername());	
			messageBody.append("<br><br>");	
			break;

		case UPDATE:
			messageBody.append("The following environment has been updated by "+loginForm.getUsername()+"<br><br>");
			messageBody.append("Environment is used by following members :<br>");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);
			messageBody.append("<br><br>");	
			break;
			
		case DELETE:
			messageBody.append("The following environment has been marked for deletion by "+loginForm.getUsername()+"<br><br>");
			messageBody.append("Environment was being used by following members :<br>");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);	
			messageBody.append("<br><br>");	
			break;
			
		case PERMANENTDELETE:
			messageBody.append("The following environment has been permanently deleted by "+loginForm.getUsername()+"<br><br>");
			messageBody.append("Environment was being used by following members :<br>");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);	
			messageBody.append("<br><br>");	
			break;

		case INUSE:
			messageBody.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " is being used by "+ loginForm.getUsername()+"<br><br>");
			messageBody.append("Environment is now being used by following members :<br>");
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
			messageBody.append("<br><br>");	
			break;
			
		case RELEASE:
			messageBody.append(installation.getEnvironmentType()+" - http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort() + " has been released by "+ loginForm.getUsername()+"<br><br>");
			messageBody.append("Environment is now being used by following members :<br>");
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
				else
				{
					users="NONE";
				}
			}
			else if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);	
			messageBody.append("<br><br>");	
			break;
			
		case DEMOBLOCK:
			messageBody.append("The following environment has been blocked for demo by "+loginForm.getUsername()+"<br><br>");
			messageBody.append("Environment is used by following members :<br>");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);
			messageBody.append("<br><br>");	
			break;
			
		case DEMORELEASE:
			messageBody.append("Demo is complete and environment has been released by "+loginForm.getUsername()+"<br><br>");
			messageBody.append("Environment is used by following members :<br>");
			users=hashMapUserDetailsForEachInstallation.get(String.valueOf(installation.getId()));
			if(users==null)
			{
				users="NONE";
			}
			messageBody.append(users);
			messageBody.append("<br><br>");	
			break;
		}				
		return messageBody.toString();
	}
	
	private String getMessageBodyForMail(LoginForm loginForm, Installation installation, HashMap<String, String> hashMapUserDetailsForEachInstallation, ActivityEmail activityEmail)
	{

		StringBuilder myvar = new StringBuilder(); 
		String environmentSOAorICS="";
		if(installation.getEnvironmentType().equalsIgnoreCase("SOA 11G") || installation.getEnvironmentType().equalsIgnoreCase("SOA 12C"))
	     {
			environmentSOAorICS="SOA ";
	     }		
		
		myvar.append("<html>")
		     .append("	<head>")
		     .append("		<title></title>")
		     .append("	</head>")
		     .append("	<body>")
		     .append("		<p>")
		     .append("			Hi Team,</p>")
		     .append("			{beginningmessage}")
		     .append("		<table border =\"1\"1cellpadding=\"0\" cellspacing=\"0\"")
		     .append("			<tbody>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							IP</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{ip}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Environment Type</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{environmenttype}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Status</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{status}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Version</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{version}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Middleware Location</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{middlewarelocation}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Bits Location</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{bitslocation}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Schema Prefix</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{schemaprefix}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Admin Server HTTP Port</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{adminserverhttpport}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Admin Server HTTPS Port</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{adminserverhttpsport}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							"+ environmentSOAorICS +"Managed Server HTTP Port</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{managedserverhttpport}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							"+ environmentSOAorICS +"Managed Server HTTPS Port</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{managedserverhttpsport}</p>")
		     .append("					</td>")
		     .append("				</tr>");
		
	     if(installation.getEnvironmentType().equalsIgnoreCase("SOA 11G") || installation.getEnvironmentType().equalsIgnoreCase("SOA 12C"))
	     {
	    	 if(installation.getManagedServer2HTTPPort()!=null && !installation.getManagedServer2HTTPPort().isEmpty())
	    	 { myvar.append("				<tr>")
				     .append("					<td style=\"width:200px;\">")
				     .append("						<p>")
				     .append("							OSB Managed Server HTTP Port</p>")
				     .append("					</td>")
				     .append("					<td style=\"width:312px;\">")
				     .append("						<p>")
				     .append("							{managedserver2httpport}</p>")
				     .append("					</td>")
				     .append("				</tr>");
	    	 }
	    	 if(installation.getManagedServer2HTTPSPort()!=null && !installation.getManagedServer2HTTPSPort().isEmpty())
	    	 {
				myvar.append("				<tr>")
				     .append("					<td style=\"width:200px;\">")
				     .append("						<p>")
				     .append("							OSB Managed Server HTTPS Port</p>")
				     .append("					</td>")
				     .append("					<td style=\"width:312px;\">")
				     .append("						<p>")
				     .append("							{managedserver2httpsport}</p>")
				     .append("					</td>")
				     .append("				</tr>");
	    	 }
	     }
		myvar.append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							Installed By</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{installedby}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">")
		     .append("						<p>")
		     .append("							VNC port</p>")
		     .append("					</td>")
		     .append("					<td style=\"width:312px;\">")
		     .append("						<p>")
		     .append("							{vncport}</p>")
		     .append("					</td>")
		     .append("				</tr>")
		     .append("				<tr>")
		     .append("					<td style=\"width:200px;\">");		     
		if(installation.getEnvironmentType().equalsIgnoreCase("ICS IC") || installation.getEnvironmentType().equalsIgnoreCase("ICS EC"))
		     {
				myvar.append("						<p>")
				     .append("							ICS Console Link</p>");
		     }
		
		myvar.append("						<p>")
		     .append("							Admin Console Link</p>")
		     .append("						<p>")
		     .append("							EM Console Link</p>");
	    if(installation.getEnvironmentType().equalsIgnoreCase("SOA 12C") || installation.getEnvironmentType().equalsIgnoreCase("SOA 11G"))
		     {
	    		if(installation.getManagedServer2HTTPPort()!=null && installation.getManagedServer2HTTPSPort()!=null)
				{
	    			myvar.append("						<p>")
	    				 .append("							Service Bus Console Link</p>");
				}
		     }
		     
	    myvar.append("					</td>")
		     .append("					<td style=\"width:312px;\">");
		
		if(installation.getEnvironmentType().equalsIgnoreCase("ICS IC") || installation.getEnvironmentType().equalsIgnoreCase("ICS EC"))
	     {
			myvar.append("						<p>")
		     .append("							{icsconsolelink}</p>");
	     }
		
		myvar.append("						<p>")
		     .append("							{adminconsolelink}</p>")
		     .append("						<p>")
		     .append("							{emconsolelink}</p>");
		
		if(installation.getEnvironmentType().equalsIgnoreCase("SOA 12C") || installation.getEnvironmentType().equalsIgnoreCase("SOA 11G"))
		{
			if(installation.getManagedServer2HTTPPort()!=null && installation.getManagedServer2HTTPSPort()!=null)
			{
				myvar.append("						<p>")
					 .append("							{sbconsolelink}</p>");
			}
		}     
		
		myvar.append("					</td>")
		     .append("				</tr>")
		     .append("			</tbody>")
		     .append("		</table>")
		     .append("		<p>")
		     .append("			Thanks and Regards,<br />")
		     .append("			{username}</p>")
		     .append("	</body>")
		     .append("</html>");
	
		
		
	        
		Map<String, String> data = new HashMap<String, String>();
		data.put("beginningmessage", getMessageForBeginning(loginForm, installation, hashMapUserDetailsForEachInstallation, activityEmail) );
		data.put("ip",installation.getIp());
		data.put("environmenttype",installation.getEnvironmentType());
		if(installation.getStatus().equalsIgnoreCase("A"))
			data.put("status","ACTIVE");
		else
			data.put("status","INACTIVE");
		data.put("version",installation.getVersion());
		data.put("middlewarelocation",installation.getMiddlewareLocation());
		data.put("bitslocation",installation.getBitsLocation());
		data.put("schemaprefix",installation.getSchemaPrefix());
		data.put("adminserverhttpport",installation.getAdminServerHTTPPort());
		data.put("adminserverhttpsport",installation.getAdminServerHTTPSPort());
		data.put("managedserverhttpport",installation.getManagedServerHTTPPort());
		data.put("managedserverhttpsport",installation.getManagedServerHTTPSPort());
		data.put("managedserver2httpport",installation.getManagedServer2HTTPPort());
		data.put("managedserver2httpsport",installation.getManagedServer2HTTPSPort());
		data.put("installedby",installation.getInstalledBy());
		data.put("vncport",installation.getVncPort());
		
		if(installation.getEnvironmentType().equalsIgnoreCase("ICS IC"))
		{
			data.put("icsconsolelink","http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort()+"/ics");
		}
		else if(installation.getEnvironmentType().equalsIgnoreCase("ICS EC"))
		{
			data.put("icsconsolelink","http://"+ installation.getIp()+":"+installation.getManagedServerHTTPPort()+"/ic/integration/home/faces/icslogin.jspx");
		}
		
		data.put("adminconsolelink","http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort()+"/console");
		data.put("emconsolelink","http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort()+"/em");
		
		if(installation.getEnvironmentType().equalsIgnoreCase("SOA 12C") || installation.getEnvironmentType().equalsIgnoreCase("SOA 11G"))
		{
			if(installation.getManagedServer2HTTPPort()!=null && installation.getManagedServer2HTTPSPort()!=null)
			data.put("sbconsolelink","http://"+ installation.getIp()+":"+installation.getAdminServerHTTPPort()+"/sbconsole");
		}
		

		data.put("username",loginForm.getUsername());
		
		String message = StrSubstitutor.replace(myvar,data,"{","}");
		
		return message;
	}
	
	
}