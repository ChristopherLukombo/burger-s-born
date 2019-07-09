import { Component, OnInit } from '@angular/core';
import {ServicesDataService} from '../services/services-data.service';
import {HttpErrorResponse} from '@angular/common/http';
import { Menu } from '../../model/model.menu';
import {Router} from '@angular/router';
import { AuthProviderService } from '../services/auth-provider.service';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  errorMessage: string;
  successMessage: string;
  menus;
  totalElements;
  pages;
  selectedPage = 0;
  isAdmin: boolean;

  constructor(
      private servicesDataService: ServicesDataService,
      public authProviderService: AuthProviderService,
      private router: Router
  ) { }

  ngOnInit() {
    this.authProviderService.admin.subscribe(value => {
      this.isAdmin = value;
  });
  this.isAdmin = this.isAdmin || this.authProviderService.isAdmin();
    this.findAll(0);
  }

  findAll(indexPage: number) {
    this.servicesDataService.findAllMenus(indexPage)
        .subscribe(data => {

          this.menus = data.body['content'];
          this.totalElements = data.body['totalElements'];
          this.pages = Array(data.body['totalPages']).fill(0).map((x, i) => i + 1);
          this.selectedPage = indexPage;

        }, err => {
          if (err instanceof HttpErrorResponse) {
            if (403 === err.status) {
              this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
            } else {
              this.errorMessage = 'Une erreur serveur s\'est produite.';
            }
          }
        });
  }

  onNavigateToNewMenu() {
      this.router.navigate(['newMenu']);
  }

}
