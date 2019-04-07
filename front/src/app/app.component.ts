import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {CountryCode} from "./country-code.enum";
import {NGXLogger} from 'ngx-logger';
import {AppConstants} from "./app.constants";
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private language: string;

  constructor(
      private translateService: TranslateService,
      private logger: NGXLogger
  ) {}


  ngOnInit(): void {
    this.language = CountryCode.FR;
    this.translateService.use(this.language);
  }

  public switchLanguage(language: string): void {
    if (false === environment.production) {
      this.logger.debug(AppConstants.LANGUAGE_CHANGE);
    }
    this.translateService.use(language);
    this.language = language;
  }

  public getLanguage(): string {
    return this.language;
  }
}
