import { TestBed } from '@angular/core/testing';

import { MortgageRedemptionService } from './mortgage-redemption.service';

describe('MortgageRedemptionService', () => {
  let service: MortgageRedemptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MortgageRedemptionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
