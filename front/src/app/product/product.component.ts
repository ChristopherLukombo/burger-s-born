import { Component, OnInit, Injector } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../model/model.product';
import { ServicesDataService } from '../services/services-data.service';
import { AuthProviderService } from '../services/auth-provider.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Category } from '../../model/model.category';

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

    errorMessage: string;
    successMessage: string;
    products;
    totalElements;
    pages;
    selectedPage = 0;
    categories: Array<Category>;
    updateForm: FormGroup;
    submitted = false;
    selectedProduct;
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

        this.loadCategories();
        this.createForm();
    }

    private createForm() {
        const target = {
            name: ['', [Validators.required, Validators.maxLength(50)]],
            category: ['', [Validators.required, Validators.maxLength(50)]],
            price: ['', [Validators.required]],
            available: ['', [Validators.required]]
        };
        this.updateForm = this.formBuilder.group(target);
    }

    initForm(product) {
        this.selectedProduct = product;

        this.updateForm.controls.name.setValue(this.selectedProduct.name);
        this.updateForm.controls.category.setValue(this.selectedProduct.categoryId);
        this.updateForm.controls.price.setValue(this.selectedProduct.price);
        this.updateForm.controls.available.setValue(this.selectedProduct.available);
    }

    // convenience getter for easy access to form fields
    get f() { return this.updateForm.controls; }

    findAll(indexPage: number) {
        this.servicesDataService.findAllProduct(indexPage)
            .subscribe(data => {

                this.products = data.body['content'];
                this.totalElements = data.body['totalElements'];
                this.pages = Array(data.body['totalPages']).fill(0).map((x, i) => i + 1);
                this.selectedPage = indexPage;

                for (let i = 0; i < this.products.length; i++) {
                    this.categories.forEach(categ => {
                        if (this.products[i].categoryId === categ.id) {
                            this.products[i].categoryName = categ.name;
                        }
                    });
                }

            }, err => {
                if (err instanceof HttpErrorResponse) {
                    if (403 === err.status) {
                        this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
                    } else if (404 === err.status) {
                        this.errorMessage = 'Aucun produit d\'enregistré.';
                        this.products = null;
                    } else {
                        this.errorMessage = 'Une erreur serveur s\'est produite.';
                    }
                }
                this.successMessage = null;
            });
    }

    delete(id: number) {
        this.servicesDataService.deleteProduct(id)
            .subscribe(data => {
                this.successMessage = 'Vous avez supprimé le produit avec succès.';
                this.errorMessage = null;

                this.findAll(this.selectedPage);

            }, err => {
                if (err instanceof HttpErrorResponse) {
                    if (403 === err.status) {
                        this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
                    } else if (401 === err.status) {
                        this.errorMessage = err.error;
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
        const category = this.updateForm.controls.category.value;
        const price = this.updateForm.controls.price.value;
        const available = this.updateForm.controls.available.value;

        if (this.updateForm.invalid) {
            return;
        }

        const product = new Product();

        product.id = this.selectedProduct.id;
        product.name = name;
        product.categoryId = category;
        product.price = price;
        product.available = available;
        product.managerId = 1;

        this.servicesDataService.updateProduct(product)
            .subscribe(data => {
                this.successMessage = 'Vous avez modifié le produit avec succès.';
                this.errorMessage = null;

                this.findAll(this.selectedPage);
                this.toUpdate = false;

            }, errorUpdate => {
                if (errorUpdate instanceof HttpErrorResponse) {
                    if (400 === errorUpdate.status) {
                        this.errorMessage = errorUpdate.error;
                    } else if (403 === errorUpdate.status) {
                        this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
                    } else if (401 === errorUpdate.status) {
                        this.errorMessage = errorUpdate.error;
                    } else {
                        this.errorMessage = 'Une erreur serveur s\'est produite.';
                    }
                }
                this.successMessage = null;
            });
    }

    public loadCategories(): void {
        this.servicesDataService.findAllCategory()
            .subscribe(data => {
                this.categories = data['body'] as Array<Category>;
                this.findAll(0);
            }, error => {
                if (error.status === 404) {
                    this.errorMessage = 'Aucune catégorie de produit enregistré.';
                }
            });
    }
}