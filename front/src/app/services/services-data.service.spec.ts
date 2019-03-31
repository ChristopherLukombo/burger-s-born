import {TestBed} from '@angular/core/testing';

import {ServicesDataService} from './services-data.service';
import {HttpClientModule} from "@angular/common/http";

describe('ServicesDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      ServicesDataService
    ],
    imports: [
      HttpClientModule
    ],
  }));

  it('should be created', () => {
    const service: ServicesDataService = TestBed.get(ServicesDataService);
    expect(service).toBeTruthy();
  });
});
