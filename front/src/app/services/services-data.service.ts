import { HttpClient, HttpHeaders, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Command } from '../../model/model.command';
import { User } from '../../model/model.user';
import { Paypal } from '../../model/model.paypal';
import { AuthProviderService } from './auth-provider.service';


@Injectable()
export class ServicesDataService {
    private resourceUrl = environment.serverUrl;

    constructor(private http: HttpClient, private authProviderService: AuthProviderService) { }


    // Register
    save(user: User, lang: string): Observable<HttpResponse<Object>> {
        const params = new HttpParams().set('lang', lang);
        // let headers = new HttpHeaders().set('Content-Type', 'application/json');
        // .set('Authorization', 'Bearer ' + this.authProviderService.getToken());
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

    importFile(file: File) {
        const formdata: FormData = new FormData();
        formdata.append('importfile', file);

        const req = new HttpRequest('POST', this.resourceUrl + '/product/import/json', formdata, {
            responseType: 'text',

        });

        return this.http.request(req);
    }

    getImageURL(pseudo: string) {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.get(this.resourceUrl + '/users/imageURL/' + `${pseudo}`, { headers, responseType: 'blob' });
    }

    // Product
    public findAllProduct(indexPage): Observable<HttpResponse<Object>> {
        return this.http.get<HttpResponse<Object>>(this.resourceUrl + '/product?page=' + indexPage + '&size=4', { observe: 'response' });
    }

    // Menu
    public findAllMenus(indexPage): Observable<HttpResponse<Object>> {
        return this.http.get<HttpResponse<Object>>(this.resourceUrl + '/menu/all?page=' + indexPage + '&size=4', { observe: 'response' });
    }

    public findAllProductBymenuId(categorieName: string, id: number): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.get<HttpResponse<Object>>(
            this.resourceUrl + '/menus/products/?categorieName=' + `${categorieName}` + '&id=' + `${id}`,
         {headers, observe: 'response' });
    }

    findProductsByCategoryName(categorieName: string): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken() });
        return this.http.get<HttpResponse<Object>>(
            this.resourceUrl + '/products/category?categorieName=' + `${categorieName}`,
         {headers, observe: 'response' });
    }

    createPayment(command: Command): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken()});
        return this.http.post<HttpResponse<Object>>(
            this.resourceUrl + '/make/payment', command,
         {headers, observe: 'response' });
    }

    completePayment(paypal: Paypal): Observable<HttpResponse<Object>> {
        const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.authProviderService.getToken()});
        return this.http.post<HttpResponse<Object>>(
            this.resourceUrl + '/complete/payment', paypal,
         {headers, observe: 'response' });
    }

}
