import { HttpClient, HttpHeaders, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Command } from '../../model/model.command';
import { User } from '../../model/model.user';
import { Paypal } from '../../model/model.paypal';
import { AuthProviderService } from './auth-provider.service';
import {Menu} from '../../model/model.menu';
import { Product } from '../../model/model.product';


@Injectable()
export class ServicesDataService {
    private resourceUrl = environment.serverUrl;

    constructor(private http: HttpClient, private authProviderService: AuthProviderService) { }


    /******************************REGISTRER**********************************************************/
    save(user: User, lang: string): Observable<HttpResponse<Object>> {
        const params = new HttpParams().set('lang', lang);
        return this.http.post<HttpResponse<Object>>(this.resourceUrl + '/register', user, { params, observe: 'response' });
    }

    uploadFile(file: File, userId: number) {
        const formdata: FormData = new FormData();
        formdata.append('file', file);

        const req = new HttpRequest('POST', this.resourceUrl + '/register/file/' + `${userId}`, formdata, {
            responseType: 'text',

        });

        return this.http.request(req);
    }

    importFile(file: File, fileFomat: string, element: string) {
        const formdata: FormData = new FormData();
        formdata.append('importfile', file);
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });

        const req = new HttpRequest('POST', this.resourceUrl + '/' + `${element}` + '/import?fileFormat=' + `${fileFomat}`, formdata, {
            headers: headers,
            reportProgress: true,
            responseType: 'text',
        });

        return this.http.request(req);
    }

    getImageURL(pseudo: string) {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.get(this.resourceUrl + '/users/imageURL/' + `${pseudo}`, { headers, responseType: 'blob' });
    }

    /***************************************Category**********************************************************/
    public findAllCategory(): Observable<HttpResponse<Object>> {
        return this.http.get<HttpResponse<Object>>(this.resourceUrl + '/category', { observe: 'response' });
    }

    /***************************************Product**********************************************************/
    public findAllProduct(indexPage: number): Observable<HttpResponse<Object>> {
        return this.http.get<HttpResponse<Object>>(this.resourceUrl + '/products?page=' + indexPage + '&size=4', { observe: 'response' });
    }

    public createProduct(product: Product): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.post<HttpResponse<Object>>(this.resourceUrl + '/new/product', product, { headers, observe: 'response' });
    }

    public deleteProduct(id: number): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.delete<HttpResponse<Object>>(this.resourceUrl + '/delete/product/' + id, { headers, observe: 'response' });
    }

    public updateProduct(product: Product): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.put<HttpResponse<Object>>(this.resourceUrl + '/product', product, { headers, observe: 'response' });
    }

    /*****************************************MENU**********************************************************/
    public findAllMenus(indexPage: number): Observable<HttpResponse<Object>> {
        return this.http.get<HttpResponse<Object>>(this.resourceUrl + '/menu/all?page=' + indexPage + '&size=4', { observe: 'response' });
    }

    public findAllProductBymenuId(categorieName: string, id: number): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.get<HttpResponse<Object>>(
            this.resourceUrl + '/menus/products/?categorieName=' + `${categorieName}` + '&id=' + `${id}`,
         {headers, observe: 'response' });
    }

    public registerMenu(menu: Menu): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
       return this.http.post<HttpResponse<Object>>(this.resourceUrl + '/new/menu', menu, { headers, observe: 'response' });

    }

    public updateMenu(menu: Menu): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.put<HttpResponse<Object>>(this.resourceUrl + '/menu', menu, { headers, observe: 'response' });
    }

    public deleteMenu(id: number): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.delete<HttpResponse<Object>>(this.resourceUrl + '/delete/menu/'+id, { headers, observe: 'response' });
    }

    findProductsByCategoryName(indexPage: number, categorieName: string): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.get<HttpResponse<Object>>(
            this.resourceUrl + '/products/category?page=' + `${indexPage}` + '&size=4&categorieName=' + `${categorieName}`,
         {headers, observe: 'response' });
    }

    getAllCommands(indexPage: number, customerId: number): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.get<HttpResponse<Object>>(
            this.resourceUrl + '/commands?page=' + indexPage + '&size=4' + '&customerId=' + `${customerId}`,
            { headers, observe: 'response' });
    }

    createPayment(command: Command): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.post<HttpResponse<Object>>(
            this.resourceUrl + '/make/payment', command,
            { headers, observe: 'response' });
    }

    completePayment(paypal: Paypal): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.post<HttpResponse<Object>>(
            this.resourceUrl + '/complete/payment', paypal,
            { headers, observe: 'response' });
    }

    cancelPayment(commandId: number): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.post<HttpResponse<Object>>(
            this.resourceUrl + '/refund/payment/' + `${commandId}`, null,
            { headers, observe: 'response' });
    }

    getAllTrendsMenus(): Observable<HttpResponse<Object>> {
        return this.http.get<HttpResponse<Object>>(
            this.resourceUrl + '/menus/trends',
            { observe: 'response' });
    }

}
