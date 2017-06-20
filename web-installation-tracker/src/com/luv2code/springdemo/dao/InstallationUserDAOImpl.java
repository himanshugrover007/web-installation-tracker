package com.luv2code.springdemo.dao;

import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.InstallationUserDetails;

@Repository
public class InstallationUserDAOImpl extends Dao<InstallationUserDetails> implements InstallationUserDAO {

	public InstallationUserDAOImpl() {
		super(InstallationUserDetails.class);
	}

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	/*
	 * /@Override public List<Installation> getInstallations() {
	 * 
	 * // get the current hibernate session Session currentSession =
	 * sessionFactory.getCurrentSession();
	 * 
	 * // create a query ... sort by last name Query<Installation> theQuery =
	 * currentSession.createQuery("from Installation order by id",
	 * Installation.class);
	 * 
	 * // execute query and get result list List<Installation> installations =
	 * theQuery.getResultList();
	 * 
	 * // return the results return installations; }
	 * 
	 * @Override public void saveInstallation(Installation theInstallation) {
	 * 
	 * // get current hibernate session Session currentSession =
	 * sessionFactory.getCurrentSession();
	 * 
	 * // save/upate the customer ... finally LOL
	 * currentSession.saveOrUpdate(theInstallation);
	 * 
	 * }
	 * 
	 * @Override public Installation getInstallation(int theId) {
	 * 
	 * // get the current hibernate session Session currentSession =
	 * sessionFactory.getCurrentSession();
	 * 
	 * // now retrieve/read from database using the primary key Installation
	 * theInstallation = currentSession.get(Installation.class, theId);
	 * 
	 * return theInstallation; }
	 * 
	 * @Override public void deleteInstallation(int theId) {
	 * 
	 * // get the current hibernate session Session currentSession =
	 * sessionFactory.getCurrentSession();
	 * 
	 * // delete object with primary key Query theQuery = currentSession.
	 * createQuery("delete from Installation where id=:installationId");
	 * theQuery.setParameter("installationId", theId);
	 * 
	 * theQuery.executeUpdate(); }
	 */

	@Override
	public void deleteDetailsFromInstallationUser(InstallationUserDetails theInstallationUser) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete object with primary key
		Query theQuery = currentSession.createQuery(
				"delete from InstallationUserDetails where installationId=" + theInstallationUser.getInstallationId()
						+ " and installationUser='" + theInstallationUser.getInstallationUser() + "'");
		theQuery.executeUpdate();
	}

	public HashMap<String, String> getUserDetailsForEachInstallation() {
		HashMap<String, String> hashMap = new HashMap<>();
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete object with primary key
		SQLQuery theQuery = currentSession.createSQLQuery(
				"SELECT installation_id, GROUP_CONCAT(installation_user) FROM web_customer_tracker.installation_user_details GROUP BY installation_id");

		List<Object[]> rows = theQuery.list();
		for (Object[] row : rows) {

			hashMap.put(row[0].toString(), row[1].toString());

		}
		return hashMap;
	}

	@Override
	public void deleteUserFromInstallationUserTable(int theInstallationId) {
		// TODO Auto-generated method stub
		// get the current hibernate session
				Session currentSession = sessionFactory.getCurrentSession();

				// delete object with primary key
				Query theQuery = currentSession.createQuery(
						"delete from InstallationUserDetails where installationId=" + theInstallationId);
				theQuery.executeUpdate();
	}

}
