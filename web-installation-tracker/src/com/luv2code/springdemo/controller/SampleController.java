package com.luv2code.springdemo.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luv2code.springdemo.entity.LoginForm;

/**
 * Handles requests for the application pages.
 * 
 * @author Giuseppe Urso
 */
@Controller
public class SampleController {
	
	/*@Autowired 
	public LdapTemplate ldapTemplate;*/

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	// static String bundle = "configuration";
	// public static ResourceBundle settings = ResourceBundle.getBundle(bundle);

	/**
	 * The request mapper for welcome page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		return "welcome";
	}

	/**
	 * Simply selects the login view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLogin(Model model, LoginForm loginform) {
		logger.info("Login page");
		if (!model.containsAttribute("error")) {
			model.addAttribute("error", false);
		}
		model.addAttribute("loginAttribute", loginform);
		return "login";
	}

	/**
	 * The POST method to submit login credentials.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(Model model, LoginForm loginform, Locale locale, HttpServletRequest request) throws Exception {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);

		String username = loginform.getUsername();
		String password = loginform.getPassword();

		logger.info("Login attempt for username " + username + " at: " + formattedDate);

		// A simple authentication manager
		if (username != null && password != null) {
			
			if (!username.contains("@"))
			{
				username=username+"@bcone.com";
			}
			
			boolean autheticatedFlag=authenticate(username,password);
//			if (username.equalsIgnoreCase("himanshu") && password.equalsIgnoreCase("grover")) {
			if (autheticatedFlag) {

			// Set a session attribute to check authentication then redirect
				// to the welcome uri;
				loginform.setUsername(username);
				request.getSession().setAttribute("LOGGEDIN_USER", loginform);
				return "redirect:/installation/list?deleted=0";
			} else {
				return "redirect:/login.failed";
			}
		} else {
			return "redirect:/login.failed";
		}
	}

	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(Model model, Locale locale, HttpServletRequest request) throws Exception {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);

		LoginForm loginform = (LoginForm) request.getSession().getAttribute("LOGGEDIN_USER");

		if (loginform != null) {
			String username = loginform.getUsername();
			String password = loginform.getPassword();

			System.out.println("Login attempt for username " + username + " at: " + formattedDate);

			// A simple authentication manager
			if (username != null && password != null) {

				request.getSession().invalidate();
				return "redirect:/";
			}
		}
		return "redirect:/";
	}

	/**
	 * The login failed controller
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login.failed", method = RequestMethod.GET)
	public String loginFailed(Model model, LoginForm loginForm) {
		logger.debug("Showing the login failed page");
		model.addAttribute("error", true);
		model.addAttribute("loginAttribute", loginForm);
		return "login";
	}
	
	
	private boolean authenticate(String username, String password){
		
		boolean authed =false;
		try {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl("ldap://bcone.com:389");
		contextSource.setBase("dc=bcone,dc=com");
		contextSource.setUserDn(username);
		contextSource.setPassword(password);
		contextSource.setReferral("follow");
		contextSource.afterPropertiesSet();

		LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
		
		
			ldapTemplate.afterPropertiesSet();
		

		String email = username;
		String name   = email.split("@")[0];
		//String domain = email.substring(email.lastIndexOf("@") +1);
		
		System.out.println("name--------->"+name);
		// Perform the authentication.
		Filter filter = new EqualsFilter("sAMAccountName", name);

		authed = ldapTemplate.authenticate("",
		    filter.encode(),
		    password);

		// Display the results.
		System.out.println("Authenticated: " + authed);
		
		return authed;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return authed;
		}		

}
}