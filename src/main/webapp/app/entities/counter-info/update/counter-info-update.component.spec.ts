jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CounterInfoService } from '../service/counter-info.service';
import { ICounterInfo, CounterInfo } from '../counter-info.model';
import { IShopInfo } from 'app/entities/shop-info/shop-info.model';
import { ShopInfoService } from 'app/entities/shop-info/service/shop-info.service';

import { CounterInfoUpdateComponent } from './counter-info-update.component';

describe('Component Tests', () => {
  describe('CounterInfo Management Update Component', () => {
    let comp: CounterInfoUpdateComponent;
    let fixture: ComponentFixture<CounterInfoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let counterInfoService: CounterInfoService;
    let shopInfoService: ShopInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CounterInfoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CounterInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CounterInfoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      counterInfoService = TestBed.inject(CounterInfoService);
      shopInfoService = TestBed.inject(ShopInfoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ShopInfo query and add missing value', () => {
        const counterInfo: ICounterInfo = { id: 456 };
        const shopInfo: IShopInfo = { id: 56217 };
        counterInfo.shopInfo = shopInfo;

        const shopInfoCollection: IShopInfo[] = [{ id: 61538 }];
        spyOn(shopInfoService, 'query').and.returnValue(of(new HttpResponse({ body: shopInfoCollection })));
        const additionalShopInfos = [shopInfo];
        const expectedCollection: IShopInfo[] = [...additionalShopInfos, ...shopInfoCollection];
        spyOn(shopInfoService, 'addShopInfoToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ counterInfo });
        comp.ngOnInit();

        expect(shopInfoService.query).toHaveBeenCalled();
        expect(shopInfoService.addShopInfoToCollectionIfMissing).toHaveBeenCalledWith(shopInfoCollection, ...additionalShopInfos);
        expect(comp.shopInfosSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const counterInfo: ICounterInfo = { id: 456 };
        const shopInfo: IShopInfo = { id: 96400 };
        counterInfo.shopInfo = shopInfo;

        activatedRoute.data = of({ counterInfo });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(counterInfo));
        expect(comp.shopInfosSharedCollection).toContain(shopInfo);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const counterInfo = { id: 123 };
        spyOn(counterInfoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ counterInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: counterInfo }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(counterInfoService.update).toHaveBeenCalledWith(counterInfo);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const counterInfo = new CounterInfo();
        spyOn(counterInfoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ counterInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: counterInfo }));
        saveSubject.complete();

        // THEN
        expect(counterInfoService.create).toHaveBeenCalledWith(counterInfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const counterInfo = { id: 123 };
        spyOn(counterInfoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ counterInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(counterInfoService.update).toHaveBeenCalledWith(counterInfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackShopInfoById', () => {
        it('Should return tracked ShopInfo primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackShopInfoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
