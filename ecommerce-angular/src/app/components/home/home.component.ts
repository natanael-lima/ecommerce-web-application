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
  @ViewChild('carousel') carousel!: ElementRef;
  
  cards = [
    { title: 'Card title 1', text: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.' },
    { title: 'Card title 2', text: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.' },
    { title: 'Card title 3', text: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.' },
    { title: 'Card title 4', text: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.' },
    { title: 'Card title 5', text: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.' },

    // ... agrega más tarjetas aquí
  ];

  carouselWidth!: number;
  cardWidth!: number;
  scrollPosition = 0;

  constructor(private productService:ProductService,private categoryService:CategoryService,private userService:UserService, private formBuilder:FormBuilder, private loginService:LoginService,private router:Router ){
    this.image = new Imagen();
  }

  ngOnInit(): void {
    this.getAllProductbyHighlight();
   
  }
  ngAfterViewInit(): void {
    this.initCarousel();
  }
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.initCarousel();
  }

  initCarousel(): void {
    if (window.matchMedia('(min-width: 768px)').matches) {
      const carouselInner = this.carousel.nativeElement.querySelector('.carousel-inner-new');
      this.carouselWidth = carouselInner.scrollWidth;
      this.cardWidth = carouselInner.querySelector('.carousel-item').offsetWidth;
    }
  }

  prevSlide(): void {
    if (this.scrollPosition > 0) {
      this.scrollPosition -= this.cardWidth;
      this.carousel.nativeElement.querySelector('.carousel-inner-new').scrollLeft = this.scrollPosition;
    }
  }

  nextSlide(): void {
    if (this.scrollPosition < (this.carouselWidth - (this.cardWidth * 4))) {
      this.scrollPosition += this.cardWidth;
      this.carousel.nativeElement.querySelector('.carousel-inner-new').scrollLeft = this.scrollPosition;
    }
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
