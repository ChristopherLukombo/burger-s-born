import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../model/model.user';
import { ServicesDataService } from '../services/services-data.service';
import { NGXLogger } from 'ngx-logger';
import { environment } from '../../environments/environment';
import { AppConstants } from '../app.constants';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RoleName } from '../RoleName';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {DialogSuccessComponent} from "../dialog-success/dialog-success.component";
import {MatDialog} from "@angular/material";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

    user: User;
    registerForm: FormGroup;
    submitted = false;
    startDate = new Date(1950, 0, 1);

    selectedFiles: FileList;
    currentFileUpload: File;
    @Input() image: String;
    errorMessage: String;

    constructor(
        private servicesDataService: ServicesDataService,
        private logger: NGXLogger,
        private translateService: TranslateService,
        private router: Router,
        private formBuilder: FormBuilder,
        public dialog: MatDialog,
    ) { }

    private static checkExtension(file: File): boolean {
        if (!file) {
            return false;
        }

        const extensions = ['image/jpeg', 'image/png'];
        return -1 !== extensions.indexOf(file.type);
    }

    ngOnInit() {
        this.createForm();
    }

    private createForm() {
        this.registerForm = this.formBuilder.group({
            pseudo: ['', [Validators.required, Validators.maxLength(50)]],
            firstName: ['', [Validators.required, Validators.maxLength(50)]],
            lastName: ['', [Validators.required, Validators.maxLength(50)]],
            email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(100), Validators.email]],
            password: ['', [Validators.required, Validators.minLength(12), Validators.maxLength(20)]],
            imageUrl: ['', []],
            birthDay: ['', [Validators.required]],
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    public save(): void {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }

        this.setUser();

        if (!environment.production) {
            this.logger.debug(AppConstants.CALL_SERVICE, this.user);
        }

        if (this.selectedFiles) {
            this.currentFileUpload = this.selectedFiles.item(0);
        }

        if (this.currentFileUpload) {
            if (!RegisterComponent.checkExtension(this.currentFileUpload)) {
                this.errorMessage = "Fichier non valide";
            }
        }

        this.servicesDataService.save(this.user, this.translateService.currentLang)
            .subscribe(data => {

                if (this.currentFileUpload) {
                    const userId = getUserId(data);
                    this.upload(userId);
                }

                this.resetForm();
                this.errorMessage = undefined;
                this.openDialogSuccess();

                this.logger.info(AppConstants.USER_SAVED_SUCCESSFULLY);
            }, error => {
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
                    }


                }
                this.logger.error(AppConstants.USER_HASNT_BEEN_SAVED, error.message, error.status);
            });


        function getUserId(data) {
            let userId;
            if ('/api/users/' === data.headers.get('Location').slice(0, 11)) {
                userId = data.headers.get('Location').slice(11, data.headers.get('Location').length);
            }
            return userId;
        }
    }

    private resetForm() {
        this.registerForm.reset();
        for (let i in this.registerForm.controls) {
            this.registerForm.controls[i].setErrors(null);
        }
    }

    private setUser() {
        this.user = new User();
        this.user.pseudo = this.registerForm.get('pseudo').value;
        this.user.firstName = this.registerForm.get('firstName').value;
        this.user.lastName = this.registerForm.get('lastName').value;
        this.user.email = this.registerForm.get('email').value;
        this.user.password = this.registerForm.get('password').value;
        this.user.imageUrl = this.registerForm.get('imageUrl').value;
        this.user.birthDay = this.registerForm.get('birthDay').value;
        this.user.roleId = RoleName.ROLES[0].id;
        this.user.activated = true;
        this.user.createDate = new Date();
    }

    selectFile(event) {
        this.selectedFiles = event.target.files;
    }

    private upload(userId) {
        this.servicesDataService.uploadFile(this.currentFileUpload, userId)
            .subscribe((event) => {
                if (event instanceof HttpResponse) {
                    this.logger.debug('File is completely uploaded!');
                }
            });
        this.selectedFiles = undefined;
    }

    public openDialogSuccess(): void {
        this.dialog.open(
            DialogSuccessComponent,
            {
                width: '50%',
                height: '10%'
            }
        );
    }
}
