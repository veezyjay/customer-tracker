package com.tracker.service;

import java.util.List;

import com.tracker.entity.Customer;

public interface CustomerService {
	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);
}
