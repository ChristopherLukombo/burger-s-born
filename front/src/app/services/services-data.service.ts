import { HttpClient, HttpHeaders, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { User } from '../../model/model.user';


@Injectable()
export class ServicesDataService {
  private resourceUrl = environment.serverUrl;

  constructor(private http: HttpClient) { }


  // Register
  save(user: User, lang: string): Observable<HttpResponse<Object>>  {
    const params = new HttpParams().set('lang', lang);
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
        // .set('Authorization', 'Bearer ' + this.authProviderService.getToken());

    return this.http.post<HttpResponse<Object>>(this.resourceUrl + '/register', user, {params, observe: 'response'});
  }


  uploadFile(file: File, userId: any) {
    const formdata: FormData = new FormData();
    formdata.append('file', file);

    const req = new HttpRequest('POST', this.resourceUrl +  '/register/file/' + `${userId}`, formdata, {
      responseType: 'text',

    });

    return this.http.request(req);
  }

}
