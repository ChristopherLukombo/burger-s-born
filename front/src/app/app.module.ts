import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatInputModule, MatNativeDateModule } from '@angular/material';
import { MatDatepickerModule } from '@angular/material/datepicker';
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
import { DialogSuccessComponent } from './dialog-success/dialog-success.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ProductComponent } from './product/product.component';
import { RegisterComponent } from './register/register.component';
import { AuthProviderService } from './services/auth-provider.service';
import { ServicesDataService } from './services/services-data.service';
import { HeaderComponent } from './header/header.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MenuComponent } from './menu/menu.component';
import { ImporterComponent } from './importer/importer.component';

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
    ProductComponent,
    NavbarComponent,
    HeaderComponent,
    MenuComponent,
    ImporterComponent
  ],
  exports: [
    DialogSuccessComponent,
    TranslateModule,
    NavbarComponent,
    HeaderComponent,
  ],
  entryComponents: [DialogSuccessComponent],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
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
