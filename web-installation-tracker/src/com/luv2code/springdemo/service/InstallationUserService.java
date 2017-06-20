package com.luv2code.springdemo.service;

import java.util.HashMap;
import java.util.List;

import com.luv2code.springdemo.entity.InstallationUserDetails;

public interface InstallationUserService {

	public List<InstallationUserDetails> getInstallationUsers();

	public void saveInstallationUser(InstallationUserDetails theInstallationUser);
	
	public void deleteDetailsFromInstallationUser(InstallationUserDetails theInstallationUser);
	
	public HashMap<String, String> getUserDetailsForEachInstallation();

	public void deleteUserFromInstallationUserTable(int theInstallationId);
	
}
