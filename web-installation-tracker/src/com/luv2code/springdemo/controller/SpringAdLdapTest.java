package com.luv2code.springdemo.controller;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;

public class SpringAdLdapTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		// Setup the LDAP client (normally done via Spring context file).
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl("ldap://10.30.2.23:389");
		contextSource.setBase("dc=test,dc=com");
		contextSource.setUserDn("aduser1@test.com");
		contextSource.setPassword("test,123");
		contextSource.setReferral("follow");
		contextSource.afterPropertiesSet();

		LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
		ldapTemplate.afterPropertiesSet();

		// Perform the authentication.
		Filter filter = new EqualsFilter("sAMAccountName", "aduser1");

		boolean authed = ldapTemplate.authenticate("",
		    filter.encode(),
		    "test,123");

		// Display the results.
		System.out.println("Authenticated: " + authed);

	}

}
