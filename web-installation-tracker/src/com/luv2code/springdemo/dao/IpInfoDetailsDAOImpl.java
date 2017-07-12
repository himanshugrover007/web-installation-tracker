package com.luv2code.springdemo.dao;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
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
	
	public HashMap<String, String> getPortsUsedInEachIP() {
		HashMap<String, String> hashMap = new HashMap<>();
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete object with primary key
		NativeQuery theQuery = currentSession.createNativeQuery("select ip, GROUP_CONCAT(CONCAT_WS(',',i.admin_server_HTTPPort,i.admin_server_HTTPSPort,i.managed_server_HTTPPort,i.managed_server_HTTPSPort, NULLIF(i.managed_server_2_HTTPPort,''),NULLIF(i.managed_server_2_HTTPSPort,''))) ports "
				+ "from installations i where i.deleted=0 and i.status='A' group by ip");

		List<Object[]> rows = theQuery.getResultList();
		for (Object[] row : rows) {

			hashMap.put(row[0].toString(), row[1].toString());

		}
		return hashMap;
	}
	

}