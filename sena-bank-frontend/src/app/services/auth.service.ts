import { Injectable, computed, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { AuthRequest, AuthResponse } from '../models/bank.models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly tokenKey = 'sena_bank_token';
  private readonly rolesSignal = signal<string[]>(this.readRolesFromToken(localStorage.getItem(this.tokenKey)));

  readonly roles = computed(() => this.rolesSignal());

  constructor(private http: HttpClient, private router: Router) {}

  login(request: AuthRequest) {
    return this.http.post<AuthResponse>(`${environment.apiBaseUrl}/authenticate`, request);
  }

  setSession(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.rolesSignal.set(this.readRolesFromToken(token));
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  hasRole(role: 'ROLE_CLERK' | 'ROLE_MGR'): boolean {
    return this.roles().includes(role);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.rolesSignal.set([]);
    this.router.navigate(['/']);
  }

  private readRolesFromToken(token: string | null): string[] {
    if (!token) return [];
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return Array.isArray(payload.roles) ? payload.roles : [];
    } catch {
      return [];
    }
  }
}
