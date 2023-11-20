package com.pesto.TaskManagementService.proxy;

import com.pesto.TaskManagementService.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-authentication-service", url = "http://localhost:8080")
public interface CustomerProxy {
    @PostMapping("/api/v2/register")
    public ResponseEntity<?> saveCustomerToAuthentication(@RequestBody Customer customer);
}
