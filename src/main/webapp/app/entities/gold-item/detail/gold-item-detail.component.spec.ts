import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoldItemDetailComponent } from './gold-item-detail.component';

describe('Component Tests', () => {
  describe('GoldItem Management Detail Component', () => {
    let comp: GoldItemDetailComponent;
    let fixture: ComponentFixture<GoldItemDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GoldItemDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ goldItem: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GoldItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoldItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goldItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goldItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
