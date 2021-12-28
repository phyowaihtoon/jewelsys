jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RoleMenuMapService } from '../service/role-menu-map.service';
import { IRoleMenuMap, RoleMenuMap } from '../role-menu-map.model';
import { IMenu } from 'app/entities/menu/menu.model';
import { MenuService } from 'app/entities/menu/service/menu.service';

import { RoleMenuMapUpdateComponent } from './role-menu-map-update.component';

describe('Component Tests', () => {
  describe('RoleMenuMap Management Update Component', () => {
    let comp: RoleMenuMapUpdateComponent;
    let fixture: ComponentFixture<RoleMenuMapUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let roleMenuMapService: RoleMenuMapService;
    let menuService: MenuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RoleMenuMapUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RoleMenuMapUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RoleMenuMapUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      roleMenuMapService = TestBed.inject(RoleMenuMapService);
      menuService = TestBed.inject(MenuService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Menu query and add missing value', () => {
        const roleMenuMap: IRoleMenuMap = { id: 456 };
        const menu: IMenu = { id: 15679 };
        roleMenuMap.menu = menu;

        const menuCollection: IMenu[] = [{ id: 2123 }];
        spyOn(menuService, 'query').and.returnValue(of(new HttpResponse({ body: menuCollection })));
        const additionalMenus = [menu];
        const expectedCollection: IMenu[] = [...additionalMenus, ...menuCollection];
        spyOn(menuService, 'addMenuToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ roleMenuMap });
        comp.ngOnInit();

        expect(menuService.query).toHaveBeenCalled();
        expect(menuService.addMenuToCollectionIfMissing).toHaveBeenCalledWith(menuCollection, ...additionalMenus);
        expect(comp.menusSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const roleMenuMap: IRoleMenuMap = { id: 456 };
        const menu: IMenu = { id: 19900 };
        roleMenuMap.menu = menu;

        activatedRoute.data = of({ roleMenuMap });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(roleMenuMap));
        expect(comp.menusSharedCollection).toContain(menu);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const roleMenuMap = { id: 123 };
        spyOn(roleMenuMapService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ roleMenuMap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: roleMenuMap }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(roleMenuMapService.update).toHaveBeenCalledWith(roleMenuMap);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const roleMenuMap = new RoleMenuMap();
        spyOn(roleMenuMapService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ roleMenuMap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: roleMenuMap }));
        saveSubject.complete();

        // THEN
        expect(roleMenuMapService.create).toHaveBeenCalledWith(roleMenuMap);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const roleMenuMap = { id: 123 };
        spyOn(roleMenuMapService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ roleMenuMap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(roleMenuMapService.update).toHaveBeenCalledWith(roleMenuMap);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackMenuById', () => {
        it('Should return tracked Menu primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMenuById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
