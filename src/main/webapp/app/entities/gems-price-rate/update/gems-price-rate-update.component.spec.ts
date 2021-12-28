jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GemsPriceRateService } from '../service/gems-price-rate.service';
import { IGemsPriceRate, GemsPriceRate } from '../gems-price-rate.model';
import { IGemsItem } from 'app/entities/gems-item/gems-item.model';
import { GemsItemService } from 'app/entities/gems-item/service/gems-item.service';

import { GemsPriceRateUpdateComponent } from './gems-price-rate-update.component';

describe('Component Tests', () => {
  describe('GemsPriceRate Management Update Component', () => {
    let comp: GemsPriceRateUpdateComponent;
    let fixture: ComponentFixture<GemsPriceRateUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let gemsPriceRateService: GemsPriceRateService;
    let gemsItemService: GemsItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GemsPriceRateUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GemsPriceRateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GemsPriceRateUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      gemsPriceRateService = TestBed.inject(GemsPriceRateService);
      gemsItemService = TestBed.inject(GemsItemService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call GemsItem query and add missing value', () => {
        const gemsPriceRate: IGemsPriceRate = { id: 456 };
        const gemsItem: IGemsItem = { id: 25170 };
        gemsPriceRate.gemsItem = gemsItem;

        const gemsItemCollection: IGemsItem[] = [{ id: 63085 }];
        spyOn(gemsItemService, 'query').and.returnValue(of(new HttpResponse({ body: gemsItemCollection })));
        const additionalGemsItems = [gemsItem];
        const expectedCollection: IGemsItem[] = [...additionalGemsItems, ...gemsItemCollection];
        spyOn(gemsItemService, 'addGemsItemToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ gemsPriceRate });
        comp.ngOnInit();

        expect(gemsItemService.query).toHaveBeenCalled();
        expect(gemsItemService.addGemsItemToCollectionIfMissing).toHaveBeenCalledWith(gemsItemCollection, ...additionalGemsItems);
        expect(comp.gemsItemsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const gemsPriceRate: IGemsPriceRate = { id: 456 };
        const gemsItem: IGemsItem = { id: 27258 };
        gemsPriceRate.gemsItem = gemsItem;

        activatedRoute.data = of({ gemsPriceRate });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(gemsPriceRate));
        expect(comp.gemsItemsSharedCollection).toContain(gemsItem);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const gemsPriceRate = { id: 123 };
        spyOn(gemsPriceRateService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ gemsPriceRate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: gemsPriceRate }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(gemsPriceRateService.update).toHaveBeenCalledWith(gemsPriceRate);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const gemsPriceRate = new GemsPriceRate();
        spyOn(gemsPriceRateService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ gemsPriceRate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: gemsPriceRate }));
        saveSubject.complete();

        // THEN
        expect(gemsPriceRateService.create).toHaveBeenCalledWith(gemsPriceRate);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const gemsPriceRate = { id: 123 };
        spyOn(gemsPriceRateService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ gemsPriceRate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(gemsPriceRateService.update).toHaveBeenCalledWith(gemsPriceRate);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackGemsItemById', () => {
        it('Should return tracked GemsItem primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackGemsItemById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
