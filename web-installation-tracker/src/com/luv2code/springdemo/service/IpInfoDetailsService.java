package com.luv2code.springdemo.service;

import java.util.List;

import com.luv2code.springdemo.entity.IpInfoDetails;

public interface IpInfoDetailsService {

	public List<IpInfoDetails> getIpInfoDetails();

	public void saveIpInfoDetails(IpInfoDetails ipInfoDetails);
	
	public void updateIpInfoDetails(IpInfoDetails ipInfoDetails);

	public IpInfoDetails getIpInfoDetails(int theId);

	public void deleteIpInfoDetails(int theId);
	
}
