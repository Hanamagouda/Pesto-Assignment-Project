/*
 *  Author : Hanamagouda Goudar
 *  Date : 10/11/2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.TaskManagementService.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.concurrent.atomic.AtomicLong;

@Document(collection = "tasks")
@Data
@EqualsAndHashCode
public class Task {
    private static final AtomicLong idGenerator = new AtomicLong(1);
    @Id
    private String taskId;
    private String title;
    private String description;
    private TaskStatus status;

    // Add a constructor to initialize taskId with a sequential ID
    public Task() {
        this.taskId = String.valueOf(idGenerator.getAndIncrement());

    }

}
