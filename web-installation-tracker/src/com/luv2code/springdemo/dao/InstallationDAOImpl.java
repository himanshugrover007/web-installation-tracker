package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Installation;

@Repository
public class InstallationDAOImpl extends Dao<Installation> implements InstallationDAO {

	
	public InstallationDAOImpl() {
        super(Installation.class);
    }

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
/*			
	/@Override
	public List<Installation> getInstallations() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// create a query  ... sort by last name
		Query<Installation> theQuery = 
				currentSession.createQuery("from Installation order by id",
											Installation.class);
		
		// execute query and get result list
		List<Installation> installations = theQuery.getResultList();
				
		// return the results		
		return installations;
	}

	@Override
	public void saveInstallation(Installation theInstallation) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/upate the customer ... finally LOL
		currentSession.saveOrUpdate(theInstallation);
		
	}

	@Override
	public Installation getInstallation(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		Installation theInstallation = currentSession.get(Installation.class, theId);
		
		return theInstallation;
	}

	@Override
	public void deleteInstallation(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery("delete from Installation where id=:installationId");
		theQuery.setParameter("installationId", theId);
		
		theQuery.executeUpdate();		
	}
*/
	
	@Override
	public List<Installation> findAll() {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria cr = session.createCriteria(Installation.class);
		// To sort records in ascending order
		cr.add(Restrictions.eq("deleted", 0));
		cr.addOrder(Order.desc("lastUpdatedDate"));

		return super.findAll(cr);
	}
}











