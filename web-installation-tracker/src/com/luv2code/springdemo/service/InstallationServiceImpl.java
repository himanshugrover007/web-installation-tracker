package com.luv2code.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.InstallationDAO;
import com.luv2code.springdemo.entity.Installation;

@Service
public class InstallationServiceImpl implements InstallationService {

	// need to inject installation dao
	@Autowired
	private InstallationDAO installationDAO;
	
	@Override
	@Transactional
	public List<Installation> getInstallations() {
		return installationDAO.findAll();
	}

	@Override
	@Transactional
	public void saveInstallation(Installation theInstallation) {

		installationDAO.save(theInstallation);
	}
	
	@Override
	@Transactional
	public void updateInstallation(Installation theInstallation) {

		installationDAO.update(theInstallation);
	}

	@Override
	@Transactional
	public Installation getInstallation(int theId) {
		
		return installationDAO.get(theId);
	}

	@Override
	@Transactional
	public void deleteInstallation(int theId) {
		
		Installation installation = new Installation()		;
		installation.setId(theId);
		installationDAO.delete(installation);
	}
}





