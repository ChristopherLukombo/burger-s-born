import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCheckboxModule, MatDatepickerModule, MatDialogModule, MatInputModule, MatNativeDateModule, MatStepperModule } from '@angular/material';
import { MatRadioModule } from '@angular/material/radio';
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
import { AuthComponent } from '../auth/auth.component';
import { DialogRedirectionComponent } from '../dialog-redirection/dialog-redirection.component';
import { DialogSuccessComponent } from '../dialog-success/dialog-success.component';
import { HeaderComponent } from '../header/header.component';
import { HomeComponent } from '../home/home.component';
import { ImporterComponent } from '../importer/importer.component';
import { MenuComponent } from '../menu/menu.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { OrdersComponent } from '../orders/orders.component';
import { ProductComponent } from '../product/product.component';
import { RegisterComponent } from '../register/register.component';
import { AuthProviderService } from '../services/auth-provider.service';
import { ServicesDataService } from '../services/services-data.service';
import { StepOrderComponent } from '../step-order/step-order.component';
import { CreateMenuComponent } from '../create-menu/create-menu.component';
import { CreateProductComponent } from '../create-product/create-product.component';

describe('AuthProviderService', () => {
  beforeEach(() => TestBed.configureTestingModule({
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
        CreateMenuComponent,
        CreateProductComponent,
        OrdersComponent,
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
      MatCheckboxModule,
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
  }));

  it('should be created', () => {
    const service: AuthProviderService = TestBed.get(AuthProviderService);
    expect(service).toBeTruthy();
  });
});