import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UpdateTaskComponent } from './update-task/update-task.component';
import { ViewTaskComponent } from './view-task/view-task.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { TaskCreationFormComponent } from './task-creation-form/task-creation-form.component';
import { ViewProfileComponent } from './view-profile/view-profile.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { HeaderComponent } from './header/header.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path: '', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'task-creation/:customerEmailId', component: TaskCreationFormComponent, canActivate: [AuthGuard] },
  { path: 'view-task/:customerEmailId/:taskId', component: ViewTaskComponent , canActivate: [AuthGuard]},
  { path: 'update-task/:customerEmailId/:taskId', component: UpdateTaskComponent , canActivate: [AuthGuard]},
  { path: 'dashboard', component: DashboardComponent ,  canActivate: [AuthGuard]},
  { path: 'header', component: HeaderComponent },
  { path: 'view-profile', component: ViewProfileComponent, canActivate: [AuthGuard] },
  { path: 'update-profile', component: UpdateProfileComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
