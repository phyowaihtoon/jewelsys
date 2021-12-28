import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CounterInfoDetailComponent } from './counter-info-detail.component';

describe('Component Tests', () => {
  describe('CounterInfo Management Detail Component', () => {
    let comp: CounterInfoDetailComponent;
    let fixture: ComponentFixture<CounterInfoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CounterInfoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ counterInfo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CounterInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CounterInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load counterInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.counterInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
