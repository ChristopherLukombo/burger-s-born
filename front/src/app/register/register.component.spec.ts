import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RegisterComponent} from './register.component';
import {HomeComponent} from '../home/home.component';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from '../app-routing.module';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {MatDatepickerModule, MatDialogModule, MatInputModule, MatNativeDateModule} from '@angular/material';
import {MatSelectModule} from '@angular/material/select';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ServicesDataService} from '../services/services-data.service';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {HttpLoaderFactory} from '../app.module';
import {LoggerModule, NgxLoggerLevel} from 'ngx-logger';
import {AppComponent} from '../app.component';
import {AuthComponent} from '../auth/auth.component';
import {DialogSuccessComponent} from '../dialog-success/dialog-success.component';
import {JwtModule} from '@auth0/angular-jwt';
import {AuthProviderService} from '../services/auth-provider.service';
import {LocalStorageService} from 'ngx-webstorage';
import {AuthGuard} from '../auth.guard';
import {ProductComponent} from '../product/product.component';


describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;


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
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
