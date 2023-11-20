import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Customer } from '../models/customer';
import { ProfileService } from '../Services/profile.service';

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit {
  customerEmailId!: any;
  customer: Customer = {
    id: '',
    customerEmailId: '',
    customerPassword: '',
    customerName: '',
    customerContactNumber: '',
    address: '',
    tasks: []
  };
  loading = true; 

  constructor(private route: ActivatedRoute, private viewProfileService: ProfileService) { }

  ngOnInit(): void {
    
 
      this.customerEmailId = localStorage.getItem('customerEmailId')

     
      this.viewProfileService.getProfile(this.customerEmailId).subscribe(
        (result) => {
          this.customer = result;
        },
        (error) => {
          console.error('Error fetching profile:', error);
       
        },
        () => {
          this.loading = false; 
        }
      );
    };
  

}
