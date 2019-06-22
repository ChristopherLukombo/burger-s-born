import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatStepperModule, MatInputModule, MatNativeDateModule } from '@angular/material';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';
import { LocalStorageService } from 'ngx-webstorage';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthGuard } from './auth.guard';
import { AuthComponent } from './auth/auth.component';
import { DialogRedirectionComponent } from './dialog-redirection/dialog-redirection.component';
import { DialogSuccessComponent } from './dialog-success/dialog-success.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ProductComponent } from './product/product.component';
import { RegisterComponent } from './register/register.component';
import { AuthProviderService } from './services/auth-provider.service';
import { ServicesDataService } from './services/services-data.service';
import { StepOrderComponent } from './step-order/step-order.component';
import { ImporterComponent } from './importer/importer.component';
import { MenuComponent } from './menu/menu.component';
import { HeaderComponent } from './header/header.component';
import { OrdersComponent } from './orders/orders.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}
export function tokenGetter() {
  return localStorage.getItem('authenticationToken');
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    AuthComponent,
    DialogSuccessComponent,
    DialogRedirectionComponent,
    ProductComponent,
    NavbarComponent,
    HeaderComponent,
    MenuComponent,
    ImporterComponent,
    HeaderComponent,
    MenuComponent,
    StepOrderComponent,
    OrdersComponent
  ],
  exports: [
    DialogSuccessComponent,
    DialogRedirectionComponent,
    TranslateModule,
    NavbarComponent,
    HeaderComponent,
  ],
  entryComponents: [
    DialogSuccessComponent,
    DialogRedirectionComponent
  ],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatStepperModule,
    MatRadioModule,
    BrowserAnimationsModule,
    FormsModule,
    MatDialogModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    LoggerModule.forRoot(
        {
          level: NgxLoggerLevel.DEBUG,
          serverLogLevel: NgxLoggerLevel.ERROR
        }
    ),
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
      }
    })
  ],
  providers: [
    ServicesDataService,
    AuthProviderService,
    LocalStorageService,
    AuthGuard,
    MatDatepickerModule
  ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  bootstrap: [AppComponent]
})
export class AppModule { }
