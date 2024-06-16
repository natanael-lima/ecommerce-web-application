import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { LoginService } from '../services/login.service';
import { inject } from '@angular/core';

export const authGuardGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
  if (inject(LoginService).isLoggedIn.value) {
    return true; // Si el usuario está autenticado, permitir acceso a la ruta
  } else {
    inject(Router).navigate(['/home']); // Si no está autenticado, redirigir a la página de inicio de sesión
    return false;
  }
};