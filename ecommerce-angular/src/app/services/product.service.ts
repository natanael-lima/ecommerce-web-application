import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { environment } from '../../environments/environment.prod';
import { Product } from '../models/product';
import { Observable, catchError, throwError } from 'rxjs';
import { ProductRequest } from '../interfaces/productRequest';

@Injectable({
  providedIn: 'root'
})
export class ProductService implements OnInit {

  constructor(private http: HttpClient)  { 

  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  createProduct(formData:FormData): Observable<any> {
    return this.http.post<any>(`${environment.urlHost}api/product/registration-product`,formData).pipe(
    catchError(this.handleError)
    );
  }

  updateProduct(formData:FormData,id:number): Observable<any> {
    return this.http.put<any>(`${environment.urlHost}api/product/update-product/${id}`, formData);
  }

  deleteProduct(id:number): Observable<ProductRequest> {
    return this.http.delete<ProductRequest>(`${environment.urlHost}api/product/delete-product/${id}`);
  }

  getProductById(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${environment.urlHost}api/product/get-product/${id}`);
  }

  getAllProducts(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.urlHost}api/product/find-all`);
  }

  getAllbyName(name: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${environment.urlHost}api/product/search-products?name=${encodeURIComponent(name)}`);
  }

  getAllByPrices(priceStart: number, priceEnd:number){
    return this.http.get<any>(`${environment.urlHost}api/product/price-range?priceStart=${encodeURIComponent(priceStart)}&priceEnd=${encodeURIComponent(priceEnd)}`);
  }
  getAllProductsHighlight(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.urlHost}api/product/search-highlight`);
  }
  getAllProductsByCategory(name: string): Observable<any[]> {
    return this.http.get<any[]>(`${environment.urlHost}api/product/categoria/${name}`);
  }


  private handleError(error: HttpErrorResponse) {
    console.error('Error completo:', error);
  
    if (error.error instanceof ErrorEvent) {
      // Error del lado del cliente
      console.error('Error del lado del cliente:', error.error.message);
    } else {
      // El backend devolvió un código de respuesta sin éxito.
      console.error(
        `Código de estado del backend ${error.status}, ` +
        `cuerpo: ${error.error}`);
    }
    // Devuelve un observable con un mensaje de error orientado al usuario
    return throwError(
      () => new Error('Algo salió mal; por favor, inténtalo de nuevo más tarde.'));
  }

}
