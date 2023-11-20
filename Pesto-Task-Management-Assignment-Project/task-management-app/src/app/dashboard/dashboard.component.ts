import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskManagementService } from '../Services/task-management.service';
import { Task } from '../models/task';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  customerEmailId: any;
  tasks!: Task[];
  filteredTasks: Task[] = [];
  selectedStatus: string = 'all';

  constructor(private route: ActivatedRoute, private taskService: TaskManagementService) { }

  ngOnInit(): void {
    this.customerEmailId = localStorage.getItem('customerEmailId');
    console.log(this.customerEmailId);
    this.taskService.getTasksByCustomerEmailId(this.customerEmailId).subscribe(
      (result: Task[]) => {
        this.tasks = result;
        this.filteredTasks = [...this.tasks]; // Initialize filteredTasks with all tasks initially
        this.updateFilter(); // Apply the initial filter
      },
      (error: any) => {
        console.error('Error fetching tasks:', error);
      }
    );
  }

  deleteTask(taskId: string): void {
    this.taskService.deleteTaskFromCustomer(this.customerEmailId, taskId).subscribe(
      () => {
        this.tasks = this.tasks.filter(task => task.taskId !== taskId);
        this.updateFilter(); // Update filteredTasks after deleting a task
        console.log('Task deleted successfully.');
      },
      (error: any) => {
        console.error('Error deleting task:', error);
      }
    );
  }

  updateFilter(): void {
    if (this.selectedStatus === 'all') {
      this.filteredTasks = [...this.tasks];
    } else {
      this.filteredTasks = this.tasks.filter(task => task.status === this.selectedStatus);
    }
  }

}
