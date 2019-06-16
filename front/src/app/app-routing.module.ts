import { AuthGuard } from './auth.guard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { HomeComponent } from './home/home.component';
import { ProductComponent } from './product/product.component';
import { RegisterComponent } from './register/register.component';
import { MenuComponent } from './menu/menu.component';
import { ImporterComponent } from './importer/importer.component';


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'auth', component: AuthComponent},
  {path: 'product', component: ProductComponent},
  {path: 'menu' , component: MenuComponent},
  {path: 'importer', component: ImporterComponent},
  // {path: 'register', canActivate: [AuthGuard], component: RegisterComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }
