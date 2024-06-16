import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { CategoryRequest } from '../interfaces/categoryRequest';

import { environment } from '../../environments/environment.prod';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient)  { 

  }
  createCategory(category:Category): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${environment.urlHost}api/category/registration-category`,category,{ headers }).pipe(
    catchError(this.handleError)
    );
  }

  updateCategory(categoryData: CategoryRequest): Observable<any> {
    return this.http.put<any>(`${environment.urlHost}api/category/update-category/${categoryData.id}`, categoryData);
  }

  deleteCategory(id:number): Observable<CategoryRequest> {
    return this.http.delete<CategoryRequest>(`${environment.urlHost}api/category/delete-category/${id}`);
  }

  
  getCategoryById(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${environment.urlHost}api/category/get-category/${id}`);
  }

  getAllCategorys(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.urlHost}api/category/find-all`);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('Ocurrio un error:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError('Something bad happened; please try again later.');
  }

}
