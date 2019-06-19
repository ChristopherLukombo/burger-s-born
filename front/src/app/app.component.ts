import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NGXLogger } from 'ngx-logger';
import { environment } from '../environments/environment';
import { Paypal } from './../model/model.paypal';
import { AppConstants } from './app.constants';
import { CountryCode } from './country-code.enum';
import { AuthProviderService } from './services/auth-provider.service';
import { ServicesDataService } from './services/services-data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private language: string;

  isAdmin: boolean;

  constructor(
      private translateService: TranslateService,
      private logger: NGXLogger,
      public authProviderService: AuthProviderService,
      private router: Router,
      private servicesDataService: ServicesDataService,
  ) {}


  ngOnInit(): void {
    this.language = CountryCode.FR;
    this.translateService.use(this.language);
    this.isAdmin = this.authProviderService.isAdmin();
    this.completePayment();
  }

  public switchLanguage(language: string): void {
    if (!environment.production) {
      this.logger.debug(AppConstants.LANGUAGE_CHANGE);
    }
    this.translateService.use(language);
    this.language = language;
  }

  public getLanguage(): string {
    return this.language;
  }

  public logout() {
    this.authProviderService.logout()
        .subscribe();
    this.router.navigate(['/auth']);
  }

  completePayment() {
    const url = new URL(window.location.href);
    const paymentId = url.searchParams.get('paymentId');
    const payerID = url.searchParams.get('PayerID');

    if (!paymentId || !payerID) {
      return;
    }
    const paypal = new Paypal();
    paypal.paymentId = paymentId;
    paypal.payerID = payerID;
    this.servicesDataService.completePayment(paypal)
    .subscribe(data => {
      this.logger.debug('Completed payment!');
    }, err => {
      this.logger.error('Error payment could n\'t be completed');
    });
  }
}