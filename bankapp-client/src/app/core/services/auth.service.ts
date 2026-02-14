import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { AuthRequest, AuthResponse } from '../../models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(
    private http: HttpClient,
    private cookie: CookieService,
    private router: Router
  ) {}

  login(request: AuthRequest) {
    return this.http.post<AuthResponse>(`${environment.baseUrl}/login`, request);
  }

  saveToken(token: string) {
    this.cookie.set('jwt', token);
  }

  getToken() {
    return this.cookie.get('jwt');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout() {
    this.cookie.delete('jwt');
    this.router.navigate(['/login']);
  }
}
