import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../../model/model.user";
import {environment} from "../../environments/environment";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};

@Injectable()
export class ServicesDataService {
  private resourceUrl = environment.serverUrl;

  constructor(private http: HttpClient) { }

  // Register
  save(user: User) {
      return this.http.post<User>(this.resourceUrl + '/register', user, httpOptions);
  }

}
