import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const roleGuard = (role: 'ROLE_CLERK' | 'ROLE_MGR'): CanActivateFn => {
  return () => {
    const auth = inject(AuthService);
    const router = inject(Router);
    if (auth.hasRole(role)) {
      return true;
    }
    router.navigate(['/']);
    return false;
  };
};
