import { HttpErrorResponse, HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NGXLogger } from 'ngx-logger';
import { environment } from '../../environments/environment';
import { AppConstants } from '../app.constants';
import { ServicesDataService } from '../services/services-data.service';


@Component({
    selector: 'app-importer',
    templateUrl: './importer.component.html',
    styleUrls: ['./importer.component.css']
})
export class ImporterComponent implements OnInit {

    importForm: FormGroup;
    submitted = false;

    selectedFiles: FileList;
    currentFileToUpload: File;

    selectedElementIsValid = false;
    selectedElement: string;
    selectedFormat: string;


    // Error messages
    errorMessage: string;
    successMessage: string;

    progress: { percentage: number } = { percentage: 0 };

    constructor(
        private logger: NGXLogger,
        private servicesDataService: ServicesDataService,
    ) { }

    ngOnInit() {
        this.importForm = new FormGroup({
            'elementSelector': new FormControl('produit'),
            'formatSelector': new FormControl('application/json'),
            'fileImport': new FormControl('', Validators.required)
        });
    }

    private checkExtension(file: File): boolean {
        if (!file) {
            return false;
        }
        const extensionsJSON = [
            'application/json',
        ];

        const extensionsCSV = [
            'text/csv',
            'application/vnd.ms-excel'
        ];

        if (this.importForm.get('formatSelector').value === 'text/csv') {
            if (extensionsCSV.indexOf(file.type) < 0) {
                return false;
            } else {
                return true;
            }
        } else {
            if (extensionsJSON.indexOf(file.type) >= 0) {
                return true;
            }
        }
    }

    selectFile(event: any): void {
        this.selectedFiles = event.target.files;
        this.progress.percentage = 0;

            this.currentFileToUpload = this.selectedFiles.item(0);
            this.logger.log('Current file information : \n'
                + 'fileName : ' + this.currentFileToUpload.name + '\n'
                + 'type : ' + this.currentFileToUpload.type);
    }

    public importFile(): void {
        this.successMessage = undefined;
        this.submitted = true;

        // Stop here if the form is invalid
        if (this.importForm.invalid) {
            this.errorMessage = 'Fichier requis';
            return;
        }

        if (this.currentFileToUpload) {
            if (!this.checkExtension(this.currentFileToUpload)) {
                this.errorMessage = 'Fichier non valide';
                return;
            }
        }

        if (!environment.production) {
            this.logger.debug(AppConstants.CALL_SERVICE_IMPORT, this.importForm.get('formatSelector').value);
        }

        const extension = new Map<string, string>();
        extension.set('text/csv', 'csv');
        extension.set('application/json', 'json');

        this.progress.percentage = 0;

        const elements = [
            'product',
            'menu'
        ];

        if (elements.indexOf(this.importForm.get('elementSelector').value) >= 0) {
            this.servicesDataService.importFile(
                this.currentFileToUpload,
                extension.get(this.importForm.get('formatSelector').value),
                this.importForm.get('elementSelector').value)
                .subscribe((event) => {
                    this.handleSuccessImport(event);
                }, error => {
                    this.logger.error(AppConstants.FILE_IMPORT_FAILED, error.message, error.status);
                    this.handleErrorImport(error);
                });
        }
        this.selectedFiles = undefined;
    }

    private handleSuccessImport(event: any): void {
        this.logger.debug(AppConstants.FILE_IMPORT_SUCCESS);
        if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
            this.importForm.reset();
            this.errorMessage = undefined;
            this.successMessage = event.statusText;
            this.logger.info('File is completely uploaded!');
        }
    }

    private handleErrorImport(error: any): void {
        this.importForm.reset();
        this.progress.percentage = 0;
        this.successMessage = undefined;
        if (error instanceof HttpErrorResponse) {
            if (422 === error.status) {
                Object.keys(error.error).forEach(prop => {
                    const formControl = this.importForm.get(prop);
                    if (formControl) {
                        formControl.setErrors({
                            serverError: error.error[prop]
                        });
                    }
                });
            } else if (400 === error.status) {
                this.errorMessage = error.error;
            } else if (500 === error.status) {
                this.errorMessage = error.error;
            }
        }
    }

}
