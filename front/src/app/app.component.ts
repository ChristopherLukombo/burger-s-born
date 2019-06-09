import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {CountryCode} from './country-code.enum';
import {NGXLogger} from 'ngx-logger';
import {AppConstants} from './app.constants';
import {environment} from '../environments/environment';
import {AuthProviderService} from './services/auth-provider.service';
import {Router} from '@angular/router';

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
      private router: Router
  ) {}


  ngOnInit(): void {
    this.language = CountryCode.FR;
    this.translateService.use(this.language);
    this.isAdmin = this.authProviderService.isAdmin();
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
}
