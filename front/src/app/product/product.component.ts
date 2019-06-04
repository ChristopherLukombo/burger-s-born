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

    constructor(private servicesDataService: ServicesDataService) { }

    ngOnInit() {
        this.findAll();
    }

    findAll() {

        this.servicesDataService.findAllProduct()
            .subscribe(data => {
                this.products = data.body['content'];
                console.log(this.products);
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
