import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ServicesDataService} from './services/services-data.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {HomeComponent} from './home/home.component';
import {RegisterComponent} from './register/register.component';
import {MatDialogModule, MatInputModule, MatNativeDateModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {LoggerModule, NgxLoggerLevel} from 'ngx-logger';
import {AuthComponent} from './auth/auth.component';
import {AuthProviderService} from './services/auth-provider.service';
import {LocalStorageService} from 'ngx-webstorage';
import {AuthGuard} from './auth.guard';
import {JwtModule} from '@auth0/angular-jwt';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { DialogSuccessComponent } from './dialog-success/dialog-success.component';
import { ProductComponent } from './product/product.component';

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
    ProductComponent
  ],
  exports: [
    DialogSuccessComponent,
    TranslateModule,
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
    MatDatepickerModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
