import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { environment } from '../../environments/environment.prod';
import { UserRequest } from '../interfaces/userRequest';
import { Observable, catchError, throwError } from 'rxjs';
import { User } from '../models/user';
import { PasswordRequest } from '../interfaces/passwordRequest';

@Injectable({
  providedIn: 'root'
})
export class UserService implements OnInit{

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }


// Método para registrar un usuario
createUser(data:User): Observable<User> { 
  return this.http.post<User>(`${environment.urlHost}auth/registration-user`, data).pipe(
  );
 }
// Método para verficar username
checkUsernameExists(username: string): Observable<any> { 
  return this.http.get<any>(`${environment.urlHost}api/user/check-username?username=${username}`).pipe(
  );
 }

// Método para obtener un usuario
 getUserById(id:number):Observable<any>{
  return this.http.get<any>(`${environment.urlHost}api/user/get-user/${id}`).pipe(
    catchError(this.handleError)
  )
}

// Método para obtener los datos del usuario actualmente logueado
 getCurrentUser(): Observable<UserRequest> {
  return this.http.get<UserRequest>(`${environment.urlHost}api/user/current`).pipe(
    catchError(this.handleError)
  );
}


// Método para obtener todos los usuarios excepto el logueado
getAllUsers(): Observable<UserRequest[]> {
  return this.http.get<UserRequest[]>(`${environment.urlHost}api/user/get-all-except-me`);
   
}

// Método para obtener todos los usuarios basico
getAllUsersBasic(): Observable<UserRequest[]> {
  return this.http.get<UserRequest[]>(`${environment.urlHost}api/user/get-all`);
   
}

// Método para eliminar un usuario
deleteUser(id:number): Observable<User> {
  return this.http.delete<User>(`${environment.urlHost}api/user/delete-user/${id}`);
}

// Método para actualizar un usuario
updateUser(data:UserRequest): Observable<UserRequest> {
  return this.http.put<UserRequest>(`${environment.urlHost}api/user/update-user/${data.id}`,data).pipe(
    catchError(this.handleError)
  );
}

// Método para actualizar un usuario sin pasar id
updateUserByQuery(data:UserRequest): Observable<UserRequest> {
  return this.http.put<UserRequest>(`${environment.urlHost}api/user/update-user-query`,data).pipe(
    catchError(this.handleError)
  );
}

// Método para actualizar un password
updatePassword(id:number,  change :PasswordRequest): Observable<PasswordRequest> {
  return this.http.put<PasswordRequest>(`${environment.urlHost}api/user/${id}/change-password`,change).pipe(
    catchError(this.handleError)
  );
}

// Método para contart total
countUsers(): Observable<number> {
  return this.http.get<number>(`${environment.urlHost}api/user/count-user`).pipe(
    catchError(this.handleError)
  );
}

private handleError(error: any): Observable<never> {
  console.error('Ocurrio un error:', error);
  // Puedes manejar el error aquí, por ejemplo, mostrar un mensaje de error
  return throwError('Something bad happened; please try again later.');
}

}
