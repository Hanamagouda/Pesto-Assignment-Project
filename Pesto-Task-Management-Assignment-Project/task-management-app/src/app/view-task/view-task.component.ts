import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskManagementService } from '../Services/task-management.service';
import { Task } from '../models/task';

@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.css']
})
export class ViewTaskComponent implements OnInit{
  customerEmailId: any;
  taskId!: string;
  task!: Task;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskManagementService,
    private myRouter: Router
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.customerEmailId = localStorage.getItem('customerEmailId')
      this.taskId = params['taskId'];


      this.taskService.getTaskByCustomerIdAndTaskId(this.customerEmailId, this.taskId).subscribe(
        (result: Task) => {
          this.task = result;
        },
        (error: any) => {
          console.error('Error fetching task:', error);
          
        }
      );
    });
    
  }
  deleteTask(): void {
    this.taskService.deleteTaskFromCustomer(this.customerEmailId, this.taskId).subscribe(
      () => {
        alert('Task deleted successfully.');
        console.log('Task deleted successfully.');
        this.myRouter.navigateByUrl('/dashboard')
        
      },
      (error: any) => {
        alert('Error deleting task:');
        console.error('Error deleting task:', error);
       
      }
    );
  }
  
}
