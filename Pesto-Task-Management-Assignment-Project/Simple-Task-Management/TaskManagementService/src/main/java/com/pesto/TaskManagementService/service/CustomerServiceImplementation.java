/*
 *  Author : Hanamagouda Goudar
 *  Date : 10/11/2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.TaskManagementService.service;

import com.pesto.TaskManagementService.exception.CustomerAlreadyExistsException;
import com.pesto.TaskManagementService.exception.CustomerNotFoundException;
import com.pesto.TaskManagementService.exception.TaskAlreadyExistsException;
import com.pesto.TaskManagementService.exception.TaskNotFoundException;
import com.pesto.TaskManagementService.model.Customer;
import com.pesto.TaskManagementService.model.Task;
import com.pesto.TaskManagementService.model.TaskStatus;
import com.pesto.TaskManagementService.proxy.CustomerProxy;
import com.pesto.TaskManagementService.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerProxy customerProxy;

    @Autowired
    public CustomerServiceImplementation(CustomerRepository customerRepository, CustomerProxy customerProxy) {
        this.customerRepository = customerRepository;
        this.customerProxy = customerProxy;
    }

    @Override
    public Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException {

        if (customerRepository.findByCustomerEmailId(customer.getCustomerEmailId()) != null) {
            throw new CustomerAlreadyExistsException("Customer with email " + customer.getCustomerEmailId() + " already exists.");
        }


        Customer savedCustomer = customerRepository.save(customer);


        ResponseEntity<?> responseEntity = customerProxy.saveCustomerToAuthentication(savedCustomer);



        return savedCustomer;
    }

    public Customer updateCustomer(String customerEmailId, Customer updatedCustomer) throws CustomerNotFoundException {

        Customer existingCustomer = customerRepository.findByCustomerEmailId(customerEmailId);

        if (existingCustomer == null) {
            throw new CustomerNotFoundException("Customer with email address " + customerEmailId + " not found.");
        }


        existingCustomer.setCustomerPassword(updatedCustomer.getCustomerPassword());
        existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
        existingCustomer.setCustomerContactNumber(updatedCustomer.getCustomerContactNumber());
        existingCustomer.setAddress(updatedCustomer.getAddress());


        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer getCustomerByEmailId(String customerEmailId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerEmailId(customerEmailId);
        if (customer != null) {
            return customer;
        } else {
            throw new CustomerNotFoundException("Customer with email address " + customerEmailId + " not found.");
        }
    }


    @Override
    public List<Task> getTasksByCustomerEmailIdAndStatus(String customerEmailId, TaskStatus status) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerEmailId(customerEmailId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with email address " + customerEmailId + " does not exist.");
        }

        List<Task> tasks = customer.getTasks();

        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getStatus().equals(status)) {
                filteredTasks.add(task);
            }
        }

        return filteredTasks;
    }

    @Override
    public Task getTaskByCustomerIdAndTaskId(String customerEmailId, String taskId) throws CustomerNotFoundException, TaskNotFoundException {
        Customer customer = customerRepository.findByCustomerEmailId(customerEmailId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with email address " + customerEmailId + " not found.");
        }

        List<Task> tasks = customer.getTasks();
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                return task;
            }
        }

        throw new TaskNotFoundException("Task with ID " + taskId + " not found for customer with email " + customerEmailId);
    }

    @Override
    public List<Task> getTasksByCustomerEmailId(String customerEmailId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerEmailId(customerEmailId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with email address " + customerEmailId + " not found.");
        }

        return customer.getTasks();
    }

    @Override
    public Customer addTaskToCustomer(String customerEmailId, Task task) throws CustomerNotFoundException, TaskAlreadyExistsException {
        Customer customer = customerRepository.findByCustomerEmailId(customerEmailId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with email address " + customerEmailId + " not found.");
        }

        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        if (customer.getTasks() == null) {
            customer.setTasks(new ArrayList<>());
        }

        List<Task> tasks = customer.getTasks();
        for (Task existingTask : tasks) {
            if (existingTask.getTaskId().equals(task.getTaskId())) {
                throw new TaskAlreadyExistsException("Task with ID " + task.getTaskId() + " already exists for customer with email " + customerEmailId);
            }
        }

        customer.getTasks().add(task);

        customerRepository.save(customer);

        if (!customer.getTasks().contains(task)) {
            throw new TaskAlreadyExistsException("Task could not be added to the customer's task list.");
        }

        return customer;
    }

    @Override
    public List<Task> deleteTaskFromCustomer(String customerEmailId, String taskId) throws CustomerNotFoundException, TaskNotFoundException {
        Customer customer = customerRepository.findByCustomerEmailId(customerEmailId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with email address " + customerEmailId + " not found.");
        }

        List<Task> tasks = customer.getTasks();
        Task taskToDelete = null;
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                taskToDelete = task;
                break;
            }
        }

        if (taskToDelete != null) {
            customer.getTasks().remove(taskToDelete);

            customerRepository.save(customer);

            return customer.getTasks();
        } else {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found for customer with email " + customerEmailId);
        }
    }

    @Override
    public Customer updateTaskForCustomer(String customerEmailId, String taskId, Task updatedTask)
            throws CustomerNotFoundException, TaskNotFoundException {
        Customer customer = customerRepository.findByCustomerEmailId(customerEmailId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with email address " + customerEmailId + " not found.");
        }

        List<Task> tasks = customer.getTasks();
        Task taskToUpdate = null;
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                taskToUpdate = task;
                break;
            }
        }

        if (taskToUpdate != null) {
            taskToUpdate.setTitle(updatedTask.getTitle());
            taskToUpdate.setDescription(updatedTask.getDescription());
            taskToUpdate.setStatus(updatedTask.getStatus());

            customerRepository.save(customer);

            return customer;
        } else {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found for customer with email " + customerEmailId);
        }
    }
}