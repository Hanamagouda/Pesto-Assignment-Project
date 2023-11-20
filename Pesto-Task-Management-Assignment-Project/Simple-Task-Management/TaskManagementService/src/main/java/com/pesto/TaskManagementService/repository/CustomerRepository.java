package com.pesto.TaskManagementService.repository;

import com.pesto.TaskManagementService.model.Customer;
import com.pesto.TaskManagementService.model.Task;
import com.pesto.TaskManagementService.model.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByCustomerEmailId(String customerEmailId);


}
