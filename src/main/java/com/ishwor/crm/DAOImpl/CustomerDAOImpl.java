package com.ishwor.crm.DAOImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ishwor.crm.DAO.CustomerDAO;
import com.ishwor.crm.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<Customer> getCustomer() {
		// get current hibernate Session
		Session currenSession = sessionFactory.getCurrentSession();
		// create Query
		Query<Customer> myQuery = currenSession.createQuery("from customer", Customer.class);
		// execute query and get result

		return myQuery.getResultList();
	}
}
