import { Component, OnInit, Injector } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NGXLogger } from 'ngx-logger';
import { environment } from 'src/environments/environment';
import { AppConstants } from '../app.constants';
import { CountryCode } from '../country-code.enum';
import { ServicesDataService } from '../services/services-data.service';
import { AuthProviderService } from './../services/auth-provider.service';

declare const require: any;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  private language: string;

  imageBlobUrl: string | ArrayBuffer | null;
  isAdmin: boolean;

  constructor(
    private logger: NGXLogger,
    // private translateService: TranslateService,
    protected injector: Injector,
    private servicesDataService: ServicesDataService,
    public authProviderService: AuthProviderService
  ) { }

  ngOnInit() {
    this.language = CountryCode.FR;
    this.injector.get(TranslateService).use(this.language);
    this.authProviderService.admin.subscribe(value => {
      this.isAdmin = value;
    });
    this.isAdmin = this.isAdmin || this.authProviderService.isAdmin();
    // this.translateService.use(this.language);
    this.loadImage();
  }

  public getLanguage(): string {
    return this.language;
  }

  public switchLanguage(language: string): void {
    if (!environment.production) {
      this.logger.debug(AppConstants.LANGUAGE_CHANGE);
    }
    this.injector.get(TranslateService).use(language);
    this.language = language;
  }

  loadImage() {
    let empty = false;
    this.authProviderService.imageBlobUrl.subscribe(value => {
      this.imageBlobUrl = value;
      empty = !this.imageBlobUrl;
    });
    if (empty) {
      empty = this.imageBlobUrl = require('../../assets/images/avatar.jpg');
      const pseudo = this.authProviderService.getPseudo();
      this.servicesDataService.getImageURL(pseudo)
        .subscribe(data => {
          this.createImageFromBlob(data);
        }, error => {
          this.logger.error(error);
          this.imageBlobUrl = require('../../assets/images/avatar.jpg');
        });
    }
  }

  private createImageFromBlob(image: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imageBlobUrl = reader.result;
    }, false);
    if (image) {
      reader.readAsDataURL(image);
    }
  }
}
