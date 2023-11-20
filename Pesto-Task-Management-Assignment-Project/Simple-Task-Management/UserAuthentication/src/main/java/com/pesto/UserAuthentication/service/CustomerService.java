package com.pesto.UserAuthentication.service;

import com.pesto.UserAuthentication.exception.CustomerAlreadyExistsException;
import com.pesto.UserAuthentication.exception.CustomerNotFoundException;
import com.pesto.UserAuthentication.model.Customer;

public interface CustomerService {
    public Customer registerUser(Customer customer) throws CustomerAlreadyExistsException;

    public Customer loginUser(Customer customer) throws CustomerNotFoundException;
}
