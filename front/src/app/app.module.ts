import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ServicesDataService} from "./services/services-data.service";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule, Routes} from "@angular/router";
import { HomeComponent } from './home/home.component';

const appRoutes: Routes = [
  {path: 'home', component: HomeComponent},
  // {path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
  ],
  providers: [
      ServicesDataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
