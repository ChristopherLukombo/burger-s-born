import { HttpClient, HttpClientModule } from '@angular/common/http';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule, MatStepperModule, MatDialogModule, MatInputModule, MatNativeDateModule } from '@angular/material';
import { MatSelectModule } from '@angular/material/select';
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
import { DialogSuccessComponent } from '../dialog-success/dialog-success.component';
import { DialogRedirectionComponent } from '../dialog-redirection/dialog-redirection.component';
import { HeaderComponent } from '../header/header.component';
import { HomeComponent } from '../home/home.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { StepOrderComponent } from '../step-order/step-order.component';
import { ProductComponent } from '../product/product.component';
import { RegisterComponent } from '../register/register.component';
import { MatRadioModule} from '@angular/material/radio';
import { AuthProviderService } from '../services/auth-provider.service';
import { ServicesDataService } from '../services/services-data.service';
import { AuthComponent } from '../auth/auth.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MenuComponent } from '../menu/menu.component';
import { ImporterComponent } from '../importer/importer.component';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

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

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
