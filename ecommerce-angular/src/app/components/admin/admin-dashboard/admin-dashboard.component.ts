import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
  currentSection: string = 'dashboard'; // Sección actual

  // Función para cambiar la sección actual
changeSection(section: string): void {
  this.currentSection = section;
}
}
