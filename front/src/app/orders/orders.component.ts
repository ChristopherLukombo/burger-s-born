import { Component, OnInit } from '@angular/core';
import { AuthProviderService } from '../services/auth-provider.service';
import { ServicesDataService } from '../services/services-data.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Command } from 'src/model/model.command';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  errorMessage: string;
  successMessage: string;

  commands: Array<Command>;
  totalElements: number;
  pages: Array<number>;
  selectedPage = 0;

  constructor(
    private servicesDataService: ServicesDataService,
    private authProviderService: AuthProviderService
  ) { }

  ngOnInit() {
    this.getAll(0);
  }

  getAll(indexPage: number): void {
    const customerId = this.authProviderService.getIdCustomer();
    this.servicesDataService.getAllCommands(indexPage, customerId)
      .subscribe(data => {

        this.commands = data.body['content'];
        this.totalElements = data.body['totalElements'];
        this.pages = Array(data.body['totalPages']).fill(0)
          .map((x, i) => i + 1);
        this.selectedPage = indexPage;

      }, err => {
        if (err instanceof HttpErrorResponse) {
          if (403 === err.status) {
            this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
          } else if (404 === err.status) {
            this.errorMessage = 'Pas de commandes';
          } else {
            this.errorMessage = 'Une erreur serveur s\'est produite.';
          }
        }
      });
  }

  cancel(commandId: number): void {
    this.servicesDataService.cancelPayment(commandId)
      .subscribe(data => {
        let indexToRemove = 0;
        this.commands.forEach(function (command, index) {
          if (commandId === command.id) {
            indexToRemove = index;
          }
        });
        this.commands.splice(indexToRemove, 1);
        if (this.totalElements > 0) {
          this.totalElements -= 1;
        }
        if (this.commands.length === 0) {
          this.commands = undefined;
          this.errorMessage = 'Pas de commandes';
        }
      }, error => {
        if (error instanceof HttpErrorResponse) {
          if (403 === error.status) {
            this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
          } else if (404 === error.status) {
            this.errorMessage = 'Pas de commandes';
          } else {
            this.errorMessage = 'Une erreur serveur s\'est produite.';
          }
        }
      });
  }

  isDisplay(date: Date) {
    const timeToday = new Date();
    timeToday.setHours(0, 0, 0, 0);
    const timeCommand = new Date(date).setHours(0, 0, 0, 0);
    return timeCommand === timeToday.getTime();
  }

  mapStatus(status: string) {
    let statusCommand;
    if ('Waitting' === status) {
      statusCommand = 'En attente';
    } else if ('Paid' === status) {
      statusCommand = 'Payé';
    }
    return statusCommand;
  }
}
