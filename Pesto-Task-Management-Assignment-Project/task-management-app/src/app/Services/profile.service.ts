import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Customer } from '../models/customer';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private baseURL: string = 'http://localhost:9000/api/v1/user';

  constructor(private http: HttpClient) {}

  getProfile(customerEmailId: string): Observable<Customer> {
    const url = `${this.baseURL}/${customerEmailId}`;
    const headers = this.getHeaders();

    return this.http.get<Customer>(url, { headers })
      .pipe(
        catchError((error: any) => this.handleError<Customer>('Error fetching profile:', error))
      );
  }

  updateProfile(customerEmailId: string, updatedProfile: Customer): Observable<Customer> {
    const url = `${this.baseURL}/update/${customerEmailId}`;
    const headers = this.getHeaders();

    return this.http.put<Customer>(url, updatedProfile, { headers })
      .pipe(
        catchError((error: any) => this.handleError<Customer>('Error updating profile:', error))
      );
  }

  private getHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
  }

  private handleError<T>(message: string, error: any): Observable<T> {
    console.error(message, error);
    return throwError(message + ' Please try again later.');
  }

  private getToken(): string | null {
    // Implement your logic to retrieve the JWT token from local storage or any other source
    return localStorage.getItem('token');
  }
}
