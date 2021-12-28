import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MortgageEntryDetailComponent } from './mortgage-entry-detail.component';

describe('Component Tests', () => {
  describe('MortgageEntry Management Detail Component', () => {
    let comp: MortgageEntryDetailComponent;
    let fixture: ComponentFixture<MortgageEntryDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MortgageEntryDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ mortgageEntry: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MortgageEntryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MortgageEntryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mortgageEntry on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mortgageEntry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
