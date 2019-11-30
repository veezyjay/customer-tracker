package com.tracker.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tracker.entity.Customer;

@Repository
public class CusomerDAOImpl implements CustomerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Customer> query = currentSession.createQuery("from Customer order by lastName", Customer.class);
		List<Customer> customers = query.getResultList();
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession.get(Customer.class, theId);
	}

	@Override
	public void deleteCustomer(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		// METHOD ONE
//		Query query = currentSession.createQuery("delete from Customer where id=:customerId");
//		query.setParameter("customerId", theId);
//		query.executeUpdate();
		
//		METHOD TWO
		Customer theCustomer = currentSession.get(Customer.class, theId);
		currentSession.delete(theCustomer);
	}

	@Override
	public List<Customer> searchCustomer(String theSearchName) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Customer> theQuery = null;
	        
        // only search by name if theSearchName is not empty
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        } else {
            // theSearchName is empty ... so just get all customers
            theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);            
        }
        // execute query and return result
        return theQuery.getResultList();
	}

}
