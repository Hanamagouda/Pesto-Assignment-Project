/*
 *  Author : Hanamagouda Goudar
 *  Date : 10/11/2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.UserAuthentication.security;

import com.pesto.UserAuthentication.model.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtSecurityTokenGeneratorImpl implements JwtSecurityTokenGenerator {


    @Override
    public Map<String, String> generateToken(Customer customer) {
        Map<String, String> tokenMap = new HashMap<>();
      customer.setCustomerPassword("");
        Map<String,Object> userData = new HashMap<>();
        userData.put("customerEmailId",customer.getCustomerEmailId());

        String jwtTokenString = Jwts.builder().setClaims(userData).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512,"mySecret").compact();
        tokenMap.put("token",jwtTokenString);
        tokenMap.put("message", "Login Successful");
        tokenMap.put("customerEmailId", customer.getCustomerEmailId());
        return tokenMap;
    }
}
