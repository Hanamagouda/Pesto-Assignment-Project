package com.pesto.UserAuthentication.security;

import com.pesto.UserAuthentication.model.Customer;

import java.util.Map;

public interface JwtSecurityTokenGenerator {
    Map<String,String> generateToken(Customer customer);
}
