jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IGoldItem, GoldItem } from '../gold-item.model';
import { GoldItemService } from '../service/gold-item.service';

import { GoldItemRoutingResolveService } from './gold-item-routing-resolve.service';

describe('Service Tests', () => {
  describe('GoldItem routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: GoldItemRoutingResolveService;
    let service: GoldItemService;
    let resultGoldItem: IGoldItem | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(GoldItemRoutingResolveService);
      service = TestBed.inject(GoldItemService);
      resultGoldItem = undefined;
    });

    describe('resolve', () => {
      it('should return IGoldItem returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGoldItem).toEqual({ id: 123 });
      });

      it('should return new IGoldItem if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldItem = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultGoldItem).toEqual(new GoldItem());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGoldItem).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
