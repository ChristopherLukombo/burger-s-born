import { Component, OnInit } from '@angular/core';
import { Product } from "../../model/model.product";
import { ServicesDataService } from '../services/services-data.service';
import {HttpErrorResponse} from "@angular/common/http";

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

    constructor(private servicesDataService : ServicesDataService) { }

    ngOnInit() {
        this.findAll();
    }

    findAll() {

        this.servicesDataService.findAllProduct()
            .subscribe(data => {
                console.log(data);
            }, err => {
                if (err instanceof HttpErrorResponse) {
                    if (403 === err.status) {
                        //this.errorMessage = 'Login ou mot de passe incorrecte';
                    } else {
                        //this.errorMessage = 'Une erreur serveur s\'est produite';
                    }
                }
            });
    }

}
