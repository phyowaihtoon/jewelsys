jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MortgageItemService } from '../service/mortgage-item.service';
import { IMortgageItem, MortgageItem } from '../mortgage-item.model';

import { MortgageItemUpdateComponent } from './mortgage-item-update.component';

describe('Component Tests', () => {
  describe('MortgageItem Management Update Component', () => {
    let comp: MortgageItemUpdateComponent;
    let fixture: ComponentFixture<MortgageItemUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let mortgageItemService: MortgageItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MortgageItemUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MortgageItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MortgageItemUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      mortgageItemService = TestBed.inject(MortgageItemService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const mortgageItem: IMortgageItem = { id: 456 };

        activatedRoute.data = of({ mortgageItem });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(mortgageItem));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const mortgageItem = { id: 123 };
        spyOn(mortgageItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ mortgageItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: mortgageItem }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(mortgageItemService.update).toHaveBeenCalledWith(mortgageItem);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const mortgageItem = new MortgageItem();
        spyOn(mortgageItemService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ mortgageItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: mortgageItem }));
        saveSubject.complete();

        // THEN
        expect(mortgageItemService.create).toHaveBeenCalledWith(mortgageItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const mortgageItem = { id: 123 };
        spyOn(mortgageItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ mortgageItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(mortgageItemService.update).toHaveBeenCalledWith(mortgageItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
