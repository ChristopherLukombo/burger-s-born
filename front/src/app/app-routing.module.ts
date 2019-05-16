import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./register/register.component";
import {AuthComponent} from "./auth/auth.component";
import {ProductComponent} from "./product/product.component";


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'auth', component: AuthComponent},
  {path: 'product', component: ProductComponent},
  // {path: 'register', canActivate: [AuthGuard], component: RegisterComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
