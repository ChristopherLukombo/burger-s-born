import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {LocalStorageService} from 'ngx-webstorage';
import {Login} from '../../model/model.login';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { RoleName } from '../RoleName';

@Injectable({
  providedIn: 'root'
})
export class AuthProviderService {
  private resourceUrl = environment.serverUrl;

  constructor(
      private http: HttpClient,
      private $localStorage: LocalStorageService,
      private jwtHelper: JwtHelperService
  ) {}

  authenticate(login: Login): Observable<HttpResponse<Object>> {
    return this.http.post<HttpResponse<Object>>(this.resourceUrl + '/authenticate', login, {observe: 'response'});
  }

  storeAuthenticationToken(jwt) {
    this.$localStorage.store('authenticationToken', jwt);
  }

  getToken() {
    return this.$localStorage.retrieve('authenticationToken');
  }

  logout(): Observable<any> {
    return new Observable((observer) => {
      this.$localStorage.clear('authenticationToken');
      observer.complete();
    });
  }

  isAuthenticated() {
      const token = this.getToken();

      // Check whether the token is expired and return
      // true or false
      return !this.jwtHelper.isTokenExpired(token);
  }

  isAdmin(): boolean {
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.getToken());
    if (null === decodedToken) {
      return false;
    }
    return RoleName.ROLE_ADMIN === decodedToken.auth;
  }
}
