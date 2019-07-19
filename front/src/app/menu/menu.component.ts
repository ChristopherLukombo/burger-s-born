import { Component, OnInit, Injector } from '@angular/core';
import {ServicesDataService} from '../services/services-data.service';
import {HttpErrorResponse} from '@angular/common/http';
import { Menu } from '../../model/model.menu';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  submitted = false;
  selectedMenu;
  toUpdate: boolean;
  isAdmin: boolean;

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
    this.findAll(0);
  }

private createForm(){
  const target = {
    name: ['', [Validators.required, Validators.maxLength(50)]],
    price: ['', [Validators.required]]

  };
  this.updateForm = this.formBuilder.group(target);

}

  initForm(menu){
    this.selectedMenu= menu;
    this.updateForm.controls.name.setValue(this.selectedMenu.name);
    this.updateForm.controls.price.setValue(this.selectedMenu.price);
   }

   get f() { return this.updateForm.controls; }


  findAll(indexPage: number) {
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
            } else if(404 === err.status) {
              this.errorMessage = 'Aucun menu d\'enregistré.';
              this.menus = null;
            }else{
              this.errorMessage = 'Une erreur serveur s\'est produite.';
            }
          }
          this.successMessage = null;
        });
  }


  delete(id: number){

    this.servicesDataService.deleteMenu(id)
      .subscribe(data => {
        this.successMessage = 'Vous avez supprimé le menu'
        this.errorMessage = null;

        this.findAll(this.selectedPage);
      },err => {
        if(err instanceof HttpErrorResponse){
          if(403 === err.status){
            this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
          }else{
            this.errorMessage = 'Une erreur serveur s\'est produite.';
          }
        }
        this.successMessage = null;

      });

  }


  update(){
    this.errorMessage = null;
    this.submitted = true;

    const name = this.updateForm.controls.name.value;
    const price = this.updateForm.controls.price.value;
    if (this.updateForm.invalid) {
        return;
    }

    const menu = new Menu();

    menu.id = this.selectedMenu.id;
    menu.name = name;
    menu.price = price;
    menu.managerId = this.authProviderService.getIdManager();


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
  }
