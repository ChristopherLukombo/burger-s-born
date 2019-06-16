import {Component, Injector, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {environment} from '../../environments/environment';
import {NGXLogger} from 'ngx-logger';
import {AppConstants} from '../app.constants';
import {ServicesDataService} from '../services/services-data.service';
import {TranslateService} from '@ngx-translate/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
// import {errorObject} from 'rxjs/internal-compatibility';

@Component({
    selector: 'app-importer',
    templateUrl: './importer.component.html',
    styleUrls: ['./importer.component.css']
})
export class ImporterComponent implements OnInit {

    // importForm: FormGroup;
    // submitted = false;

    // selectedFiles: FileList;
    // currentFileToUpload: File;

    // selectedElementIsValid = false;
    // selectedElement: string;
    // selectedFormat: string;


    // // Error messages
    // errorMessage: string;
    // successMessage : string;

    constructor(
    //     private logger: NGXLogger,
    //     private servicesDataService: ServicesDataService
    ) { }

    ngOnInit() {
    //     this.importForm = new FormGroup({
    //         'elementSelector': new FormControl('produit'),
    //         'formatSelector' : new FormControl('application/json'),
    //         'fileImport': new FormControl('', Validators.required)
    //     })
    }

    // private static checkExtension(file: File): boolean {
    //     if (!file) {
    //         return false;
    //     }
    //     const extensions = [
    //         'application/json',
    //         'application/vnd.ms-excel',
    //         'text/csv'
    //     ];
    //     return -1 !== extensions.indexOf(file.type);
    // }

    // selectFile(event): void {
    //     document.getElementById("custom-file-label").innerText = event.target.files[0].name;
    //     this.selectedFiles = event.target.files;
    //     if(this.selectedFiles) {
    //         this.currentFileToUpload = this.selectedFiles.item(0);
    //         console.log("Current file information : \n"
    //         + "fileName : " + this.currentFileToUpload.name + "\n"
    //         + "type : " + this.currentFileToUpload.type);
    //     }
    // }

    // public importFile(): void {
    //     this.submitted = true;

    //     // Stop here if the form is invalid
    //     if (this.importForm.invalid) {
    //         this.errorMessage = 'Fichier requis';
    //         return;
    //     }

    //     if (this.currentFileToUpload) {
    //         if (!ImporterComponent.checkExtension(this.currentFileToUpload)) {
    //             this.errorMessage = 'Fichier non valide';
    //             return;
    //         }
    //     }

    //     if (environment.production) {
    //         this.logger.debug(AppConstants.CALL_SERVICE_IMPORT, this.importForm.get('formatSelector').value);
    //     }

    //     this.servicesDataService.importFile(this.currentFileToUpload)
    //         .subscribe((event) => {
    //             if (event instanceof HttpResponse) {
    //                 this.logger.debug(AppConstants.FILE_IMPORT_SUCCESS);
    //                 this.handleSuccessImport(event);
    //             }
    //         }, error => {
    //             this.logger.info(AppConstants.FILE_IMPORT_FAILED, error.message, error.status);
    //             this.handleErrorImport(error);
    //         });

    //     this.selectedFiles = undefined;
    // }

    // private handleSuccessImport(event: HttpResponse<Object>) {
    //     this.importForm.reset();
    //     this.errorMessage = undefined;
    //     this.successMessage = event.statusText;
    // }

    // private handleErrorImport(error: any) {
    //     if (error instanceof HttpErrorResponse) {
    //         if (422 === error.status) {
    //             Object.keys(error.error).forEach(prop => {
    //                 const formControl = this.importForm.get(prop);
    //                 if (formControl) {
    //                     formControl.setErrors({
    //                         serverError: error.error[prop]
    //                     });
    //                 }
    //             });
    //         } else if (400 === error.status) {
    //             this.errorMessage = error.error;
    //         } else if (500 === error.status) {
    //             this.errorMessage = error.error;
    //         }
    //     }
    // }

}
