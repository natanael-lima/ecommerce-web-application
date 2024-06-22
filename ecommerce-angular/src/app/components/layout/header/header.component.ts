import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { SearchService } from '../../../services/search.service';
import { Router } from '@angular/router';
import { CategoryService } from '../../../services/category.service';
import { CategoryRequest } from '../../../interfaces/categoryRequest';
import { ProductRequest } from '../../../interfaces/productRequest';
interface CartItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
}
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [FormsModule, HttpClientModule,CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  searchName: string = '';
  products: Product[] = [];
  categories: CategoryRequest[] = []; // Lista de categorias
  cartItems: CartItem[] = [
    { id: 1, name: 'Producto 1', price: 100, quantity: 1 },
    { id: 2, name: 'Producto 2', price: 200, quantity: 2 }
  ];
  isCartVisible = false;

  value: number = 1;

 

  constructor(private categoryService:CategoryService,private productService: ProductService, private searchService: SearchService, private router: Router) {}

  ngOnInit(): void {

    this.getAllCategory();
  }

  onSearch(): void {
    console.log('Searching for:', this.searchName);
    this.searchService.setSearchTerm(this.searchName.trim());
    this.router.navigate(['/product']); // Redirige al componente de productos
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

  searchProductsByCategory(category:string): void {
    if (category === '') {
      // Lógica para obtener todos los productos cuando se selecciona "Todos"
       this.router.navigate(['/product']); // Redirige al componente de productos
    } else {
      // Lógica para obtener productos por categoría
      this.router.navigate(['/product'], { queryParams: { category: category } });
    }
  }


  toggleCart() {
    this.isCartVisible = !this.isCartVisible;
  }

  increaseQuantity(index: number) {
    this.cartItems[index].quantity++;
    this.value += 1;
  }
  decrementQuantity(index: number) {
    if (this.value > 1) {
      this.cartItems[index].quantity--;
      this.value += 1;
    }
  }

  removeFromCart(index: number) {
    this.cartItems.splice(index, 1);
  }

  clearCart() {
    this.cartItems = [];
  }

  checkout() {
    alert('Compra realizada!');
    this.clearCart();
    this.toggleCart();
  }


  

}
