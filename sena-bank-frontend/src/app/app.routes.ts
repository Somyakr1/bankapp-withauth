import { Routes } from '@angular/router';
import { LoginComponent } from './components/login.component';
import { ClerkDashboardComponent } from './components/clerk-dashboard.component';
import { ManagerDashboardComponent } from './components/manager-dashboard.component';
import { roleGuard } from './guards/role.guard';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'clerk', component: ClerkDashboardComponent, canActivate: [roleGuard('ROLE_CLERK')] },
  { path: 'manager', component: ManagerDashboardComponent, canActivate: [roleGuard('ROLE_MGR')] },
  { path: '**', redirectTo: '' }
];
