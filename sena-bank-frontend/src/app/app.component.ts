import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
  <header style="background:#0f172a;color:#fff;padding:12px 20px;display:flex;justify-content:space-between;align-items:center;">
    <div style="font-size:22px;font-weight:700;">Sena Bank</div>
    <button *ngIf="auth.getToken()" class="secondary" (click)="auth.logout()">Logout</button>
  </header>
  <router-outlet></router-outlet>
  `
})
export class AppComponent {
  constructor(public auth: AuthService) {}
}
