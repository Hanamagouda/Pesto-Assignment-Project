/*
 *  Author : Hanamagouda Goudar
 *  Date :
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.TaskManagementService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "TrackNotFound")
public class TaskNotFoundException extends Exception {
    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public TaskNotFoundException(String message) {
        super(message);
    }
}
