import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule, MatDialogModule, MatInputModule, MatNativeDateModule, MatSelectModule, MatStepperModule, MatRadioModule } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';
import { LocalStorageService } from 'ngx-webstorage';
import { AppRoutingModule } from '../app-routing.module';
import { AppComponent } from '../app.component';
import { HttpLoaderFactory } from '../app.module';
import { AuthGuard } from '../auth.guard';
import { AuthComponent } from '../auth/auth.component';
import { DialogSuccessComponent } from '../dialog-success/dialog-success.component';
import { HeaderComponent } from '../header/header.component';
import { HomeComponent } from '../home/home.component';
import { ProductComponent } from '../product/product.component';
import { RegisterComponent } from '../register/register.component';
import { AuthProviderService } from '../services/auth-provider.service';
import { ServicesDataService } from '../services/services-data.service';
import { MenuComponent } from './../menu/menu.component';
import { StepOrderComponent } from './../step-order/step-order.component';
import { NavbarComponent } from './navbar.component';
import { STEPPER_GLOBAL_OPTIONS, CdkStepperModule } from '@angular/cdk/stepper';
import { DialogRedirectionComponent } from '../dialog-redirection/dialog-redirection.component';


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
        MenuComponent,
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
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
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