import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppConstants} from '../app.constants';

@Injectable()
export class ServicesDataService {
  private resourceUrl = AppConstants.SERVER_API_URL + '/api';

  constructor(private http: HttpClient) { }

  // Auth

}
