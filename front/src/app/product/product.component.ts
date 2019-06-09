import { Component, OnInit } from '@angular/core';
import { Product } from "../../model/model.product";
import { ServicesDataService } from '../services/services-data.service';
import { HttpErrorResponse } from "@angular/common/http";

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

    errorMessage;
    successMessage;
    products;
    totalElements;
    pages;
    selectedPage = 0;

    constructor(private servicesDataService: ServicesDataService) { }

    ngOnInit() {
        this.findAll(0);
    }

    findAll(indexPage) {
        this.servicesDataService.findAllProduct(indexPage)
            .subscribe(data => {

                this.products = data.body['content'];
                this.totalElements = data.body['totalElements'];
                this.pages = Array(data.body['totalPages']).fill(0).map((x, i) => i+1);
                this.selectedPage = indexPage;

            }, err => {
                if (err instanceof HttpErrorResponse) {
                    if (403 === err.status) {
                        this.errorMessage = 'Vous n\'êtes pas autorisé à effectuer cette action.';
                    } else {
                        this.errorMessage = 'Une erreur serveur s\'est produite.';
                    }
                }
            });
    }

}
