import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { ProductComponent } from './components/product/product.component';
import { LoginComponent } from './components/login/login.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';


export const routes: Routes = [ 
{ path: 'admin', component: LoginComponent },
{ path: 'admin/dashboard', component: AdminDashboardComponent },
{ path: 'home', component: HomeComponent },
{ path: 'product', component: ProductComponent },
{ path: 'product/product-detail/:id', component: ProductDetailComponent },
{ path: '', redirectTo: '/home', pathMatch: 'full' },
{ path: '**', redirectTo: '/home' }
];
