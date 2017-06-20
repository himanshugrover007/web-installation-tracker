package com.luv2code.springdemo.dao;

import java.util.HashMap;

import com.luv2code.springdemo.entity.InstallationUserDetails;

public interface InstallationUserDAO extends GenericDaoInterface<InstallationUserDetails>{

//	public List<Installation> getInstallations();
//
//	public void saveInstallation(Installation theInstallation);
//
//	public Installation getInstallation(int theId);
//
	public void deleteDetailsFromInstallationUser(InstallationUserDetails theInstallationUser);
	
	public HashMap<String, String> getUserDetailsForEachInstallation();

	public void deleteUserFromInstallationUserTable(int theInstallationId);	
}
