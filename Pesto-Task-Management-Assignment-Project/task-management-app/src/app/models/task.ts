
import { TaskStatus } from './taskStatus'; 
export interface Task {
    taskId: string;
    title: string;
    description: string;
    status: TaskStatus;
  }