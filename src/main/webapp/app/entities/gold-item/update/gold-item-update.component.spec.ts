jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GoldItemService } from '../service/gold-item.service';
import { IGoldItem, GoldItem } from '../gold-item.model';
import { IGoldItemGroup } from 'app/entities/gold-item-group/gold-item-group.model';
import { GoldItemGroupService } from 'app/entities/gold-item-group/service/gold-item-group.service';

import { GoldItemUpdateComponent } from './gold-item-update.component';

describe('Component Tests', () => {
  describe('GoldItem Management Update Component', () => {
    let comp: GoldItemUpdateComponent;
    let fixture: ComponentFixture<GoldItemUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let goldItemService: GoldItemService;
    let goldItemGroupService: GoldItemGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GoldItemUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GoldItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoldItemUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      goldItemService = TestBed.inject(GoldItemService);
      goldItemGroupService = TestBed.inject(GoldItemGroupService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call GoldItemGroup query and add missing value', () => {
        const goldItem: IGoldItem = { id: 456 };
        const goldItemGroup: IGoldItemGroup = { id: 9890 };
        goldItem.goldItemGroup = goldItemGroup;

        const goldItemGroupCollection: IGoldItemGroup[] = [{ id: 69506 }];
        spyOn(goldItemGroupService, 'query').and.returnValue(of(new HttpResponse({ body: goldItemGroupCollection })));
        const additionalGoldItemGroups = [goldItemGroup];
        const expectedCollection: IGoldItemGroup[] = [...additionalGoldItemGroups, ...goldItemGroupCollection];
        spyOn(goldItemGroupService, 'addGoldItemGroupToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ goldItem });
        comp.ngOnInit();

        expect(goldItemGroupService.query).toHaveBeenCalled();
        expect(goldItemGroupService.addGoldItemGroupToCollectionIfMissing).toHaveBeenCalledWith(
          goldItemGroupCollection,
          ...additionalGoldItemGroups
        );
        expect(comp.goldItemGroupsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const goldItem: IGoldItem = { id: 456 };
        const goldItemGroup: IGoldItemGroup = { id: 45249 };
        goldItem.goldItemGroup = goldItemGroup;

        activatedRoute.data = of({ goldItem });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(goldItem));
        expect(comp.goldItemGroupsSharedCollection).toContain(goldItemGroup);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldItem = { id: 123 };
        spyOn(goldItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: goldItem }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(goldItemService.update).toHaveBeenCalledWith(goldItem);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldItem = new GoldItem();
        spyOn(goldItemService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: goldItem }));
        saveSubject.complete();

        // THEN
        expect(goldItemService.create).toHaveBeenCalledWith(goldItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const goldItem = { id: 123 };
        spyOn(goldItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ goldItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(goldItemService.update).toHaveBeenCalledWith(goldItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackGoldItemGroupById', () => {
        it('Should return tracked GoldItemGroup primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackGoldItemGroupById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
