import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {CountryCode} from "./country-code.enum";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private language: string;

  constructor(
      private translateService: TranslateService,
  ) {}


  ngOnInit(): void {
    this.language = CountryCode.FR;
    this.translateService.setDefaultLang(CountryCode.EN);
    this.translateService.use(this.language);
  }

  public switchLanguage(language: string): void {
    this.translateService.use(language);
    this.language = language;
  }

  public getLanguage(): string {
    return this.language;
  }
}
