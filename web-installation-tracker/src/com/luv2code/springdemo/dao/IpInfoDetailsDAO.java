package com.luv2code.springdemo.dao;

import java.util.HashMap;

import com.luv2code.springdemo.entity.IpInfoDetails;

public interface IpInfoDetailsDAO extends GenericDaoInterface<IpInfoDetails>{

//	public List<Installation> getInstallations();
//
//	public void saveInstallation(Installation theInstallation);
//
//	public Installation getInstallation(int theId);
//
//	public void deleteInstallation(int theId);
	
	public HashMap<String, String> getPortsUsedInEachIP();
	
}
