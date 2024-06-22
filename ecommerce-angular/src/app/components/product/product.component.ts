import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment.prod';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchService } from '../../services/search.service';
import { CategoryService } from '../../services/category.service';
import { CategoryRequest } from '../../interfaces/categoryRequest';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{

  baseUrl = environment.baseUrl;
  products: Product[] = [];
  searchName: string = '';

  categories: CategoryRequest[] = []; // Lista de categorias

  price1!:number;
  price2!:number;
  
  constructor(private categoryService:CategoryService,private productService: ProductService, private searchService: SearchService,private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {

  this.getAllCategory();
  //Listar productos por categoria desde navegador
  this.route.queryParams.subscribe(params => {
    const category = params['category'];
    console.log("devuelto:",category);
    if (category) {
      console.log("si:",category);
      this.loadProductsByCategory(category);
    } else {
      console.log("no:",category);
      this.getAllProducts();
      
    }
  });

    this.searchService.getSearchTerm().subscribe((searchTerm: string) => {
      if (searchTerm.trim()) {
        this.searchProducts(searchTerm.trim());
      } else {
        //this.getAllProducts();
      }
    });

  }

  loadProductsByCategory(category: string) {
    this.productService.getAllProductsByCategory(category).subscribe(
      (data: Product[]) => {
        this.products = data;
        console.log("Productos de load",this.products);
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
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
  getAllCategory(): void {
    this.categoryService.getAllCategorys().subscribe(
      (data: CategoryRequest[]) => {
        this.categories = data;
      },
      (error) => {
        console.error('Error fetching categories:', error);
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
  searchProductsByCategory(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const selectedValue = selectElement.value;

    if (selectedValue === '') {
      // Lógica para obtener todos los productos cuando se selecciona "Todos"
      this.getAllProducts();
    } else {
      // Lógica para obtener productos por categoría
      this.productService.getAllProductsByCategory(selectedValue).subscribe(
        (data: Product[]) => {
          this.products = data;
        },
        (error) => {
          console.error('Error fetching products:', error);
        }
      );
    }
  }

  searchProductsByPrice(): void {

    if (this.price1===null && this.price2===null) {
      // Lógica para obtener todos los productos cuando se selecciona "Todos"
      this.getAllProducts();
    } else {
      // Lógica para obtener productos por categoría
      this.productService.getAllByPrices(this.price1,this.price2).subscribe(
        (data: Product[]) => {
          this.products = data;
        },
        (error) => {
          console.error('Error fetching products:', error);
        }
      );
    }
  }


  navigateToProductDetail(productId: number): void {
    this.router.navigate(['/product/product-detail', productId]);
  }
}
