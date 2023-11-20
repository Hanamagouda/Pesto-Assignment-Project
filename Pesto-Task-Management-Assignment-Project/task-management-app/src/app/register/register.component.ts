import { Component, OnInit } from '@angular/core';
import { Customer } from '../models/customer';
import { FormGroup, FormBuilder, FormControl, Validators, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../Services/register.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: Customer = {
    id: '',
    customerEmailId: '',
    customerPassword: '',
    customerName: '',
    customerContactNumber: '',
    address: '',
    tasks: []
  };
  registration!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private registrationService: RegisterService,
    private myRouter: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.initializeRegistrationForm();
  }

  initializeRegistrationForm(): void {
    this.registration = this.formBuilder.group({
      customerName: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z]{4,20}$/)]),
      customerEmailId: new FormControl('', [Validators.required, Validators.pattern(/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i)]),
      customerPassword: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]),
      confirmPassword: new FormControl('', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]),
      customerContactNumber: new FormControl('', [Validators.required, Validators.minLength(10), Validators.pattern(/^[789]\d{9,9}$/)]),
      address: new FormControl("", [Validators.required, Validators.minLength(10), Validators.maxLength(30)])
    }, { validator: [this.passwordValidator] });

    this.registration.patchValue({ typeOfUser: 'customer' });
  }

  get customerName() { return this.registration.get("customerName"); }
  get customerEmailId() { return this.registration.get("customerEmailId"); }
  get customerPassword() { return this.registration.get("customerPassword"); }
  get confirmPassword() { return this.registration.get("confirmPassword"); }
  get customerContactNumber() { return this.registration.get("customerContactNumber"); }
  get address() { return this.registration.get("address"); }

  passwordValidator(control: AbstractControl) {
    const password = control.get('customerPassword')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    if (!password || !confirmPassword) {
      return null;
    }

    return password !== confirmPassword ? { passwordShouldMatch: false } : null;
  }

  onSubmit() {
    this.user = this.registration.value;


    this.registrationService.registration(this.user).subscribe({
      next: (data) => {
        this.snackBar.open('Registration Successful', 'Close', { duration: 3000 });
        // Uncomment and customize the navigation logic if needed
        // this.myRouter.navigate(['home']);
        this.myRouter.navigateByUrl('/login')
      },
      error: (error) => {
        this.snackBar.open('Registration Failed', 'Close', { duration: 3000 });

      }
    });
  }
}
