import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoldItemGroupDetailComponent } from './gold-item-group-detail.component';

describe('Component Tests', () => {
  describe('GoldItemGroup Management Detail Component', () => {
    let comp: GoldItemGroupDetailComponent;
    let fixture: ComponentFixture<GoldItemGroupDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GoldItemGroupDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ goldItemGroup: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GoldItemGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoldItemGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goldItemGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goldItemGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
