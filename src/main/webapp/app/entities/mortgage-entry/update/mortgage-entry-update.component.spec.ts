jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MortgageEntryService } from '../service/mortgage-entry.service';
import { IMortgageEntry, MortgageEntry } from '../mortgage-entry.model';

import { MortgageEntryUpdateComponent } from './mortgage-entry-update.component';

describe('Component Tests', () => {
  describe('MortgageEntry Management Update Component', () => {
    let comp: MortgageEntryUpdateComponent;
    let fixture: ComponentFixture<MortgageEntryUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let mortgageEntryService: MortgageEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MortgageEntryUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MortgageEntryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MortgageEntryUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      mortgageEntryService = TestBed.inject(MortgageEntryService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const mortgageEntry: IMortgageEntry = { id: 456 };

        activatedRoute.data = of({ mortgageEntry });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(mortgageEntry));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const mortgageEntry = { id: 123 };
        spyOn(mortgageEntryService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ mortgageEntry });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: mortgageEntry }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(mortgageEntryService.update).toHaveBeenCalledWith(mortgageEntry);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const mortgageEntry = new MortgageEntry();
        spyOn(mortgageEntryService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ mortgageEntry });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: mortgageEntry }));
        saveSubject.complete();

        // THEN
        expect(mortgageEntryService.create).toHaveBeenCalledWith(mortgageEntry);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const mortgageEntry = { id: 123 };
        spyOn(mortgageEntryService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ mortgageEntry });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(mortgageEntryService.update).toHaveBeenCalledWith(mortgageEntry);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
