import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { LoginService } from '../Services/login.service';
import {  Router } from '@angular/router';
import { Customer } from '../models/customer';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy{

  loginForm!: FormGroup;
  private loggedInSubscription!: Subscription;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      customerEmailId: ['', [Validators.required, Validators.email, Validators.pattern(/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i)]],
      customerPassword: ['', [Validators.required,Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]]
    });

    this.loggedInSubscription = this.loginService.loggedIn$.subscribe(loggedIn => {
   
    });
  }

  get customerEmailId() { return this.loginForm.get("customerEmailId"); }
  get customerPassword() { return this.loginForm.get("customerPassword"); }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const loginData: Customer = this.loginForm.value;
      this.loginService.login(loginData).subscribe(
        response => {
       
          console.log('Login successful:', response);
          this.router.navigateByUrl('dashboard');
        },
        error => {
         
          alert('Login failed:');
          console.error('Login failed:', error);
        }
      );
    }
  }

  ngOnDestroy(): void {
    
    if (this.loggedInSubscription) {
      this.loggedInSubscription.unsubscribe();
    }
  }


}
