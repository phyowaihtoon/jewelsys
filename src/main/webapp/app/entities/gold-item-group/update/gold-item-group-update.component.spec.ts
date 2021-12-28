jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GoldItemGroupService } from '../service/gold-item-group.service';
import { IGoldItemGroup, GoldItemGroup } from '../gold-item-group.model';
import { IGoldType } from 'app/entities/gold-type/gold-type.model';
import { GoldTypeService } from 'app/entities/gold-type/service/gold-type.service';

import { GoldItemGroupUpdateComponent } from './gold-item-group-update.component';

describe('Component Tests', () => {
  describe('GoldItemGroup Management Update Component', () => {
    let comp: GoldItemGroupUpdateComponent;
    let fixture: ComponentFixture<GoldItemGroupUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let goldItemGroupService: GoldItemGroupService;
    let goldTypeService: GoldTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GoldItemGroupUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GoldItemGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoldItemGroupUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      goldItemGroupService = TestBed.inject(GoldItemGroupService);
      goldTypeService = TestBed.inject(GoldTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call GoldType query and add missing value', () => {
        const goldItemGroup: IGoldItemGroup = { id: 456 };
        const goldType: IGoldType = { id: 86801 };
        goldItemGroup.goldType = goldType;

        const goldTypeCollection: IGoldType[] = [{ id: 98253 }];
        spyOn(goldTypeService, 'query').and.returnValue(of(new HttpResponse({ body: goldTypeCollection })));
        const additionalGoldTypes = [goldType];
        const expectedCollection: IGoldType[] = [...additionalGoldTypes, ...goldTypeCollection];
        spyOn(goldTypeService, 'addGoldTypeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ goldItemGroup });
        comp.ngOnInit();

        expect(goldTypeService.query).toHaveBeenCalled();
        expect(goldTypeService.addGoldTypeToCollectionIfMissing).toHaveBeenCalledWith(goldTypeCollection, ...additionalGoldTypes);
        expect(comp.goldTypesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const goldItemGroup: IGoldItemGroup = { id: 456 };
        const goldType: IGoldType = { id: 5181 };
        goldItemGroup.goldType = goldType;

        activatedRoute.data = of({ goldItemGroup });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(goldItemGroup));
        expect(comp.goldTypesSharedCollection).toContain(goldType);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldItemGroup = { id: 123 };
        spyOn(goldItemGroupService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldItemGroup });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: goldItemGroup }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(goldItemGroupService.update).toHaveBeenCalledWith(goldItemGroup);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldItemGroup = new GoldItemGroup();
        spyOn(goldItemGroupService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldItemGroup });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: goldItemGroup }));
        saveSubject.complete();

        // THEN
        expect(goldItemGroupService.create).toHaveBeenCalledWith(goldItemGroup);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldItemGroup = { id: 123 };
        spyOn(goldItemGroupService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldItemGroup });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(goldItemGroupService.update).toHaveBeenCalledWith(goldItemGroup);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackGoldTypeById', () => {
        it('Should return tracked GoldType primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackGoldTypeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
