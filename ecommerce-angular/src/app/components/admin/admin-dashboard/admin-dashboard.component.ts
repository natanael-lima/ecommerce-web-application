import { CommonModule } from '@angular/common';
import { AfterViewInit, Component } from '@angular/core';
import { FormBuilder, FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { LoginService } from '../../../services/login.service';
import { Router } from '@angular/router';
import { User } from '../../../interfaces/user';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
  currentSection: string = 'dashboard'; // Sección actual
  currentUser: User ={
    id:0,
    name:'',
    lastname:'',
    username:'',
    role:''
  };

  isLoggedIn: boolean = false;

  constructor(private userService:UserService, private formBuilder:FormBuilder, private loginService:LoginService,private router:Router ){
    
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

}
