import { TestBed } from '@angular/core/testing';

import { ShatappApiService } from './shatapp-api.service';

describe('ShatappApiService', () => {
  let service: ShatappApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShatappApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
