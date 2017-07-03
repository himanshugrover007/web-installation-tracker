package com.luv2code.springdemo.entity.custom.validator;

import java.util.HashSet;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.luv2code.springdemo.entity.Installation;

@Component
public class InstallationValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Installation.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		Installation installation = (Installation) target;

		String adminServerHTTPPort = installation.getAdminServerHTTPPort() == null ? ""
				: installation.getAdminServerHTTPPort();
		String adminServerHTTPSPort = installation.getAdminServerHTTPSPort() == null ? ""
				: installation.getAdminServerHTTPSPort();
		String managedServerHTTPPort = installation.getManagedServerHTTPPort() == null ? ""
				: installation.getManagedServerHTTPPort();
		String managedServerHTTPSPort = installation.getManagedServerHTTPSPort() == null ? ""
				: installation.getManagedServerHTTPSPort();
		String managedServer2HTTPPort = installation.getManagedServer2HTTPPort() == null ? ""
				: installation.getManagedServer2HTTPPort();
		String managedServer2HTTPSPort = installation.getManagedServer2HTTPSPort() == null ? ""
				: installation.getManagedServer2HTTPSPort();
		
		if(installation.getManagedServer2HTTPPort()!=null && !installation.getManagedServer2HTTPPort().isEmpty()
		&& (installation.getManagedServer2HTTPSPort()==null || installation.getManagedServer2HTTPSPort().isEmpty()))
		{
			errors.rejectValue("managedServer2HTTPSPort", "mandatory.installation.managedServer2HTTPSPort");			
		}
		
		if(installation.getManagedServer2HTTPSPort()!=null && !installation.getManagedServer2HTTPSPort().isEmpty()
				&&(installation.getManagedServer2HTTPPort()==null || installation.getManagedServer2HTTPPort().isEmpty()))
		{
			errors.rejectValue("managedServer2HTTPPort", "mandatory.installation.managedServer2HTTPPort");			
		}

		if (installation.getEnvironmentType() != null && (installation.getEnvironmentType().equalsIgnoreCase("ICS EC")
				|| installation.getEnvironmentType().equalsIgnoreCase("ICS IC"))) {
			
			HashSet<String> hashSet= new HashSet<>();
			hashSet.add(adminServerHTTPPort);
			hashSet.add(adminServerHTTPSPort);
			hashSet.add(managedServerHTTPPort);
			hashSet.add(managedServerHTTPSPort);

			if (hashSet.size()!=4) {
				errors.rejectValue("adminServerHTTPPort", "notunique.installation.adminServerHTTPPort");
				errors.rejectValue("adminServerHTTPSPort", "notunique.installation.adminServerHTTPPort");
				errors.rejectValue("managedServerHTTPPort", "notunique.installation.managedServerHTTPPort");
				errors.rejectValue("managedServerHTTPSPort", "notunique.installation.managedServerHTTPSPort");
			}
		}
		else
		{
			if(installation.getManagedServer2HTTPPort()!=null &&  !installation.getManagedServer2HTTPPort().isEmpty()
					&& installation.getManagedServer2HTTPSPort()!=null &&  !installation.getManagedServer2HTTPSPort().isEmpty())
			{
				HashSet<String> hashSet= new HashSet<>();
				hashSet.add(adminServerHTTPPort);
				hashSet.add(adminServerHTTPSPort);
				hashSet.add(managedServerHTTPPort);
				hashSet.add(managedServerHTTPSPort);
				hashSet.add(managedServer2HTTPPort);
				hashSet.add(managedServer2HTTPSPort);
				
				if (hashSet.size()!=6) {
					errors.rejectValue("adminServerHTTPPort", "notunique.installation.adminServerHTTPPort");
					errors.rejectValue("adminServerHTTPSPort", "notunique.installation.adminServerHTTPPort");
					errors.rejectValue("managedServerHTTPPort", "notunique.installation.managedServerHTTPPort");
					errors.rejectValue("managedServerHTTPSPort", "notunique.installation.managedServerHTTPSPort");
					errors.rejectValue("managedServer2HTTPPort", "notunique.installation.managedServer2HTTPSPort");
					errors.rejectValue("managedServer2HTTPSPort", "notunique.installation.managedServer2HTTPSPort");
				}				
			}
			else
			{
				HashSet<String> hashSet= new HashSet<>();
				hashSet.add(adminServerHTTPPort);
				hashSet.add(adminServerHTTPSPort);
				hashSet.add(managedServerHTTPPort);
				hashSet.add(managedServerHTTPSPort);
				
				if (hashSet.size()!=4) {
					errors.rejectValue("adminServerHTTPPort", "notunique.installation.adminServerHTTPPort");
					errors.rejectValue("adminServerHTTPSPort", "notunique.installation.adminServerHTTPPort");
					errors.rejectValue("managedServerHTTPPort", "notunique.installation.managedServerHTTPPort");
					errors.rejectValue("managedServerHTTPSPort", "notunique.installation.managedServerHTTPSPort");
				}			
			}
				
			
		}		

	}
}