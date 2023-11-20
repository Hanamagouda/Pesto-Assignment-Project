/*
 *  Author : Hanamagouda Goudar
 *  Date : 10/11/2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.UserAuthentication.controller;

import com.pesto.UserAuthentication.exception.CustomerAlreadyExistsException;
import com.pesto.UserAuthentication.exception.CustomerNotFoundException;
import com.pesto.UserAuthentication.model.Customer;
import com.pesto.UserAuthentication.security.JwtSecurityTokenGenerator;
import com.pesto.UserAuthentication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class CustomerController {

    private final CustomerService customerService;

    private final JwtSecurityTokenGenerator jwtTokenGenerator;

    @Autowired
    public CustomerController(CustomerService customerService, JwtSecurityTokenGenerator jwtTokenGenerator) {
        this.customerService = customerService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Customer customer) {
        try {
            Customer registeredCustomer = customerService.registerUser(customer);
            return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
        } catch (CustomerAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Customer customer) {
        try {
            Customer authenticatedCustomer = customerService.loginUser(customer);

            // Generate JWT token
            Map<String, String> tokenMap = jwtTokenGenerator.generateToken(authenticatedCustomer);

            // Return the token along with any other necessary information
            Map<String, Object> response = new HashMap<>();
            response.put("token", tokenMap.get("token"));
            response.put("customer", authenticatedCustomer);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
