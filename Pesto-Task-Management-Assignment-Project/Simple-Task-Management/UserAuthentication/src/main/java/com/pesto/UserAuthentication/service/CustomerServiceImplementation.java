/*
 *  Author : Hanamagouda Goudar
 *  Date : 10/11/2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.UserAuthentication.service;

import com.pesto.UserAuthentication.exception.CustomerAlreadyExistsException;
import com.pesto.UserAuthentication.exception.CustomerNotFoundException;
import com.pesto.UserAuthentication.model.Customer;
import com.pesto.UserAuthentication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;


    @Autowired
    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }


    @Override
    public Customer registerUser(Customer customer) throws CustomerAlreadyExistsException {
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getCustomerEmailId());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with email " + customer.getCustomerEmailId() + " already exists");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer loginUser(Customer customer) throws CustomerNotFoundException {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(customer.getCustomerEmailId());

        if (existingCustomerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer with email " + customer.getCustomerEmailId() + " not found");
        }

        Customer existingCustomer = existingCustomerOptional.get();
        return existingCustomer;
    }
}