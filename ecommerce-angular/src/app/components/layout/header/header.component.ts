import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { SearchService } from '../../../services/search.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  searchName: string = '';

  constructor(private searchService: SearchService, private router: Router) {}

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  onSearch(): void {
    console.log('Searching for:', this.searchName);
    this.searchService.setSearchTerm(this.searchName.trim());
    this.router.navigate(['/product']); // Redirige al componente de productos
  }
}
