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

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  searchName: string = '';
  categories: CategoryRequest[] = []; // Lista de categorias

  constructor(private categoryService:CategoryService, private searchService: SearchService, private router: Router) {}

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

}
