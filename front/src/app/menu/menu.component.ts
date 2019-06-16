import { Component, OnInit } from '@angular/core';
import {ServicesDataService} from '../services/services-data.service';
import {HttpErrorResponse} from '@angular/common/http';
import { Menu } from '../../model/model.menu';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  errorMessage;
  successMessage;
  menus;
  totalElements;
  pages;
  selectedPage = 0;
  constructor(private servicesDataService: ServicesDataService) { }

  ngOnInit() {
    this.findAll(0);
  }

  findAll(indexPage) {
    this.servicesDataService.findAllMenus(indexPage)
        .subscribe(data => {

          this.menus = data.body['content'];
          this.totalElements = data.body['totalElements'];
          this.pages = Array(data.body['totalPages']).fill(0).map((x, i) => i+1);
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

}
