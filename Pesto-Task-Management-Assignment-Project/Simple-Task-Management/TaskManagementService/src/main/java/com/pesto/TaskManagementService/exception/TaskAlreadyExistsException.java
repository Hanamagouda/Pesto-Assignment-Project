/*
 *  Author : Hanamagouda Goudar
 *  Date : 10/11/2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.TaskManagementService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Task Already Exists")
public class TaskAlreadyExistsException extends Exception {
    public TaskAlreadyExistsException() {
    }

    public TaskAlreadyExistsException(String message) {
        super(message);
    }
}
