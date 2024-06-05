import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { environment } from '../../environments/environment.prod';
import { Product } from '../models/product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService implements OnInit {

  constructor(private http: HttpClient)  { 

  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  getProductById(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${environment.urlHost}api/product/find/${id}`);
  }

  getAllProducts(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.urlHost}api/product/findAll`);
  }

  getAllbyName(name: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${environment.urlHost}api/product/search-products?name=${encodeURIComponent(name)}`);
  }

  getAllByPrices(priceStart: number, priceEnd:number){
    return this.http.get<any>(`${environment.urlHost}api/product/price-range?priceStart=${encodeURIComponent(priceStart)}&priceEnd=${encodeURIComponent(priceEnd)}`);
  }
}
