import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { environment } from '../../../environments/environment.prod';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {

  baseUrl = environment.baseUrl;
  productId!: number;
  product!:Product;

  constructor(private route: ActivatedRoute, private productService: ProductService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.productId = params['id'];
      this.loadProductDetails();
    });
  }

  loadProductDetails(): void {
    this.productService.getProductById(this.productId).subscribe(
      (data: any) => {
        this.product = data;
      },
      (error) => {
        console.error('Error fetching product details:', error);
      }
    );
  }
}
