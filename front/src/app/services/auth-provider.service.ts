import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable, BehaviorSubject } from 'rxjs';
import { environment } from '../../environments/environment';
import { Login } from '../../model/model.login';
import { RoleName } from '../RoleName';

@Injectable({
  providedIn: 'root'
})
export class AuthProviderService {
  private resourceUrl = environment.serverUrl;
  public admin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public imageBlobUrl: BehaviorSubject<string | ArrayBuffer | null> = new BehaviorSubject<string>('');

  constructor(
      private http: HttpClient,
      private $localStorage: LocalStorageService,
      private jwtHelper: JwtHelperService,
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
      this.admin.next(false);
      this.imageBlobUrl.next('');
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
    if (!decodedToken) {
      return false;
    }
    return RoleName.ROLE_ADMIN === decodedToken.auth;
  }

  getPseudo(): string {
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.getToken());
    if (!decodedToken) {
      return null;
    }
    return decodedToken.sub;
  }
}
