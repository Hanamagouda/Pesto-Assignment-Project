package com.pesto.TaskManagementService.service;

import com.pesto.TaskManagementService.exception.CustomerAlreadyExistsException;
import com.pesto.TaskManagementService.exception.CustomerNotFoundException;
import com.pesto.TaskManagementService.exception.TaskAlreadyExistsException;
import com.pesto.TaskManagementService.exception.TaskNotFoundException;
import com.pesto.TaskManagementService.model.Customer;
import com.pesto.TaskManagementService.model.Task;
import com.pesto.TaskManagementService.model.TaskStatus;

import java.util.List;

public interface CustomerService {

    Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException;

    Customer updateCustomer(String customerEmailId,Customer customer) throws CustomerNotFoundException;

    Customer getCustomerByEmailId(String customerEmailId) throws CustomerNotFoundException;




    List<Task> getTasksByCustomerEmailIdAndStatus(String customerEmailId, TaskStatus status)
            throws CustomerNotFoundException;

    Task getTaskByCustomerIdAndTaskId(String customerEmailId, String taskId)
            throws CustomerNotFoundException, TaskNotFoundException;

    List<Task> getTasksByCustomerEmailId(String customerEmailId) throws CustomerNotFoundException;

   Customer addTaskToCustomer(String customerEmailId, Task task) throws
            CustomerNotFoundException, TaskAlreadyExistsException;

    List<Task> deleteTaskFromCustomer(String customerEmailId, String taskId) throws
            CustomerNotFoundException, TaskNotFoundException;

    Customer updateTaskForCustomer(String customerEmailId, String taskId, Task task)
            throws CustomerNotFoundException, TaskNotFoundException;
}