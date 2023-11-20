import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, map, throwError } from 'rxjs';
import { Customer } from '../models/customer';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loggedInSubject = new BehaviorSubject<boolean>(false);
  loggedIn$: Observable<boolean> = this.loggedInSubject.asObservable();
  applicationUrl = "http://localhost:9000/api/v2/login";

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
  constructor(private http: HttpClient, private myRouter: Router) {}

  login(login: Customer): Observable<any> {
    return this.http.post<any>(this.applicationUrl, login)
      .pipe(
        map((data: any) => {
          console.log(data);
  
          if (data && data.token && data.customer) {
            localStorage.setItem('token', data.token);
            localStorage.setItem('customerEmailId', data.customer.customerEmailId);
            this.loggedInSubject.next(true);
            console.log('Login Service', this.loggedInSubject);
            alert('LogIn Successful');
          }
  
          return data;  // Modify this line based on the structure of your backend response
        }),
        catchError(error => {
          console.error('Login failed:', error);
          alert('Username and Password do not match');
          return throwError(error);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('customerEmailId');
    this.loggedInSubject.next(false);
    this.myRouter.navigateByUrl('/dashboard')
  }
}
