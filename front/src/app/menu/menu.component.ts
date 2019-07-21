import { HttpErrorResponse } from '@angular/common/http';
import { Component, Injector, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgModel, Validators } from '@angular/forms';
import 'rxjs/add/observable/forkJoin';
import { Product } from 'src/model/model.product';
import { Menu } from '../../model/model.menu';
import { AuthProviderService } from '../services/auth-provider.service';
import { ServicesDataService } from '../services/services-data.service';
import { Category } from 'src/model/model.category';

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
  updateForm: FormGroup;
  submitted = false;
  selectedMenu;
  toUpdate: boolean;
  isAdmin: boolean;
  products;
  categories;

  selectedProducts: any[];
  toCheck = false;
  checked = false;



  constructor(
    private servicesDataService: ServicesDataService,
    protected injector: Injector,
    public authProviderService: AuthProviderService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.authProviderService.admin.subscribe(value => {
      this.isAdmin = value;
    });
    this.isAdmin = this.isAdmin || this.authProviderService.isAdmin();
    this.createForm();
    this.loadCategories();
    this.findAll(0);
    this.getAllProducts();
  }

  private getAllProducts() {
    this.servicesDataService.getAllProduct()
      .subscribe(data => {
        this.products = data.body as Array<Product>;
      }, err => {
        this.products = [];
      });
  }

  private createForm() {
    const target = {
      name: ['', [Validators.required, Validators.maxLength(50)]],
      price: ['', [Validators.required]],
      available: ['', []]

    };
    this.updateForm = this.formBuilder.group(target);
  }

  initForm(menu) {
    this.selectedMenu = menu;
    this.updateForm.controls.name.setValue(this.selectedMenu.name);
    this.updateForm.controls.price.setValue(this.selectedMenu.price);
    this.updateForm.controls.available.setValue(this.selectedMenu.available);
  }

  get f() { return this.updateForm.controls; }


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
          } else if (404 === err.status) {
            this.errorMessage = 'Aucun menu d\'enregistré.';
            this.menus = null;
          } else {
            this.errorMessage = 'Une erreur serveur s\'est produite.';
          }
        }
        this.successMessage = null;
      });
  }

  public getSelectedProducts(menuId: number) {
    this.servicesDataService.getProductsByMenuId(menuId)
      .subscribe(data => {
        // Mapping categories with products
        for (let i = 0; i < this.products.length; i++) {
          this.categories.forEach(categ => {
            if (this.products[i].categoryId === categ.id) {
              this.products[i].categoryName = categ.name;
            }
          });
        }
        this.selectedProducts = data.body as Array<Product>;
        if (this.selectedProducts.length === this.products.length && this.selectedProducts.length > 0) {
          this.checked = true;
        } else {
          this.checked = false;
        }
      }, err => {
        this.selectedProducts = [];
        this.checked = false;
      });
  }

  equals(objOne, objTwo) {
    if (typeof objOne !== 'undefined' && typeof objTwo !== 'undefined') {
      return objOne.id === objTwo.id;
    }
  }

  selectAll(checkAll, select: NgModel, values) {
    if (checkAll) {
      this.toCheck = checkAll;
      select.update.emit(values);
    } else {
      select.update.emit([]);
    }
  }

  delete(id: number) {
    this.servicesDataService.deleteMenu(id)
      .subscribe(data => {
        this.successMessage = 'Vous avez supprimé le menu';
        this.errorMessage = null;
        this.findAll(this.selectedPage);
      }, err => {
        if (err instanceof HttpErrorResponse) {
          if (403 === err.status) {
            this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
          } else {
            this.errorMessage = 'Une erreur serveur s\'est produite.';
          }
        }
        this.successMessage = null;
      });
  }

  update() {
    this.errorMessage = null;
    this.submitted = true;

    const name = this.updateForm.controls.name.value;
    const price = this.updateForm.controls.price.value;
    const available = this.updateForm.controls.available.value;
    if (this.updateForm.invalid) {
      return;
    }

    const menu = new Menu();

    menu.id = this.selectedMenu.id;
    menu.name = name;
    menu.price = price;
    menu.available = available;
    menu.managerId = this.authProviderService.getIdManager();
    menu.productsDTO = this.selectedProducts;

    this.servicesDataService.updateMenu(menu)
      .subscribe(data => {
        this.successMessage = 'Vous avez modifié le menu avec succès.';
        this.errorMessage = null;

        this.findAll(this.selectedPage);
        this.toUpdate = false;

      }, errorUpdate => {
        if (errorUpdate instanceof HttpErrorResponse) {
          if (403 === errorUpdate.status) {
            this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
          } else {
            this.errorMessage = 'Une erreur serveur s\'est produite.';
          }
        }
        this.successMessage = null;
      });
  }
  
  private loadCategories(): void {
    this.servicesDataService.findAllCategory()
      .subscribe(data => {
        this.categories = data['body'] as Array<Category>;
      }, error => {
        if (error.status === 404) {
          this.errorMessage = 'Aucune catégorie de produit enregistré.';
        }
      });
  }
}
