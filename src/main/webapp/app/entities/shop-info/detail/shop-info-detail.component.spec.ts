import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShopInfoDetailComponent } from './shop-info-detail.component';

describe('Component Tests', () => {
  describe('ShopInfo Management Detail Component', () => {
    let comp: ShopInfoDetailComponent;
    let fixture: ComponentFixture<ShopInfoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ShopInfoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ shopInfo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ShopInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShopInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shopInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shopInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
