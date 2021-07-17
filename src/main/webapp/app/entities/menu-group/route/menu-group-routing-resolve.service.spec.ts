jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMenuGroup, MenuGroup } from '../menu-group.model';
import { MenuGroupService } from '../service/menu-group.service';

import { MenuGroupRoutingResolveService } from './menu-group-routing-resolve.service';

describe('Service Tests', () => {
  describe('MenuGroup routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MenuGroupRoutingResolveService;
    let service: MenuGroupService;
    let resultMenuGroup: IMenuGroup | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MenuGroupRoutingResolveService);
      service = TestBed.inject(MenuGroupService);
      resultMenuGroup = undefined;
    });

    describe('resolve', () => {
      it('should return IMenuGroup returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMenuGroup = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMenuGroup).toEqual({ id: 123 });
      });

      it('should return new IMenuGroup if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMenuGroup = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMenuGroup).toEqual(new MenuGroup());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMenuGroup = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMenuGroup).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
