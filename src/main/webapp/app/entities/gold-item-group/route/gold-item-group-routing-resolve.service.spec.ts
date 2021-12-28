jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IGoldItemGroup, GoldItemGroup } from '../gold-item-group.model';
import { GoldItemGroupService } from '../service/gold-item-group.service';

import { GoldItemGroupRoutingResolveService } from './gold-item-group-routing-resolve.service';

describe('Service Tests', () => {
  describe('GoldItemGroup routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: GoldItemGroupRoutingResolveService;
    let service: GoldItemGroupService;
    let resultGoldItemGroup: IGoldItemGroup | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(GoldItemGroupRoutingResolveService);
      service = TestBed.inject(GoldItemGroupService);
      resultGoldItemGroup = undefined;
    });

    describe('resolve', () => {
      it('should return IGoldItemGroup returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldItemGroup = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGoldItemGroup).toEqual({ id: 123 });
      });

      it('should return new IGoldItemGroup if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldItemGroup = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultGoldItemGroup).toEqual(new GoldItemGroup());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldItemGroup = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGoldItemGroup).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
