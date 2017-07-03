package com.luv2code.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.IpInfoDetailsDAO;
import com.luv2code.springdemo.entity.IpInfoDetails;

@Service
public class IpInfoDetailsServiceImpl implements IpInfoDetailsService {
	
	@Autowired
	private IpInfoDetailsDAO ipInfoDetailsDAO;

	@Override
	@Transactional
	public List<IpInfoDetails> getIpInfoDetails() {
		// TODO Auto-generated method stub
		return ipInfoDetailsDAO.findAll();
	}

	@Override
	@Transactional
	public void saveIpInfoDetails(IpInfoDetails ipInfoDetails) {
		ipInfoDetailsDAO.save(ipInfoDetails);

	}

	@Override
	@Transactional
	public void updateIpInfoDetails(IpInfoDetails ipInfoDetails) {
		ipInfoDetailsDAO.update(ipInfoDetails);

	}

	@Override
	@Transactional
	public IpInfoDetails getIpInfoDetails(int theId) {
		return ipInfoDetailsDAO.get(theId);
	}

	@Override
	@Transactional
	public void deleteIpInfoDetails(int theId) {
		
		IpInfoDetails ipInfoDetails = new IpInfoDetails()		;
		ipInfoDetails.setId(theId);
		ipInfoDetailsDAO.delete(ipInfoDetails);
	}

}
