import { HttpErrorResponse } from '@angular/common/http';
import { Component, Injector, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { NGXLogger } from 'ngx-logger';
import { environment } from '../../environments/environment';
import { Category } from '../../model/model.category';
import { Product } from '../../model/model.product';
import { AppConstants } from '../app.constants';
import { DialogSuccessComponent } from '../dialog-success/dialog-success.component';
import { AuthProviderService } from '../services/auth-provider.service';
import { ServicesDataService } from '../services/services-data.service';
import {Menu} from '../../model/model.menu';


const WIDTH = '50%';
const HEIGHT = '15%';

@Component({
  selector: 'app-create-menu',
  templateUrl: './create-menu.component.html',
  styleUrls: ['./create-menu.component.css']
})
export class CreateMenuComponent implements OnInit {
   menu: Menu;
   registerForm: FormGroup;
   submitted = false;
   errorMessage: string;

   isAdmin: boolean


  constructor(
      private servicesDataService: ServicesDataService,
      private logger: NGXLogger,
      protected injector: Injector,
      public authProviderService: AuthProviderService,
      private formBuilder: FormBuilder,
      public dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.createForm();
    this.isAdmin = this.authProviderService.isAdmin();
  }

   private createForm() {

     const target = {
       name: ['' , [Validators.required]],
       price: ['' , [Validators.required]],
     };

     this.registerForm = this.formBuilder.group(target);
   }

   get f() { return this.registerForm.controls; }

  public createMenu(): void {

    this.errorMessage = null;

    if (!this.isAdmin || !this.authProviderService.isAuthenticated()) {
        this.errorMessage = 'Vous devez être connecté avec un compte administrateur.';
        return;
    }

    this.errorMessage = null;
    this.submitted = true;

    if (this.registerForm.invalid) {
        return;
    }

    const menu = new Menu();
    menu.name = this.registerForm.controls.name.value;
    menu.price = this.registerForm.controls.price.value;
    menu.managerId = this.authProviderService.getIdManager();

    if (!environment.production) {
        this.logger.debug(AppConstants.CALL_SERVICE, menu);
    }


    this.servicesDataService.registerMenu(menu)
         .subscribe(data => {
            this.handleSuccessRegister();
            }, error => {
           this.handleErrorRegister(error);
         });
   }

   private handleSuccessRegister() {
       this.resetForm();
       this.errorMessage = undefined;
       this.openDialogSuccess();
   }

   private handleErrorRegister(error: any) {
       if (error instanceof HttpErrorResponse) {
           if (422 === error.status) {
               Object.keys(error.error).forEach(prop => {
                   const formControl = this.registerForm.get(prop);
                   if (formControl) {
                       formControl.setErrors({
                           serverError: error.error[prop]
                       });
                   }
               });
           } else if (400 === error.status) {
               this.errorMessage = error.error;
           } else if (500 === error.status) {
               this.errorMessage = 'Une erreur serveur s\'est produite';
           } else if (error.status === 403) {
               this.errorMessage = 'Vous devez être connecté pour ajouter un produit.';
           }
       }
   }

   private resetForm() {
       this.registerForm.reset();
       for (const key in this.registerForm.controls) {
           if (!this.registerForm.controls[key]) {
               continue;
           }
           this.registerForm.controls[key].setErrors(null);
       }
   }

   public openDialogSuccess(): void {
       this.dialog.open(
           DialogSuccessComponent,
           {
               width: WIDTH,
               height: HEIGHT
           }
       );
   }
}
