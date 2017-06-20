/**
 *
 * @since 1.5
 * @version 1.0
 * 
 * A generic DAO abstract class.
 * Uses Hibernate
 * Provides all necessary DAO functionality
 * Fully customizable 
 * 
 * Suitable for Spring 3 
 * 
 * For batch updates see the hibernate documentation:
 * http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/batch.html
 * 
 */
package com.luv2code.springdemo.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransientObjectException;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class Dao<T> implements GenericDaoInterface<T> {
    
    private Class<T> entityClass;
    
    /*
     * If using Spring 3 ensure to facilitate AutoWiring by 
     * adding the Hibernate Transaction Manager in the application context xml configuration
     * e.g.
     * <bean id="sessionFactory"
              class="org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <property name="annotatedClasses">
            <list>
                <value>com.outbottle.genericdao.model.entities.TUser</value>
            </list>
        </property>
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.dialect">${hibernate.dialect}</prop>
                <prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
                <prop key="hibernate.c3p0.min_size">5</prop>
                <prop key="hibernate.c3p0.max_size">20</prop>
                <prop key="hibernate.c3p0.timeout">300</prop>
                <prop key="hibernate.c3p0.max_statements">50</prop>
                <prop key="hibernate.c3p0.idle_test_period">3000</prop>
            </props>
        </property>
    </bean>
    <bean id="hibernateTransactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory" />
    </bean>  
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    
    public Dao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    
    /*
     * Return a list defined by the Criteria
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findAll(org.hibernate.Criteria)
	 */
    @Override
	public List<T> findAll(Criteria criteria) {
        return criteria.list();
    }
    
    /*
     * Return all of type 'T' (as determined by the generic member variable type 'entityClass')
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findAll()
	 */
    @Override
	public List<T> findAll() {
        return findAll( sessionFactory.getCurrentSession().createCriteria(entityClass) );
    }
    
    
    /*
     * Use for pagination
     * Filter by Criteria
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findRange(org.hibernate.Criteria, int, int)
	 */
    @Override
	public List<T> findRange(Criteria criteria, int beginIndex, int endIndex) {
        criteria.setFirstResult(beginIndex);
        criteria.setMaxResults(endIndex-beginIndex);
        return criteria.list();
    }
    
    /*
     * Use for pagination
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findRange(int, int)
	 */
    @Override
	public List<T> findRange(int beginIndex, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
        return findRange(criteria, beginIndex, pageSize);
    }
    
    
    /*
     * Use for pagination but order by 'propertyName' before paging
     * Filter by criteria before paging
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findRangeOrderFirst(org.hibernate.Criteria, int, int, java.lang.String, boolean)
	 */
    @Override
	public List<T> findRangeOrderFirst(Criteria criteria, int beginIndex, int pageSize, String propertyName, boolean asc) {
        if( asc ) criteria.addOrder(Order.asc(propertyName));
        else criteria.addOrder(Order.desc(propertyName));
        criteria.setFirstResult(beginIndex);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }
    
    /*
     * Use for pagination but order by 'propertyName' before paging
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findRangeOrderFirst(int, int, java.lang.String, boolean)
	 */
    @Override
	public List<T> findRangeOrderFirst(int beginIndex, int pageSize, String propertyName, boolean asc) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
        return findRangeOrderFirst(criteria, beginIndex, pageSize, propertyName, asc);
    }
    
    
    /*
     * Use for pagination
     * Filter by Criteria before paging
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findPage(org.hibernate.Criteria, int, int)
	 */
    @Override
	public List<T> findPage(Criteria criteria, int pageNumber, int pageSize) {
        int beginIndex = pageNumber*pageSize;
        return findRange(criteria, beginIndex, pageSize);
    }
    
    /*
     * Use for pagination
     *
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findPage(int, int)
	 */
    @Override
	public List<T> findPage(int pageNumber, int pageSize ) {
        int beginIndex = pageNumber*pageSize;
        return findRange(beginIndex, pageSize);
    }
    
    
    /*
     * Return a page
     * Filter by criteria first
     * Order by propertyName before returning a page
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findPageOrderFirst(org.hibernate.Criteria, int, int, java.lang.String, boolean)
	 */
    @Override
	public List<T> findPageOrderFirst(Criteria criteria, int pageNumber, int pageSize, String propertyName, boolean asc ) {
        int beginIndex = pageNumber*pageSize;
        return findRangeOrderFirst(criteria, beginIndex, pageSize, propertyName, asc);
    }
    
    /*
     * Return a page
     * Order by propertyName before returning a page
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#findPageOrderFirst(int, int, java.lang.String, boolean)
	 */
    @Override
	public List<T> findPageOrderFirst(int pageNumber, int pageSize, String propertyName, boolean asc ) {
        int beginIndex = pageNumber*pageSize;
        return findRangeOrderFirst(beginIndex, pageSize, propertyName, asc);
    }

    /*
     * CRUD
     * Retrieve
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#get(java.io.Serializable)
	 */
    @Override
	@SuppressWarnings("unchecked")
    public T get(Serializable id) {
        return get(entityClass, id);
    }
    
    /*
     * CRUD
     * Retrieve
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#get(java.lang.Class, java.io.Serializable)
	 */
    @Override
	@SuppressWarnings("unchecked")
    public T get(Class c, Serializable id) {
        return (T)sessionFactory.getCurrentSession().get(c, id);
    }

    /*
     * CRUD
     * Retrieve
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#get(org.hibernate.Criteria)
	 */
    @Override
	@SuppressWarnings("unchecked")
    public T get(Criteria criteria) {
        return (T)criteria.uniqueResult();
    }
    
    /*
     * CRUD
     * Delete
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#delete(T)
	 */
    @Override
	public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    /*
     * CRUD
     * Create
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#insert(T)
	 */
    @Override
	@SuppressWarnings("unchecked")
    public T insert(T entity) {
        Serializable id = sessionFactory.getCurrentSession().save(entity);
        entity = get(id);
        return entity;
    }

    /*
     * CRUD
     * Create or Update
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#save(T)
	 */
    @Override
	@SuppressWarnings({"unchecked", "CallToThreadDumpStack"})
    public T save(T entity) {
        boolean pkOK = true;
        try {
            Long primaryKey = getPrimaryKey(entity);
            if( primaryKey != null && primaryKey > 0)
                update( entity );
            else
                entity = insert(entity);
        } catch( IllegalAccessException e ) {
            e.printStackTrace();
            pkOK = false;
        } catch( InvocationTargetException e ) {
            e.printStackTrace();
            pkOK = false;
        } catch( NoSuchMethodException e ) {
            e.printStackTrace();
            pkOK = false;
        } 
        
        if( !pkOK ) {
            try {
                update( entity );
            } catch( TransientObjectException e ) {
                //insert not update
                entity = insert(entity);
            }
        }
        
        return entity;
    }

    /*
     * CRUD
     * Update
     */
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#update(T)
	 */
    @Override
	public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }
   
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#getCurrentSession()
	 */
    @Override
	public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#setSessionFactory(org.hibernate.SessionFactory)
	 */
    @Override
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#getEntityClass()
	 */
    @Override
	public Class<T> getEntityClass() {
        return entityClass;
    }

    /* (non-Javadoc)
	 * @see com.luv2code.springdemo.dao.GenericDaoInterface#setEntityClass(java.lang.Class)
	 */
    @Override
	public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    
    
    private Long getPrimaryKey(T entity) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String primaryKeyFieldName = getPrimaryKeyFieldName();
        Long pk = null;
        if (primaryKeyFieldName != null) {
            if (BeanUtils.getSimpleProperty(entity, primaryKeyFieldName) != null) {
                pk = Long.parseLong(BeanUtils.getSimpleProperty(entity, primaryKeyFieldName));
            }
        }

        return pk;
    }
     
    private String getPrimaryKeyFieldName() {
        return sessionFactory.getClassMetadata(entityClass).getIdentifierPropertyName();
    }

}
