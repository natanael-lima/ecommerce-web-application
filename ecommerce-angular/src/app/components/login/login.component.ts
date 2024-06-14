import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginRequest } from '../../interfaces/loginRequest';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import { catchError, tap, throwError } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  successMessage : boolean = false;
  errorMessage : boolean = false;
  messageContent = '';
  loading: boolean = false; // Variable para controlar la animación de carga
  creds: LoginRequest ={
    username:'',
    password:''
  };
  constructor( private loginService: LoginService, private router:Router) { 
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  
  onLogin(){
    debugger;
      this.loginService.loginUser(this.creds).pipe(
      tap(response => {
        // Sesion exitoso, mostrar el mensaje
        this.loading = true; // Desactivar la animación de carga
        this.showMessageSuccess(' ¡Inicio de sesión exitoso!', 3000);
        // Redirigir al usuario después de un tiempo determinado
        setTimeout(() => {
          this.router.navigate(['/admin/dashboard']);
        }, 4000); // Redirigir después de 4 segundos
      }),
      catchError(error => {
        // Error en el inicio de sesión
        console.error('Error al iniciar sesión:', error);
        this.loading = false; // Desactivar la animación de carga
        this.showMessageError(' ¡Error de Credenciales!', 3000);
        this.errorMessage = true;// Puedes manejar el error aquí, por ejemplo, mostrar un mensaje de error
        return throwError(error);
      })
    ).subscribe();
  }

  private showMessageError(message: string, timeout: number) {
    this.messageContent = message;
    this.errorMessage = true;
    // Configurar temporizador para ocultar el mensaje después de 'timeout' milisegundos
    setTimeout(() => {
      this.errorMessage = false;
    }, timeout);
  }
  private showMessageSuccess(message: string, timeout: number) {
    this.messageContent = message;
    this.successMessage = true;
    // Configurar temporizador para ocultar el mensaje después de 'timeout' milisegundos
    setTimeout(() => {
      this.successMessage = false;
    }, timeout);
  }



}
