import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "../../model/model.user";
import {environment} from "../../environments/environment";

const headers = new HttpHeaders({
  'Content-Type': 'application/json'
});

@Injectable()
export class ServicesDataService {
  private resourceUrl = environment.serverUrl;

  constructor(private http: HttpClient) { }

  // Register
  save(user: User, lang: string) {
    const params = new HttpParams().set('lang', lang);
    return this.http.post<User>(this.resourceUrl + '/register', user, {headers, params});
  }

}
