import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Task } from '../models/task';

@Injectable({
  providedIn: 'root'
})
export class TaskManagementService {
  private baseURL: string = 'http://localhost:9000/api/v1/user';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Retrieve the JWT token from local storage

    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
  }

  getTasksByCustomerEmailId(customerEmailId: string): any {
    const url = `${this.baseURL}/${customerEmailId}/tasks`;
    return this.http.get<Task[]>(url, { headers: this.getHeaders() })
      .pipe(
        catchError((error: any) => this.handleError('Error fetching tasks:', error))
      );
  }

  addTaskToCustomer(customerEmailId: string, task: Task): any {
    const url = `${this.baseURL}/${customerEmailId}/tasks`;
    return this.http.post<Task>(url, task, { headers: this.getHeaders() })
      .pipe(
        catchError((error: any) => this.handleError('Error adding task:', error))
      );
  }

  getTaskByCustomerIdAndTaskId(customerEmailId: string, taskId: string): any {
    const url = `${this.baseURL}/${customerEmailId}/tasks/${taskId}`;
    return this.http.get<Task>(url, { headers: this.getHeaders() })
      .pipe(
        catchError((error: any) => this.handleError('Error fetching task:', error))
      );
  }

  updateTaskForCustomer(customerEmailId: string, taskId: string, task: Task): any {
    const url = `${this.baseURL}/${customerEmailId}/tasks/${taskId}`;
    return this.http.put<Task>(url, task, { headers: this.getHeaders() })
      .pipe(
        catchError((error: any) => this.handleError('Error updating task:', error))
      );
  }

  deleteTaskFromCustomer(customerEmailId: string, taskId: string): any {
    const url = `${this.baseURL}/${customerEmailId}/tasks/${taskId}`;
    return this.http.delete(url, { headers: this.getHeaders() })
      .pipe(
        catchError((error: any) => this.handleError('Error deleting task:', error))
      );
  }

  // Add other methods as needed

  private handleError<T>(message: string, error: any): Observable<T> {
    console.error(message, error);
    return throwError(message + ' Please try again later.');
  }
}
