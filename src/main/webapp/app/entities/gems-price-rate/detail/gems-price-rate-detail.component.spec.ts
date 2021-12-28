import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GemsPriceRateDetailComponent } from './gems-price-rate-detail.component';

describe('Component Tests', () => {
  describe('GemsPriceRate Management Detail Component', () => {
    let comp: GemsPriceRateDetailComponent;
    let fixture: ComponentFixture<GemsPriceRateDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GemsPriceRateDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ gemsPriceRate: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GemsPriceRateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GemsPriceRateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gemsPriceRate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gemsPriceRate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
