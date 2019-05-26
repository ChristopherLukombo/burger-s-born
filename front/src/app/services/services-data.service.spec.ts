import {TestBed} from '@angular/core/testing';

import {ServicesDataService} from './services-data.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {AppComponent} from '../app.component';
import {HomeComponent} from '../home/home.component';
import {RegisterComponent} from '../register/register.component';
import {ProductComponent} from '../product/product.component';
import {AuthComponent} from '../auth/auth.component';
import {DialogSuccessComponent} from '../dialog-success/dialog-success.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from '../app-routing.module';
import {MatDatepickerModule, MatDialogModule, MatInputModule, MatNativeDateModule} from '@angular/material';
import {MatSelectModule} from '@angular/material/select';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {HttpLoaderFactory} from '../app.module';
import {LoggerModule, NgxLoggerLevel} from 'ngx-logger';
import {JwtModule} from '@auth0/angular-jwt';
import {AuthProviderService} from './auth-provider.service';
import {LocalStorageService} from 'ngx-webstorage';
import {AuthGuard} from '../auth.guard';

describe('ServicesDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({
      declarations: [
          AppComponent,
          HomeComponent,
          RegisterComponent,
          AuthComponent,
          DialogSuccessComponent,
          ProductComponent
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
                  // ...
                  tokenGetter: () => {
                      return localStorage.getItem('authenticationToken');
                  }
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
  }));

  it('should be created', () => {
    const service: ServicesDataService = TestBed.get(ServicesDataService);
    expect(service).toBeTruthy();
  });
});
