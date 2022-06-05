import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MortgageRedemptionDetailComponent } from './mortgage-redemption-detail.component';

describe('MortgageRedemptionDetailComponent', () => {
  let component: MortgageRedemptionDetailComponent;
  let fixture: ComponentFixture<MortgageRedemptionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MortgageRedemptionDetailComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MortgageRedemptionDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
