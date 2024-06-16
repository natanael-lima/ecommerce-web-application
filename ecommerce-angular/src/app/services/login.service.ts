import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private loggedIn = new BehaviorSubject<boolean>(false);
  isLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) { 
     this.isLoggedIn = new BehaviorSubject<boolean>(sessionStorage.getItem("token") !== null);
  }

  loginUser(credentials: LoginRequest): Observable<any>{
    return this.http.post<any>(`${environment.urlHost}auth/login`, credentials).pipe(
      tap((res :any) => {
        // Verificar si la respuesta tiene un token antes de guardarlo
        if (res.token) {
          // Almacenar el token JWT en el almacenamiento local session storage después de un inicio de sesión exitoso
          sessionStorage.setItem("token", res.token);
          this.isLoggedIn.next(true);
        }
      })
    );
   }

  logoutUser(): void {
    sessionStorage.removeItem('token');
    // En el método de logout
    this.isLoggedIn.next(false);
   }

  get userToken(){
    return sessionStorage.getItem('token');
  }

  checkLoginStatus() {
    const token = sessionStorage.getItem('token');
    this.loggedIn.next(!!token);
  }

}
