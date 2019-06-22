import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Menu } from 'src/model/model.menu';
import { Payment } from 'src/model/model.payment';
import { Product } from 'src/model/model.product';
import { ServicesDataService } from '../services/services-data.service';
import { DialogRedirectionComponent } from './../dialog-redirection/dialog-redirection.component';
import { AuthProviderService } from './../services/auth-provider.service';
import { MatStepper } from '@angular/material/stepper';
import { Command } from 'src/model/model.command';
import { NGXLogger } from 'ngx-logger';

const WIDTH = '50%';
const HEIGHT = '15%';

@Component({
  selector: 'app-step-order',
  templateUrl: './step-order.component.html',
  styleUrls: ['./step-order.component.css'],
})
export class StepOrderComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  isLinear = false;
  isAuthenticated: boolean;
  isEditable = false;

  errorMessage: string;
  successMessage: string;
  menus;

  see = false;

  products;

  active_fish: string;
  menuId: number;
  dessertId: number;

  menuSelected: Menu;
  desserts = new Array<any>();
  dessert;

  platSelected: Product;

  constructor(
    private _formBuilder: FormBuilder,
    public authProviderService: AuthProviderService,
    private servicesDataService: ServicesDataService,
    public dialog: MatDialog,
    private logger: NGXLogger,
  ) { }

  ngOnInit() {
    this.initAuthenticate();
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: [null]
    });
    this.findAll();
  }

  get f() { return this.firstFormGroup.controls; }

  private initAuthenticate() {
    this.isAuthenticated = this.authProviderService.isAuthenticated();
    this.authProviderService.connect.subscribe(value => {
      this.isAuthenticated = value ? value : this.isAuthenticated;
    });
  }

  findAll() {
    this.servicesDataService.findAllMenus(0)
      .subscribe(data => {
        this.menus = data.body['content'];
        this.logger.log(this.menus);
      }, err => {
        if (err instanceof HttpErrorResponse) {
          if (403 === err.status) {
            this.logger.error(err);

            this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
          } else {
            this.errorMessage = 'Une erreur serveur s\'est produite.';
          }
        }
      });
  }

  seeMenu(menu: Menu) {
    if (!this.see) {
      this.see = true;
    } else {
      this.see = false;
    }

    this.menuId = menu.id;
    this.menuSelected = menu;
    this.servicesDataService.findAllProductBymenuId('plat', menu.id)
      .subscribe(data => {
        this.logger.log(data.body);
        this.products = data.body;
      }, err => {
        this.logger.log(err);
      });
  }

  validateProduct(stepper: MatStepper) {

    if (this.firstFormGroup.get('firstCtrl').value === '') {
      return;
    }

    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id === +this.firstFormGroup.get('firstCtrl').value) {
        this.platSelected = this.products[i];
        break;
      }
    }

      this.servicesDataService.findProductsByCategoryName('dessert')
      .subscribe(data => {
        // TODO Récupérer les frites
        // this.firstFormGroup.get('firstCtrl').value;
        stepper.next();
        this.products = data.body as Array<Product>;
      }, err => {
        console.log(err);
      });
  }


  takeDessert() {
    const id = +this.secondFormGroup.get('secondCtrl').value;
    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id === id) {
        this.desserts.push(this.products[i]);
        break;
      }
    }
  }

  return() {
    this.see = false;
  }

  createPayment() {
    // TODO refactorer le code.
    const command = new Command();
    command.date = new Date();
    const products = this.desserts;
    command.productsDTO = products;
    const menus = new Array<Menu>();
    const prods = new Array<Product>();
    // plat
    prods.push(this.platSelected);
    // // dessert
    this.menuSelected.productsDTO = prods;
    menus.push(this.menuSelected);
    command.menusDTO = menus;

    command.customerId = this.authProviderService.getIdCustomer();

    this.servicesDataService.createPayment(command)
      .subscribe(data => {
        const payment = data.body as Payment;
        if (payment.redirect_url) {
          (window as any).location.replace(payment.redirect_url);
          this.openDialogRedirection();
        }
      }, error => {
        console.error(error);
      });

  }

  public openDialogRedirection(): void {
    this.dialog.open(
      DialogRedirectionComponent,
      {
        width: WIDTH,
        height: HEIGHT
      }
    );
  }

}