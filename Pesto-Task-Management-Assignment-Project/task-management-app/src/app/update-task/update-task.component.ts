import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskManagementService } from '../Services/task-management.service';
import { Task } from '../models/task';

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.css']
})
export class UpdateTaskComponent implements OnInit{
  customerEmailId!: string;
  taskId!: string;
  task!: Task;
  updateTaskForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private taskService: TaskManagementService,
    private fb: FormBuilder
  ) {
    this.updateTaskForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      status: ['', Validators.required],
      
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.customerEmailId = params['customerEmailId'];
      this.taskId = params['taskId'];

      this.taskService.getTaskByCustomerIdAndTaskId(this.customerEmailId, this.taskId).subscribe(
        (result: Task) => {
          this.task = result;
          this.updateTaskForm.patchValue({
            title: result.title,
            description: result.description,
            status: result.status,
       
          });
        },
        (error: any) => {
          console.error('Error fetching task:', error);
        }
      );
    });
  }

  updateTask(): void {
    if (this.updateTaskForm.valid) {
      this.taskService.updateTaskForCustomer(this.customerEmailId, this.taskId, this.updateTaskForm.value).subscribe(
        (result: any) => {
          console.log('Task updated successfully:', result);
          this.router.navigate(['/view-task',this.customerEmailId,this.taskId]);
        },
        (error: any) => {
          console.error('Error updating task:', error);
        }
      );
    } else {
      console.error('Form is invalid. Please fill in the required fields.');
    }
  }
}
