import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMenuComponent } from './create-menu.component';
import {AppComponent} from '../app.component';
import {HomeComponent} from '../home/home.component';
import {RegisterComponent} from '../register/register.component';
import {AuthComponent} from '../auth/auth.component';
import {DialogSuccessComponent} from '../dialog-success/dialog-success.component';
import {ProductComponent} from '../product/product.component';
import {NavbarComponent} from '../navbar/navbar.component';
import {HeaderComponent} from '../header/header.component';
import {MenuComponent} from '../menu/menu.component';
import {ImporterComponent} from '../importer/importer.component';
import {StepOrderComponent} from '../step-order/step-order.component';
import {DialogRedirectionComponent} from '../dialog-redirection/dialog-redirection.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from '../app-routing.module';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {
  MatDatepickerModule, MatDialogModule,
  MatInputModule,
  MatNativeDateModule,
  MatRadioModule,
  MatSelectModule,
  MatStepperModule
} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {HttpLoaderFactory} from '../app.module';
import {LoggerModule, NgxLoggerLevel} from 'ngx-logger';
import {JwtModule} from '@auth0/angular-jwt';
import {ServicesDataService} from '../services/services-data.service';
import {AuthProviderService} from '../services/auth-provider.service';
import {LocalStorageService} from 'ngx-webstorage';
import {AuthGuard} from '../auth.guard';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

describe('CreateMenuComponent', () => {
  let component: CreateMenuComponent;
  let fixture: ComponentFixture<CreateMenuComponent>;



  beforeEach(() => {
    fixture = TestBed.createComponent(CreateMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CreateMenuComponent,
        AppComponent,
        HomeComponent,
        RegisterComponent,
        AuthComponent,
        DialogSuccessComponent,
        ProductComponent,
        NavbarComponent,
        HeaderComponent,
        NavbarComponent,
        HeaderComponent,
        MenuComponent,
        ImporterComponent,
        StepOrderComponent,
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
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    })
        .compileComponents();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
