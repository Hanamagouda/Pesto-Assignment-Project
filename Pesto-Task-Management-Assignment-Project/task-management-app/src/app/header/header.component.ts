import { Component, OnDestroy, OnInit } from '@angular/core';
import { LoginService } from '../Services/login.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy{
  loggedIn: boolean = false;
  private loggedInSubscription!: Subscription;

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
    this.loggedInSubscription = this.loginService.loggedIn$.subscribe(status => {
      this.loggedIn = status;
    });
  }

  ngOnDestroy(): void {
    
    this.loggedInSubscription.unsubscribe();
  }

  logout(): void {
    this.loginService.logout();
  }
}
