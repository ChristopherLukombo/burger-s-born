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
  constructor(private servicesDataService: ServicesDataService) { }

  ngOnInit() {
    this.findAll();
  }

  findAll() {
    this.servicesDataService.findAllMenus()
        .subscribe(data => {
          this.menus = data.body['content'];
          console.log(this.menus);
        }, err => {
          if (err instanceof HttpErrorResponse) {
            if (403 === err.status) {
              console.log(err);

              this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
            } else {
              this.errorMessage = 'Une erreur serveur s\'est produite.';
            }
          }
        });
  }

}
