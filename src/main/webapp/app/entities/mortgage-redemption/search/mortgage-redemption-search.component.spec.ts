import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MortgageRedemptionSearchComponent } from './mortgage-redemption-search.component';

describe('MortgageRedemptionSearchComponent', () => {
  let component: MortgageRedemptionSearchComponent;
  let fixture: ComponentFixture<MortgageRedemptionSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MortgageRedemptionSearchComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MortgageRedemptionSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
