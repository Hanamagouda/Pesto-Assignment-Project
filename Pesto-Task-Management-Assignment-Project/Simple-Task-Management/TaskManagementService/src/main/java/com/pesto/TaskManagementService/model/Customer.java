/*
 *  Author : Hanamagouda Goudar
 *  Date : 10/11/2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.TaskManagementService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Customer {
    @Id
    private String id;
    private String customerEmailId;
    private String customerPassword;
    private String customerName;
    private String customerContactNumber;
    private String address;
    private List<Task> tasks = new ArrayList<>();

}
