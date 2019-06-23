import {Component, Injector, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Menu} from '../../model/model.menu';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ServicesDataService} from '../services/services-data.service';
import {environment} from '../../environments/environment';
import {AppConstants} from '../app.constants';
import {NGXLogger} from 'ngx-logger';
import {TranslateService} from '@ngx-translate/core';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-create-menu',
  templateUrl: './create-menu.component.html',
  styleUrls: ['./create-menu.component.css']
})
export class CreateMenuComponent implements OnInit {
// FIXME : rendre le formulaire fonctionnel, car les tests unitaires sont KO.
  // menu: Menu;
  // createMenuForm: FormGroup;
  // isSubmit = false;
  // errorMessage: string;


  constructor(
      private servicesDataService: ServicesDataService,
      private router: Router,
      private formBuilder: FormBuilder,
      private logger: NGXLogger,
      protected injector: Injector,
  ) { }

  ngOnInit() {
  }

  // private createForm() {

  //   const target = {
  //     name: ['' , [Validators.required, Validators.maxLength(40)]],
  //     price: ['' , [Validators.required]],
  //   };

  //   this.createMenuForm = this.formBuilder.group(target);
  // }


  // public registerNewMenu(): void {

  //   this.isSubmit = true;

  //   if (this.createMenuForm.invalid) {
  //     return;
  //   }

  //   this.menuFormToMenuBean();

  //   if (!environment.production) {
  //     this.logger.debug(AppConstants.CALL_SERVICE, this.menu);
  //   }

  //   this.servicesDataService.registerMenu(this.menu, '1', this.injector.get(TranslateService).currentLang)
  //       .subscribe(data => {
  //         this.logger.info(AppConstants.MENU_SAVE_SUCCESSFULLY);
  //       }, error => {
  //         this.logger.error(AppConstants.MENU_HASNT_BEEN_SAVED, error.message, error.status);
  //         this.handleErrorRegister(error);

  //       });

  // }

  // // convenience getter for easy access to form fields
  // get getField() { return this.createMenuForm.controls; }


  // private menuFormToMenuBean() {
  //   this.menu = new Menu();
  //   this.menu.price = this.createMenuForm.get('price').value;
  //   this.menu.name = this.createMenuForm.get('name').value;
  // }


  // public onNavigateToMenu() {
  //  this.router.navigate(['menu']);

  // }

  // private handleErrorRegister(error: any) {
  //   if (error instanceof HttpErrorResponse) {
  //     if (422 === error.status) {
  //       Object.keys(error.error).forEach(prop => {
  //         const formControl = this.createMenuForm.get(prop);
  //         if (formControl) {
  //             formControl.setErrors({
  //             serverError: error.error[prop]
  //           });
  //         }
  //       });
  //     } else if (400 === error.status) {
  //       this.errorMessage = error.error;
  //     } else if (500 === error.status) {
  //       this.errorMessage = 'Une erreur serveur s\'est produite';
  //     }
  //   }
  // }
}
