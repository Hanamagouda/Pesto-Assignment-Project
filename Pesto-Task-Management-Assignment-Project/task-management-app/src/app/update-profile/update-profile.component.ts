import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../models/customer';
import { ProfileService } from '../Services/profile.service';


@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {
  customerEmailId!: any;
  updatedProfileForm: FormGroup;
  updatedProfile: Customer = {
    id: '',
    customerEmailId: '',
    customerPassword: '',
    customerName: '',
    customerContactNumber: '',
    address: '',
    tasks: []
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private profileService: ProfileService,
    private fb: FormBuilder
  ) {
    this.updatedProfileForm = this.fb.group({
      customerName: ['', Validators.required],
      customerContactNumber: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[789]\d{9,9}$/)]],
      address: ['', Validators.required],
    });
  }

  ngOnInit(): void {
   
      this.customerEmailId =localStorage.getItem('customerEmailId');

      this.profileService.getProfile(this.customerEmailId).subscribe(
        (result: Customer) => {
          this.updatedProfile = result;
          this.updatedProfileForm.patchValue({
            customerName: result.customerName,
            customerContactNumber: result.customerContactNumber,
            address: result.address,
          });
        },
        (error: any) => {
          console.error('Error fetching profile:', error);
        }
      );
    }
  

  updateProfile(): void {
    if (this.updatedProfileForm.valid) {
      this.updatedProfile.customerName = this.updatedProfileForm.value.customerName;
      this.updatedProfile.customerContactNumber = this.updatedProfileForm.value.customerContactNumber;
      this.updatedProfile.address = this.updatedProfileForm.value.address;

      this.profileService.updateProfile(this.customerEmailId, this.updatedProfile).subscribe(
        (result: any) => {
          console.log('Profile updated successfully:', result);
          this.router.navigateByUrl('/view-profile');
        },
        (error: any) => {
          console.error('Error updating profile:', error);
        }
      );
    } else {
      console.error('Form is invalid. Please fill in the required fields.');
    }
  }
}
