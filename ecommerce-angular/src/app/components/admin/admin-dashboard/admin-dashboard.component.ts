import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { LoginService } from '../../../services/login.service';
import { Router } from '@angular/router';
import { UserRequest } from '../../../interfaces/userRequest';
import { CategoryService } from '../../../services/category.service';
import { CategoryRequest } from '../../../interfaces/categoryRequest';
import { Category } from '../../../models/category';
import { catchError, tap, throwError } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import { ProductRequest } from '../../../interfaces/productRequest';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product.service';
import { environment } from '../../../../environments/environment.prod';
import { Imagen } from '../../../models/imagen';
import { User } from '../../../models/user';
import { HistoryRequest } from '../../../interfaces/historyRequest';
import { HistoryService } from '../../../services/history.service';
import { PasswordRequest } from '../../../interfaces/passwordRequest';
import { SearchRequest } from '../../../interfaces/searchRequest';



@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [FormsModule, HttpClientModule,CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit{
  
  baseUrl = environment.baseUrl; // URI base para mostrar image 
  //isLoggedIn: boolean = false; 

  currentSection: string = 'dashboard'; // Sección actual
  // Propiedades para user actual
  currentUser: UserRequest ={
    id:0,
    name:'',
    lastname:'',
    username:'',
    role:'',
    timestamp:new Date()
  };
  // Propiedades para user nuevo usuario
  newUser: User = {name:'',lastname:'',username:'',password:''};

  users: UserRequest[] = []; // Lista de usuarios
  categories: CategoryRequest[] = []; // Lista de categorias
  products: ProductRequest[] = []; // Lista de productos
  productsHighlights: ProductRequest[] = []; // Lista de productos destacados
  histories: HistoryRequest[] = []; // Lista de historia de acciones en panel de control


  categoria: Category = new Category('', '', new Date());
  producto: Product = new Product('', '',0, 0,'', new Date,false, '');

  image:Imagen;
  selectedImageURL: string | ArrayBuffer= '';

  // Propiedades para el formulario de edición categoria
  editingCategory: CategoryRequest ={
      id:0,
      name:'',
      description:'',
      timestamp: new Date
    };
  // Propiedades para el formulario de edición product
  editingProduct: ProductRequest ={
    id: 0,
    name: '',
    description: '',
    price: 0, 
    stock: 0,
    image: '',
    timestamp: new Date,
    highlight: true,
    categoria: ''

  };  
  // Propiedades para el formulario de edición user
  editingUser: UserRequest ={
    id: 0,
    name:'',
    lastname:'',
    username:'',
    role:'',
    timestamp:new Date()
  };

  changeImage = false; // Valida si quiere editar imagen o solo texto en product

 // Propiedades para el formulario de edición password
  password: PasswordRequest ={
    currentPassword:'',
    newPassword:''
  };
  oldPassword!: String;
  newPassword!: String;
  newPasswordRepeat!: String;

  // Propiedades para producto mas buscado
  mostSearched: SearchRequest ={
    id:0,
    query:'',
    searchCount:0
  };

  constructor(private historyService:HistoryService,private productService:ProductService,private categoryService:CategoryService,private userService:UserService, private formBuilder:FormBuilder, private loginService:LoginService,private router:Router ){
    this.image = new Imagen();
}

ngOnInit(): void {
    this.getAllCategory();
    this.getAllProduct();
    this.getAllProductbyHighlight();
    this.getAllUser();
    this.getAllHistory();
    this.getMostSearchProduct();
    this.userService.getCurrentUser().subscribe(
      (data: UserRequest) => {
        this.currentUser = data;
      },
      (error) => {
        console.error('Error fetching user actual:', error);
      }
    );

}
onSubmitCategory() {
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

onSubmitProduct(){
  const formData = new FormData();
  // Agregar los datos del producto como un objeto, no como una cadena JSON
  formData.append('request', new Blob([JSON.stringify({
    name: this.producto.name,
    description: this.producto.description,
    price: this.producto.price,
    stock: this.producto.stock,
    categoryName: this.producto.categoria
  })], {
    type: 'application/json'
  }));

  // Agregar archivo de imagen de perfil
  if (this.image.image) {
     formData.append('file', this.image.image, this.image.image.name);
  }
  //formData.append('request', datosProduct);
  console.log('Archivo:', formData.get('file'));
  console.log('Obejeto:',formData.get('request'))

  this.productService.createProduct(formData).pipe(
    tap(response => {
      console.log("Respuesta del servidor:", response);
      if (response) {
        console.log("Registro exitoso",response.message);
        this.getAllProduct();
      } else {
        console.error('Error al registrar la producto:', response);
      }
    }),
    catchError(error => {
      console.error('Error al registrar la producto:', error);
      return throwError(error);
    })
  ).subscribe();
}

onSubmitUser() {
  console.log('Datos a guardar:', this.newUser);
  this.userService.createUser(this.newUser).pipe(
    tap(response => {
      if (response) {
        console.log("Registro exitoso",response);
        this.getAllUser();
      } else {
        console.error('Error al registrar la user:', response);
        // Aquí puedes manejar otros tipos de errores según el caso
      }
    }),
    catchError(error => {
      console.error('Error al registrar la user:', error);
      return throwError(error);
    })
  ).subscribe();
}

onFileSelect(event: any) {
  const file = event?.target?.files?.[0];
  
  if (file) {
      this.image.image = file;
      this.image.imageFileName = file.name;

      // Crear una URL local para la imagen seleccionada
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = (event) => {
          this.selectedImageURL = event.target?.result as string;
      };
  }
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

getAllProduct(): void {
    this.productService.getAllProducts().subscribe(
      (data: ProductRequest[]) => {
        this.products = data;
      },
      (error) => {
        console.error('Error fetching productos:', error);
      }
    );
  }
  
getAllProductbyHighlight(): void {
    this.productService.getAllProductsHighlight().subscribe(
      (data: ProductRequest[]) => {
        this.productsHighlights = data;
      },
      (error) => {
        console.error('Error fetching productos destacados:', error);
      }
    );
  }   

getAllUser(): void {
    this.userService.getAllUsers().subscribe(
      (data: UserRequest[]) => {
        this.users = data;
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
}  
getAllHistory(): void {
  this.historyService.getAllHistory().subscribe(
    (data: HistoryRequest[]) => {
      this.histories = data;
    },
    (error) => {
      console.error('Error fetching histories:', error);
    }
  );
}
getMostSearchProduct(): void {
  this.productService.getMostSearched().subscribe(
    (data: SearchRequest) => {
      this.mostSearched = data;
    },
    (error) => {
      console.error('Error fetching most search product:', error);
    }
  );
}

updateProduct(){
    const formData = new FormData();
    // Agregar los datos del producto como un objeto, no como una cadena JSON
    formData.append('product', new Blob([JSON.stringify({
      name: this.editingProduct.name,
      description: this.editingProduct.description,
      price: this.editingProduct.price,
      stock: this.editingProduct.stock,
      highlight: this.editingProduct.highlight,
      categoryName: this.editingProduct.categoria
    })], {
      type: 'application/json'
    }));
    // Verificar si hay una nueva imagen seleccionada para el producto
    if (this.image.image) {
      // Agregar la nueva imagen al FormData
      formData.append('file', this.image.image, this.image.image.name);
  } else {
      // Agregar un parámetro indicando que se debe mantener la imagen actual
      formData.append('keepCurrentImage', 'true');
  }

    console.log('Archivo:', formData.get('file'));
    console.log('Objeto:',formData.get('request'))
  
    this.productService.updateProduct(formData,this.editingProduct.id).pipe(
      tap(response => {
        console.log("Respuesta del servidor:", response);
        if (response) {
          console.log("Update exitoso",response.message);
          this.getAllProduct();
          this.getAllProductbyHighlight();
          this.getAllCategory()
        } else {
          console.error('Error al actualizar la producto:', response);
        }
      }),
      catchError(error => {
        console.error('Error al actualizar la producto:', error);
        return throwError(error);
      })
    ).subscribe();
  }

toggleChangeImage() {
    if (!this.changeImage) {
      this.selectedImageURL = 'null';
      this.changeImage=true;
    }
}
updateCategory() {
  if (this.editingCategory.id === null) {
    console.error('No hay categoría seleccionada para editar');
    return;
  }
  this.categoryService.updateCategory(this.editingCategory)
    .subscribe(updatedCategory => {
      console.log('Categoría actualizada:', updatedCategory);
      this.getAllProduct();
      this.getAllProductbyHighlight();
      this.getAllCategory()
      // Cerrar el modal programáticamente
  });
}

updateUser() {
  if (this.editingUser.id === null) {
    console.error('No hay user seleccionada para editar');
    return;
  }
  this.userService.updateUser(this.editingUser)
    .subscribe(response => {
      console.log('User actualizada:', response);
      this.getAllProduct();
      this.getAllProductbyHighlight();
      this.getAllCategory();
      this.getAllUser();
      // Cerrar el modal programáticamente
  });
}

updateUserCurrent() {
  if (this.currentUser.id === null) {
    console.error('No hay user seleccionada para editar');
    return;
  }
  this.userService.updateUser(this.currentUser)
    .subscribe(response => {
      console.log('User actualizada:', response);
      this.getAllProduct();
      this.getAllProductbyHighlight();
      this.getAllCategory();
      this.getAllUser();
      // Cerrar el modal programáticamente
  });
}


changePassword() {
  console.log('Entré a cambiar la contraseña');

  if (!this.currentUser.id) {
    console.log('No hay usuario para cambiar la contraseña');
    return;
  }

  if (this.newPassword !== this.newPasswordRepeat) {
    console.log('Las contraseñas no coinciden');
    return;
  }
  console.log('Las contraseñas coinciden');
  this.password.newPassword = this.newPassword.toString();
  this.password.currentPassword = this.oldPassword.toString();

  this.userService.updatePassword(this.currentUser.id,this.password)
    .subscribe(response => {
      console.log('Password actualizado:', response);
      this.getAllProduct();
      this.getAllProductbyHighlight();
      this.getAllCategory();
      this.getAllUser();
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

deleteProduct(id:number){
  this.productService.deleteProduct(id).subscribe(
    (data: Product) => {
      console.log('Producto eliminada exitosamente:', data);
      this.getAllProduct();
    },
    (error) => {
      console.error('Error al eliminar la producto:', error);
    }
  );
}

deleteUser(id: number): void {
  this.userService.deleteUser(id).pipe(
    tap(response => {
      if (response) {
        console.log('Usuario eliminado exitosamente:', response);
        this.getAllUser();
      } 
    }),
    catchError(error => {
      console.error('Error al eliminar el usuario:', error);
      return throwError(error);
    })
  ).subscribe();
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
loadProduct(productId: number): void {
    this.productService.getProductById(productId).subscribe(
    (data: any) => {
        this.editingProduct = data;
      },
      (error) => {
        console.error('Error fetching product details:', error);
      }
    );
  }
loadUser(userId: number): void {
    this.userService.getUserById(userId).subscribe(
    (data: any) => {
        this.editingUser = data;
      },
      (error) => {
        console.error('Error fetching product details:', error);
      }
    );
  }


  // Agregar campos de datos del perfil
  //formData.append('name', this.producto.name);
  //formData.append('description', this.producto.description);
  //formData.append('price', this.producto.price.toString());
  //formData.append('stock', this.producto.stock.toString());
  //formData.append('categoryName', this.producto.categoria);

  // Agregar archivo de imagen de perfil
  //if (this.image.image) {
  //    formData.append('file', this.image.image, this.image.image.name);
  //}

}
