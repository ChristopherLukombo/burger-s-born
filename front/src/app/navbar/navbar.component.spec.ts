import { MenuComponent } from './../menu/menu.component';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NavbarComponent } from './navbar.component';
import { AppComponent } from '../app.component';
import { HomeComponent } from '../home/home.component';
import { RegisterComponent } from '../register/register.component';
import { AuthComponent } from '../auth/auth.component';
import { DialogSuccessComponent } from '../dialog-success/dialog-success.component';
import { ProductComponent } from '../product/product.component';
import { HeaderComponent } from '../header/header.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../app-routing.module';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { MatInputModule, MatDatepickerModule, MatNativeDateModule, MatSelectModule, MatDialogModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../app.module';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';
import { JwtModule } from '@auth0/angular-jwt';
import { ServicesDataService } from '../services/services-data.service';
import { AuthProviderService } from '../services/auth-provider.service';
import { LocalStorageService } from 'ngx-webstorage';
import { AuthGuard } from '../auth.guard';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
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
        MenuComponent
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
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});