jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DataCategoryService } from '../service/data-category.service';
import { IDataCategory, DataCategory } from '../data-category.model';

import { DataCategoryUpdateComponent } from './data-category-update.component';

describe('Component Tests', () => {
  describe('DataCategory Management Update Component', () => {
    let comp: DataCategoryUpdateComponent;
    let fixture: ComponentFixture<DataCategoryUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dataCategoryService: DataCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DataCategoryUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DataCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataCategoryUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dataCategoryService = TestBed.inject(DataCategoryService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dataCategory: IDataCategory = { id: 456 };

        activatedRoute.data = of({ dataCategory });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dataCategory));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dataCategory = { id: 123 };
        spyOn(dataCategoryService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dataCategory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dataCategory }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dataCategoryService.update).toHaveBeenCalledWith(dataCategory);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dataCategory = new DataCategory();
        spyOn(dataCategoryService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dataCategory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dataCategory }));
        saveSubject.complete();

        // THEN
        expect(dataCategoryService.create).toHaveBeenCalledWith(dataCategory);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dataCategory = { id: 123 };
        spyOn(dataCategoryService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dataCategory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dataCategoryService.update).toHaveBeenCalledWith(dataCategory);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
