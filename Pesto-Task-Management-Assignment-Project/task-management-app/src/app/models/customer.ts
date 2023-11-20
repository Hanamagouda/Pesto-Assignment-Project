import { Task } from "./task";

export interface Customer {
    id: string;
    customerEmailId: string;
    customerPassword: string;
    customerName: string;
    customerContactNumber: string;
    address: string;
    tasks: Task[];
  }