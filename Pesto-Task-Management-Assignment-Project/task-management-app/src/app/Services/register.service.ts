import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from '../models/customer';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private URL: string = 'http://localhost:9000/api/v1/register';

  constructor(private http: HttpClient) {}

  registration(customer: Customer): Observable<Customer> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.http.post<Customer>(this.URL, customer, httpOptions).pipe(
      catchError((error) => {
        console.error('Registration failed:', error);
        // Handle the error, e.g., show an error message to the user
        return throwError('Registration failed. Please try again later.');
      })
    );
  }
}
