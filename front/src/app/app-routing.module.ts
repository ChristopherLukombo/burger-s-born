import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { AuthComponent } from './auth/auth.component';
import { HomeComponent } from './home/home.component';
import { MenuComponent } from './menu/menu.component';
import { ProductComponent } from './product/product.component';
import { CreateProductComponent } from './create-product/create-product.component';
import { RegisterComponent } from './register/register.component';
import { ImporterComponent } from './importer/importer.component';
import { StepOrderComponent } from './step-order/step-order.component';
import { OrdersComponent } from './orders/orders.component';
import { CreateMenuComponent } from './create-menu/create-menu.component';
import { RoleName } from './RoleName';


const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'auth',
    component: AuthComponent
  },
  {
    path: 'product',
    component: ProductComponent
  },
  {
    path: 'createProduct',
    canActivate: [AuthGuard],
    data: { roles: [RoleName.ROLE_ADMIN] },
    component: CreateProductComponent
  },
  {
    path: 'menu',
    component: MenuComponent
  },
  {
    path: 'importer',
    canActivate: [AuthGuard],
    data: { roles: [RoleName.ROLE_ADMIN] },
    component: ImporterComponent
  },
  {
    path: 'steporder',
    canActivate: [AuthGuard],
    data: { roles: [RoleName.ROLE_CUSTOMER] },
    component: StepOrderComponent
  },
  {
    path: 'createMenu',
    canActivate: [AuthGuard],
    data: { roles: [RoleName.ROLE_ADMIN] },
    component: CreateMenuComponent
  },
  {
    path: 'orders',
    component: OrdersComponent
  },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,  { useHash: true })],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }
