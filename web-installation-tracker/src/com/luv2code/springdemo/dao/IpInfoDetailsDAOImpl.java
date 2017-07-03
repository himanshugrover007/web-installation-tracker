package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.IpInfoDetails;

@Repository
public class IpInfoDetailsDAOImpl extends Dao<IpInfoDetails> implements IpInfoDetailsDAO {

	public IpInfoDetailsDAOImpl() {
		super(IpInfoDetails.class);

	}
	
	@Override
	public List<IpInfoDetails> findAll() {
		
		Session session = sessionFactory.getCurrentSession();		
		Criteria cr = session.createCriteria(IpInfoDetails.class);		
		cr.add(Restrictions.eq("deleted", 0));		
		return super.findAll(cr);
		
	}
}