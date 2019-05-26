import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthComponent } from './auth.component';
import {AppComponent} from '../app.component';
import {HomeComponent} from '../home/home.component';
import {RegisterComponent} from '../register/register.component';
import {ProductComponent} from '../product/product.component';
import {DialogSuccessComponent} from '../dialog-success/dialog-success.component';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from '../app-routing.module';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {MatDatepickerModule, MatDialogModule, MatInputModule, MatNativeDateModule} from '@angular/material';
import {MatSelectModule} from '@angular/material/select';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {HttpLoaderFactory} from '../app.module';
import {LoggerModule, NgxLoggerLevel} from 'ngx-logger';
import {ServicesDataService} from '../services/services-data.service';
import {JwtModule} from '@auth0/angular-jwt';
import {AuthProviderService} from '../services/auth-provider.service';
import {LocalStorageService} from 'ngx-webstorage';
import {AuthGuard} from '../auth.guard';

describe('AuthComponent', () => {
  let component: AuthComponent;
  let fixture: ComponentFixture<AuthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        HomeComponent,
        RegisterComponent,
        ProductComponent,
        AuthComponent,
        DialogSuccessComponent
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
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
