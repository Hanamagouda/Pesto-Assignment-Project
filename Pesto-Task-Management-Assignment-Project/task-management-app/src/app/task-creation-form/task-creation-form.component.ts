import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskManagementService } from '../Services/task-management.service';

@Component({
  selector: 'app-task-creation-form',
  templateUrl: './task-creation-form.component.html',
  styleUrls: ['./task-creation-form.component.css']
})
export class TaskCreationFormComponent implements OnInit {
  customerEmailId!: string;
  createTaskForm: FormGroup;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private taskService: TaskManagementService
  ) {
    this.createTaskForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      status: ['', Validators.required],

    });
  }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.customerEmailId = params['customerEmailId'];
    });
  }

  createTask(): void {
    if (this.createTaskForm.valid) {
      this.taskService.addTaskToCustomer(this.customerEmailId, this.createTaskForm.value).subscribe(
        (result: any) => {
          console.log('Task created successfully:', result);

          this.router.navigateByUrl('/dashboard');

        },
        (error: any) => {
          console.error('Error creating task:', error);

        }
      );
    } else {
      console.error('Form is invalid. Please fill in the required fields.');
    }
  }
}
