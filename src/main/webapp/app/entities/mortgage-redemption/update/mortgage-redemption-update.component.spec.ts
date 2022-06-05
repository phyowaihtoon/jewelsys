import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MortgageRedemptionUpdateComponent } from './mortgage-redemption-update.component';

describe('MortgageRedemptionUpdateComponent', () => {
  let component: MortgageRedemptionUpdateComponent;
  let fixture: ComponentFixture<MortgageRedemptionUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MortgageRedemptionUpdateComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MortgageRedemptionUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
