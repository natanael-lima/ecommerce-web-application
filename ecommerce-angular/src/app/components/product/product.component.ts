import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment.prod';
import { Router } from '@angular/router';
import { SearchService } from '../../services/search.service';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{

  baseUrl = environment.baseUrl;
  products: any[] = [];
  searchName: string = '';
  router: any;

  constructor(private productService: ProductService, private searchService: SearchService,private route: Router) {}

  ngOnInit(): void {
    // Llamar al método con un nombre de ejemplo
    this.getAllProducts();
    
    this.searchService.getSearchTerm().subscribe((searchTerm: string) => {
      if (searchTerm.trim()) {
        this.searchProducts(searchTerm.trim());
      } else {
        this.getAllProducts();
      }
    });

  }
  getAllProducts(): void {
    this.productService.getAllProducts().subscribe(
      (data: Product[]) => {
        this.products = data;
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  searchProducts(name: string): void {
    this.productService.getAllbyName(name).subscribe(
      (data: Product[]) => {
        this.products = data;
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  navigateToProductDetail(productId: number): void {
    this.route.navigate(['/product/product-detail', productId]);
  }
}
