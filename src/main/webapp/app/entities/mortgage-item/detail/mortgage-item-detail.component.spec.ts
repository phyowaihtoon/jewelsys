import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MortgageItemDetailComponent } from './mortgage-item-detail.component';

describe('Component Tests', () => {
  describe('MortgageItem Management Detail Component', () => {
    let comp: MortgageItemDetailComponent;
    let fixture: ComponentFixture<MortgageItemDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MortgageItemDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ mortgageItem: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MortgageItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MortgageItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mortgageItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mortgageItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
