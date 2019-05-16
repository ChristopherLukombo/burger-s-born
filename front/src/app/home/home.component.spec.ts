import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import {AppComponent} from "../app.component";
import {RegisterComponent} from "../register/register.component";
import {ProductComponent} from '../product/product.component';
import {AuthComponent} from "../auth/auth.component";
import {DialogSuccessComponent} from "../dialog-success/dialog-success.component";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "../app-routing.module";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {MatDatepickerModule, MatDialogModule, MatInputModule, MatNativeDateModule} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {LoggerModule, NgxLoggerLevel} from "ngx-logger";
import {JwtModule} from "@auth0/angular-jwt";
import {ServicesDataService} from "../services/services-data.service";
import {AuthProviderService} from "../services/auth-provider.service";
import {LocalStorageService} from "ngx-webstorage";
import {AuthGuard} from "../auth.guard";
import {HttpLoaderFactory} from "../app.module";

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

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
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
