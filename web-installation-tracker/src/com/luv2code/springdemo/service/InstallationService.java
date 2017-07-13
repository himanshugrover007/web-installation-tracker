package com.luv2code.springdemo.service;

import java.util.List;

import com.luv2code.springdemo.entity.Installation;

public interface InstallationService {

	public List<Installation> getInstallations(int deleted);

	public void saveInstallation(Installation theInstallation);
	
	public void updateInstallation(Installation theInstallation);

	public Installation getInstallation(int theId);

	public void deleteInstallation(int theId);
	
}
