jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GoldPriceRateService } from '../service/gold-price-rate.service';
import { IGoldPriceRate, GoldPriceRate } from '../gold-price-rate.model';
import { IGoldType } from 'app/entities/gold-type/gold-type.model';
import { GoldTypeService } from 'app/entities/gold-type/service/gold-type.service';

import { GoldPriceRateUpdateComponent } from './gold-price-rate-update.component';

describe('Component Tests', () => {
  describe('GoldPriceRate Management Update Component', () => {
    let comp: GoldPriceRateUpdateComponent;
    let fixture: ComponentFixture<GoldPriceRateUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let goldPriceRateService: GoldPriceRateService;
    let goldTypeService: GoldTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GoldPriceRateUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GoldPriceRateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoldPriceRateUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      goldPriceRateService = TestBed.inject(GoldPriceRateService);
      goldTypeService = TestBed.inject(GoldTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call GoldType query and add missing value', () => {
        const goldPriceRate: IGoldPriceRate = { id: 456 };
        const goldType: IGoldType = { id: 85209 };
        goldPriceRate.goldType = goldType;

        const goldTypeCollection: IGoldType[] = [{ id: 73660 }];
        spyOn(goldTypeService, 'query').and.returnValue(of(new HttpResponse({ body: goldTypeCollection })));
        const additionalGoldTypes = [goldType];
        const expectedCollection: IGoldType[] = [...additionalGoldTypes, ...goldTypeCollection];
        spyOn(goldTypeService, 'addGoldTypeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ goldPriceRate });
        comp.ngOnInit();

        expect(goldTypeService.query).toHaveBeenCalled();
        expect(goldTypeService.addGoldTypeToCollectionIfMissing).toHaveBeenCalledWith(goldTypeCollection, ...additionalGoldTypes);
        expect(comp.goldTypesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const goldPriceRate: IGoldPriceRate = { id: 456 };
        const goldType: IGoldType = { id: 33579 };
        goldPriceRate.goldType = goldType;

        activatedRoute.data = of({ goldPriceRate });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(goldPriceRate));
        expect(comp.goldTypesSharedCollection).toContain(goldType);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldPriceRate = { id: 123 };
        spyOn(goldPriceRateService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldPriceRate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: goldPriceRate }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(goldPriceRateService.update).toHaveBeenCalledWith(goldPriceRate);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldPriceRate = new GoldPriceRate();
        spyOn(goldPriceRateService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldPriceRate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: goldPriceRate }));
        saveSubject.complete();

        // THEN
        expect(goldPriceRateService.create).toHaveBeenCalledWith(goldPriceRate);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldPriceRate = { id: 123 };
        spyOn(goldPriceRateService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldPriceRate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(goldPriceRateService.update).toHaveBeenCalledWith(goldPriceRate);
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
