import { AfterViewInit, Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { CategoryService } from '../../services/category.service';
import { UserService } from '../../services/user.service';
import { FormBuilder, FormsModule } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import { ProductRequest } from '../../interfaces/productRequest';
import { HttpClientModule } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import { Imagen } from '../../models/imagen';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, HttpClientModule,CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  
  baseUrl = environment.baseUrl;
  productsHighlights: ProductRequest[] = [];
  image:Imagen;

  constructor(private productService:ProductService,private categoryService:CategoryService,private userService:UserService, private formBuilder:FormBuilder, private loginService:LoginService,private router:Router ){
    this.image = new Imagen();
  }

  ngOnInit(): void {
    this.getAllProductbyHighlight();
   
  }

 addProductCart(producto: any) {
   
  }

  getAllProductbyHighlight(): void {
    this.productService.getAllProductsHighlight().subscribe(
      (data: ProductRequest[]) => {
        this.productsHighlights = data;
      },
      (error) => {
        console.error('Error fetching categories:', error);
      }
    );
  }   

}
