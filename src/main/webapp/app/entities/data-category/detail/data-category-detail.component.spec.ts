import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataCategoryDetailComponent } from './data-category-detail.component';

describe('Component Tests', () => {
  describe('DataCategory Management Detail Component', () => {
    let comp: DataCategoryDetailComponent;
    let fixture: ComponentFixture<DataCategoryDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DataCategoryDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dataCategory: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DataCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dataCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
