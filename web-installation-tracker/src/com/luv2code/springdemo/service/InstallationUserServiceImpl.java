package com.luv2code.springdemo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.InstallationUserDAO;
import com.luv2code.springdemo.entity.InstallationUserDetails;

@Service
public class InstallationUserServiceImpl implements InstallationUserService {

	// need to inject installation dao
	@Autowired
	private InstallationUserDAO installationUserDAO;
	
	@Override
	@Transactional
	public List<InstallationUserDetails> getInstallationUsers() {
		return installationUserDAO.findAll();
	}

	@Override
	@Transactional
	public void saveInstallationUser(InstallationUserDetails theInstallationUser) {

		installationUserDAO.save(theInstallationUser);
	}
	
		@Override
	@Transactional
	public void deleteDetailsFromInstallationUser(InstallationUserDetails theInstallationUser) {
		installationUserDAO.deleteDetailsFromInstallationUser(theInstallationUser);
	}
		
		@Override
		@Transactional
	public HashMap<String, String> getUserDetailsForEachInstallation(){
			return installationUserDAO.getUserDetailsForEachInstallation();
	}

	@Override
	@Transactional
	public void deleteUserFromInstallationUserTable(int theInstallationId) {
		// TODO Auto-generated method stub
		
		installationUserDAO.deleteUserFromInstallationUserTable(theInstallationId);			
	}
}





