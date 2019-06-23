import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ServicesDataService } from '../services/services-data.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  errorMessage: string;
  menus;

  constructor(private servicesDataService: ServicesDataService) { }

  ngOnInit() {
    this.getAllTrendsMenus();
  }

  getAllTrendsMenus() {
    this.servicesDataService.getAllTrendsMenus()
      .subscribe(data => {
        this.menus = data.body;
      }, err => {
        if (err instanceof HttpErrorResponse) {
          if (403 === err.status) {
            this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
          } else if (404 === err.status) {
            this.errorMessage = 'Pas de menus';
          } else {
            this.errorMessage = 'Une erreur serveur s\'est produite.';
          }
        }
      });
  }
}
