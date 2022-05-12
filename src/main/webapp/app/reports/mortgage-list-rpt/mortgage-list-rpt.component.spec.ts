import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MortgageListRptComponent } from './mortgage-list-rpt.component';

describe('MortgageListRptComponent', () => {
  let component: MortgageListRptComponent;
  let fixture: ComponentFixture<MortgageListRptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MortgageListRptComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MortgageListRptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
