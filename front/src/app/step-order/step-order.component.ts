import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { MatStepper } from '@angular/material/stepper';
import { NGXLogger } from 'ngx-logger';
import { LocalStorageService } from 'ngx-webstorage';
import { Command } from 'src/model/model.command';
import { Menu } from 'src/model/model.menu';
import { Payment } from 'src/model/model.payment';
import { Product } from 'src/model/model.product';
import { ServicesDataService } from '../services/services-data.service';
import { DialogRedirectionComponent } from './../dialog-redirection/dialog-redirection.component';
import { AuthProviderService } from './../services/auth-provider.service';

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

  totalElements;

  pages;
  pages2;

  selectedPage = 0;
  selectedPage2 = 0;

  see = false;

  products: Array<Product>;

  active_fish: string;
  menuId: number;
  dessertId: number;

  menuSelected: Menu;
  desserts = new Array<any>();
  dessert;

  platSelected: Product;

  productsSelected: Array<Product> = [];

  constructor(
    private _formBuilder: FormBuilder,
    public authProviderService: AuthProviderService,
    private servicesDataService: ServicesDataService,
    public dialog: MatDialog,
    private logger: NGXLogger,
    private $localStorage: LocalStorageService,
  ) { }

  ngOnInit() {
    this.initAuthenticate();
    this.initFormsGroup();
    this.findAll(0);
  }

  private initFormsGroup() {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: [null]
    });
  }

  get f() { return this.firstFormGroup.controls; }

  private initAuthenticate() {
    this.isAuthenticated = this.authProviderService.isAuthenticated();
    this.authProviderService.connect.subscribe(value => {
      this.isAuthenticated = value ? value : this.isAuthenticated;
    });
  }

  findAll(indexPage: number) {
    this.servicesDataService.findAllMenus(indexPage)
      .subscribe(data => {
        this.logger.log(this.menus);
        this.menus = data.body['content'];
        this.totalElements = data.body['totalElements'];
        this.pages = Array(data.body['totalPages']).fill(0).map((x, i) => i + 1);
        this.selectedPage = indexPage;
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
        this.products = data.body as Array<Product>;
      }, err => {
        this.logger.error(err);
      });
  }

  validateProduct(stepper: MatStepper) {
    if (this.firstFormGroup.get('firstCtrl').value === '' ||
      this.firstFormGroup.get('firstCtrl').value === null) {
      return;
    }

    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id === +this.firstFormGroup.get('firstCtrl').value) {
        this.platSelected = this.products[i];
        break;
      }
    }
    this.findAllProductsByCategoryName(stepper, true, 0);
  }

  private findAllProductsByCategoryName(stepper: MatStepper, next: boolean, indexPage: number) {
    this.servicesDataService.findProductsByCategoryName(indexPage, 'dessert')
      .subscribe(data => {
        if (next) {
          stepper.next();
        }

        this.products = data.body['content'];
        this.totalElements = data.body['totalElements'];
        this.pages2 = Array(data.body['totalPages']).fill(0).map((x, i) => i + 1);
        this.selectedPage2 = indexPage;
      }, err => {
        this.logger.error(err);
      }, () => {

        const productsMap = new Map(JSON.parse(this.$localStorage.retrieve('products')));
        const productsCollected = productsMap.get(this.authProviderService.getIdCustomer()) as Array<Product>;

        for (let i = 0; i < this.products.length; i++) {
          const index = this.findIndex(productsCollected, this.products[i].id);
          if (index >= 0) {
            this.products[i].isSelected = true;
          }
        }
      });
  }

  takeDessert(): void {
    for (let i = 0; i < this.productsSelected.length; i++) {
      this.desserts.push(this.productsSelected[i]);
    }
  }

  return(): void {
    this.see = false;
    this.resetPreferences();
  }

  public resetPreferences(): void {
    const productsMap = new Map(JSON.parse(this.$localStorage.retrieve('products')));
    productsMap.set(this.authProviderService.getIdCustomer(), []);
    this.$localStorage.store('products', JSON.stringify(Array.from(productsMap.entries())));
  }

  createPayment(): void {
    this.resetPreferences();
    const command = this.getCommand();
    this.servicesDataService.createPayment(command)
      .subscribe(data => {
        const payment = data.body as Payment;
        if (payment.redirect_url) {
          (window as any).location.replace(payment.redirect_url);
          this.openDialogRedirection();
        }
      }, error => {
        this.logger.error(error);
      });
  }

  private getCommand(): Command {
    // Command Creation
    const command = new Command();

    const productsMap = new Map(JSON.parse(this.$localStorage.retrieve('products')));
    command.productsDTO = productsMap.get(this.authProviderService.getIdCustomer()) as Array<Product>;
    command.date = new Date();

    const menus = new Array<Menu>();
    const prods = new Array<Product>();
    // plat
    prods.push(this.platSelected);
    // dessert
    this.menuSelected.productsDTO = prods;

    menus.push(this.menuSelected);
    // Menu
    command.menusDTO = menus;
    command.customerId = this.authProviderService.getIdCustomer();
    return command;
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

  getCheckboxes(): void {
    this.productsSelected = this.products.filter(x => x.isSelected === true)
      .map(x => x);
    this.addProducts();
    this.removeProducts();
  }

  private addProducts(): void {
    if (this.$localStorage.retrieve('products') != null) {
      const productsMapAll = new Map<number, Array<Product>>();
      const productsMap = new Map(JSON.parse(this.$localStorage.retrieve('products')));
      const productsPreferences = productsMap.get(this.authProviderService.getIdCustomer()) as Array<Product>;

      for (let i = 0; i < this.productsSelected.length; i++) {
        if (this.findIndex(productsPreferences, this.productsSelected[i].id) === -1) {
          productsPreferences.push(this.productsSelected[i]);
          productsMapAll.set(this.authProviderService.getIdCustomer(), productsPreferences);
          this.$localStorage.store('products', JSON.stringify(Array.from(productsMapAll.entries())));
        }
      }
    } else {
      const productsMap = new Map<number, Array<Product>>();
      productsMap.set(this.authProviderService.getIdCustomer(), this.productsSelected);
      this.$localStorage.store('products', JSON.stringify(Array.from(productsMap.entries())));
    }
  }

  private removeProducts(): void {
    const productsToRemove = this.getProductToRemove();

    // Gestion de suppression des produits
    const productsMapAll = new Map(JSON.parse(this.$localStorage.retrieve('products')));
    const productsForCustomer = productsMapAll.get(this.authProviderService.getIdCustomer()) as Array<Product>;
    const productsMapToReplace = new Map(JSON.parse(this.$localStorage.retrieve('products')));

    if (productsMapToReplace.get(this.authProviderService.getIdCustomer()) != null &&
      productsToRemove.length > 0) {
      for (let i = 0; i < productsToRemove.length; i++) {
        if (this.findIndex(productsForCustomer, productsToRemove[i].id) >= 0) {
          productsForCustomer.splice(this.findIndex(productsForCustomer, productsToRemove[i].id), 1);
        }
      }
      const map = new Map<number, Array<Product>>();
      map.set(this.authProviderService.getIdCustomer(), productsForCustomer);
      this.$localStorage.store('products', JSON.stringify(Array.from(map.entries())));
    }
  }

  private getProductToRemove(): Array<Product> {
    const productsToRemove = [];
    for (let i = 0; i < this.products.length; i++) {
      if (this.findIndex(this.productsSelected, this.products[i].id) === -1) {
        productsToRemove.push(this.products[i]);
      }
    }
    return productsToRemove;
  }

  private findIndex(products: Array<Product>, id: number): number {
    let index = -1;
    if (0 === products.length || !products) {
      return index;
    }
    for (let i = 0; i < products.length; i++) {
      if (products[i].id === id) {
        index = i;
        break;
      }
    }
    return index;
  }
}
