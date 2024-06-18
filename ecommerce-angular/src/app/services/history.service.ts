import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment.prod';
import { HistoryRequest } from '../interfaces/historyRequest';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  constructor(private http: HttpClient) { }

// Método para obtener una historia
 getHistoryById(id:number):Observable<any>{
  return this.http.get<any>(`${environment.urlHost}api/history/get-history/${id}`).pipe(
    catchError(this.handleError)
  )
}

// Método para obtener todos las historias
getAllHistory(): Observable<HistoryRequest[]> {
  return this.http.get<HistoryRequest[]>(`${environment.urlHost}api/history/get-all`);
   
}

private handleError(error: any): Observable<never> {
  console.error('Ocurrio un error:', error);
  // Puedes manejar el error aquí, por ejemplo, mostrar un mensaje de error
  return throwError('Something bad happened; please try again later.');
}
}
