import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate, CanActivateChild {

  constructor(private authService: AuthService) { }

  canActivate() {
    return this.authService.sudahLogin();
}

canActivateChild() {
    return this.authService.sudahLogin();
}
}
