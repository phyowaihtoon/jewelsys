import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoldPriceRateDetailComponent } from './gold-price-rate-detail.component';

describe('Component Tests', () => {
  describe('GoldPriceRate Management Detail Component', () => {
    let comp: GoldPriceRateDetailComponent;
    let fixture: ComponentFixture<GoldPriceRateDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GoldPriceRateDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ goldPriceRate: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GoldPriceRateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoldPriceRateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goldPriceRate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goldPriceRate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
