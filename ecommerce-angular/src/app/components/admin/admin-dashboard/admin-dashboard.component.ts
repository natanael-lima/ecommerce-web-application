import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { LoginService } from '../../../services/login.service';
import { Router } from '@angular/router';
import { User } from '../../../interfaces/user';
import { CategoryService } from '../../../services/category.service';
import { CategoryRequest } from '../../../interfaces/categoryRequest';
import { Category } from '../../../models/category';
import { catchError, tap, throwError } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [FormsModule, HttpClientModule,CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit{
  currentSection: string = 'dashboard'; // Sección actual
  currentUser: User ={
    id:0,
    name:'',
    lastname:'',
    username:'',
    role:''
  };

  isLoggedIn: boolean = false;
  categories: CategoryRequest[] = [];
  categoria: Category = new Category('', '', new Date());

  // Propiedades para el formulario de edición
  editingCategory: CategoryRequest ={
      id:0,
      name:'',
      description:'',
      timestamp: new Date
    };

  constructor(private categoryService:CategoryService,private userService:UserService, private formBuilder:FormBuilder, private loginService:LoginService,private router:Router ){
    
}

ngOnInit(): void {
    this.getAllCategory();

}
onSubmit() {
  console.log('Datos a guardar:', this.categoria);
  this.categoryService.createCategory(this.categoria).pipe(
    tap(response => {
      if (response) {
        console.log("Registro exitoso",response.message);
        this.getAllCategory();
      } else {
        console.error('Error al registrar la categoria:', response);
        // Aquí puedes manejar otros tipos de errores según el caso
      }
    }),
    catchError(error => {
      console.error('Error al registrar la categoria:', error);
      return throwError(error);
    })
  ).subscribe();
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

updateCategory() {
  if (this.editingCategory.id === null) {
    console.error('No hay categoría seleccionada para editar');
    return;
  }
  this.categoryService.updateCategory(this.editingCategory)
    .subscribe(updatedCategory => {
      console.log('Categoría actualizada:', updatedCategory);
      this.getAllCategory()
      // Cerrar el modal programáticamente
  });
}

deleteCategory(id:number){
  this.categoryService.deleteCategory(id).subscribe(
    (data: Category) => {
      console.log('Categoría eliminada exitosamente:', data);
      this.getAllCategory();
    },
    (error) => {
      console.error('Error al eliminar la categoría:', error);
    }
  );
}

// Función para cambiar la sección actual
changeSection(section: string): void {
  this.currentSection = section;
}
onLogout(): void {
// Llamar al método logout() del servicio LoginService
this.loginService.logoutUser();
this.router.navigateByUrl('/home');
}

loadCategory(categoryId: number): void {
    this.categoryService.getCategoryById(categoryId).subscribe(
    (data: any) => {
        this.editingCategory = data;
      },
      (error) => {
        console.error('Error fetching product details:', error);
      }
    );
  }



}
