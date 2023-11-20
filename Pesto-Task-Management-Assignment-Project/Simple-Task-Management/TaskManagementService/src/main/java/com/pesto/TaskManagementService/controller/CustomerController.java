/*
 *  Author : Hanamagouda Goudar
 *  Date : 10/11/2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.TaskManagementService.controller;

import com.pesto.TaskManagementService.exception.CustomerAlreadyExistsException;
import com.pesto.TaskManagementService.exception.CustomerNotFoundException;
import com.pesto.TaskManagementService.exception.TaskAlreadyExistsException;
import com.pesto.TaskManagementService.exception.TaskNotFoundException;
import com.pesto.TaskManagementService.model.Customer;
import com.pesto.TaskManagementService.model.Task;
import com.pesto.TaskManagementService.model.TaskStatus;
import com.pesto.TaskManagementService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        try {
            Customer registeredCustomer = customerService.registerCustomer(customer);
            return ResponseEntity.ok(registeredCustomer);
        } catch (CustomerAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("user/update/{customerEmailId}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable String customerEmailId,
            @RequestBody Customer updatedCustomer) {
        try {
            Customer updatedCustomerResult = customerService.updateCustomer(customerEmailId, updatedCustomer);
            return ResponseEntity.ok(updatedCustomerResult);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{emailId}")
    public ResponseEntity<Customer> getCustomerByEmailId(@PathVariable String emailId) {
        try {
            Customer customer = customerService.getCustomerByEmailId(emailId);
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{emailId}/tasks")
    public ResponseEntity<List<Task>> getTasksByCustomerEmailId(@PathVariable String emailId) {
        try {
            List<Task> tasks = customerService.getTasksByCustomerEmailId(emailId);
            return ResponseEntity.ok(tasks);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/user/{customerEmailId}/tasks")
    public ResponseEntity<?> addTaskToCustomer(
            @PathVariable String customerEmailId, @RequestBody Task task) {
        try {

            Customer customer = customerService.addTaskToCustomer(customerEmailId, task);


            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException | TaskAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{customerEmailId}/tasks/{taskId}")
    public ResponseEntity<?> getTaskByCustomerIdAndTaskId(
            @PathVariable String customerEmailId, @PathVariable String taskId) {
        try {
            Task task = customerService.getTaskByCustomerIdAndTaskId(customerEmailId, taskId);
            return ResponseEntity.ok(task);
        } catch (CustomerNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{customerEmailId}/tasks/status/{status}")
    public ResponseEntity<List<Task>> getTasksByCustomerEmailIdAndStatus(
            @PathVariable String customerEmailId, @PathVariable TaskStatus status) {
        try {
            List<Task> tasks = customerService.getTasksByCustomerEmailIdAndStatus(customerEmailId, status);
            return ResponseEntity.ok(tasks);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/user/{customerEmailId}/tasks/{taskId}")
    public ResponseEntity<Customer> updateTaskForCustomer(
            @PathVariable String customerEmailId, @PathVariable String taskId, @RequestBody Task task) {
        try {
            Customer updatedCustomer = customerService.updateTaskForCustomer(customerEmailId, taskId, task);
            return ResponseEntity.ok(updatedCustomer);
        } catch (CustomerNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{emailId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTaskFromCustomer(
            @PathVariable String emailId, @PathVariable String taskId) {
        try {
            customerService.deleteTaskFromCustomer(emailId, taskId);
            return ResponseEntity.noContent().build();
        } catch (CustomerNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
