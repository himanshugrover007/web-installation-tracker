package com.luv2code.springdemo.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface GenericDaoInterface<T> {

	/*
	 * Return a list defined by the Criteria
	 */
	List<T> findAll(Criteria criteria);

	/*
	 * Return all of type 'T' (as determined by the generic member variable type 'entityClass')
	 */
	List<T> findAll();

	/*
	 * Use for pagination
	 * Filter by Criteria
	 */
	List<T> findRange(Criteria criteria, int beginIndex, int endIndex);

	/*
	 * Use for pagination
	 */
	List<T> findRange(int beginIndex, int pageSize);

	/*
	 * Use for pagination but order by 'propertyName' before paging
	 * Filter by criteria before paging
	 */
	List<T> findRangeOrderFirst(Criteria criteria, int beginIndex, int pageSize, String propertyName, boolean asc);

	/*
	 * Use for pagination but order by 'propertyName' before paging
	 */
	List<T> findRangeOrderFirst(int beginIndex, int pageSize, String propertyName, boolean asc);

	/*
	 * Use for pagination
	 * Filter by Criteria before paging
	 */
	List<T> findPage(Criteria criteria, int pageNumber, int pageSize);

	/*
	 * Use for pagination
	 *
	 */
	List<T> findPage(int pageNumber, int pageSize);

	/*
	 * Return a page
	 * Filter by criteria first
	 * Order by propertyName before returning a page
	 */
	List<T> findPageOrderFirst(Criteria criteria, int pageNumber, int pageSize, String propertyName, boolean asc);

	/*
	 * Return a page
	 * Order by propertyName before returning a page
	 */
	List<T> findPageOrderFirst(int pageNumber, int pageSize, String propertyName, boolean asc);

	/*
	 * CRUD
	 * Retrieve
	 */
	T get(Serializable id);

	/*
	 * CRUD
	 * Retrieve
	 */
	T get(Class c, Serializable id);

	/*
	 * CRUD
	 * Retrieve
	 */
	T get(Criteria criteria);

	/*
	 * CRUD
	 * Delete
	 */
	void delete(T entity);

	/*
	 * CRUD
	 * Create
	 */
	T insert(T entity);

	/*
	 * CRUD
	 * Create or Update
	 */
	T save(T entity);

	/*
	 * CRUD
	 * Update
	 */
	void update(T entity);

	Session getCurrentSession();

	void setSessionFactory(SessionFactory sessionFactory);

	Class<T> getEntityClass();

	void setEntityClass(Class<T> entityClass);

}