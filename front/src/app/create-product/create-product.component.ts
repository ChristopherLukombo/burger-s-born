import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit, Injector } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NGXLogger } from 'ngx-logger';
import { environment } from '../../environments/environment';
import { Product } from '../../model/model.product';
import { AppConstants } from '../app.constants';
import { DialogSuccessComponent } from '../dialog-success/dialog-success.component';
import { RoleName } from '../RoleName';
import { AuthProviderService } from '../services/auth-provider.service';
import { ServicesDataService } from '../services/services-data.service';

const WIDTH = '50%';
const HEIGHT = '15%';

@Component({
    selector: 'app-create-product',
    templateUrl: './create-product.component.html',
    styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {

    product: Product;
    registerForm: FormGroup;
    submitted = false;
    errorMessage: string;

    isAdmin: boolean;

    constructor(
        private servicesDataService: ServicesDataService,
        private logger: NGXLogger,
        // private translateService: TranslateService,
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
            name: ['', [Validators.required, Validators.maxLength(50)]],
            category: ['', [Validators.required, Validators.maxLength(50)]],
            price: ['', [Validators.required, Validators.maxLength(50)]]
        };
        if (this.authProviderService.isAuthenticated() &&
            this.authProviderService.isAdmin()) {
            const source = {
                role: ['', [Validators.required]]
            };
            this.registerForm = this.formBuilder.group(Object.assign(target, source));
        } else {
            this.registerForm = this.formBuilder.group(target);
        }
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    public createProduct(): void {

        const name = this.registerForm.controls.name.value;
        const category = this.registerForm.controls.category.value;
        const price = this.registerForm.controls.price.value;

        let product = new Product();

        product.name = name;
        product.categoryId = category;
        product.price = price;

        /*
        constructor(
            public id?: number,
            public available?: number,
            public name?: string,
            public price?: number,
            public categoryId?: number,
            public managerId?: number,
            public isSelected?: boolean
        ) {}*/

        

        this.servicesDataService.createProduct(product)
        .subscribe(data => {
            console.log(data);
            //this.logger.info(AppConstants.USER_SAVED_SUCCESSFULLY);
            this.handleSuccessRegister(getUserId, data);
        }, error => {
            console.log(error);
            //this.logger.error(AppConstants.USER_HASNT_BEEN_SAVED, error.message, error.status);
            this.handleErrorRegister(error);
        });

        
        this.submitted = true;
        // stop here if form is invalid
        /*if (this.registerForm.invalid) {
            return;
        }

        if (!environment.production) {
            this.logger.debug(AppConstants.CALL_SERVICE, this.user);
        }
        this.servicesDataService.save(this.user, this.injector.get(TranslateService).currentLang)
            .subscribe(data => {
                this.logger.info(AppConstants.USER_SAVED_SUCCESSFULLY);
                this.handleSuccessRegister(getUserId, data);
            }, error => {
                this.logger.error(AppConstants.USER_HASNT_BEEN_SAVED, error.message, error.status);
                this.handleErrorRegister(error);
            });*/

        function getUserId(data) {
            let userId: number;
            if ('/api/users/' === data.headers.get('Location').slice(0, 11)) {
                userId = data.headers.get('Location').slice(11, data.headers.get('Location').length);
            }
            return userId;
        }
    }

    private handleSuccessRegister(getUserId: (data: any) => number, data: HttpResponse<Object>) {
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
