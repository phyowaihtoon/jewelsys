jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IShopInfo, ShopInfo } from '../shop-info.model';
import { ShopInfoService } from '../service/shop-info.service';

import { ShopInfoRoutingResolveService } from './shop-info-routing-resolve.service';

describe('Service Tests', () => {
  describe('ShopInfo routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ShopInfoRoutingResolveService;
    let service: ShopInfoService;
    let resultShopInfo: IShopInfo | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ShopInfoRoutingResolveService);
      service = TestBed.inject(ShopInfoService);
      resultShopInfo = undefined;
    });

    describe('resolve', () => {
      it('should return IShopInfo returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultShopInfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultShopInfo).toEqual({ id: 123 });
      });

      it('should return new IShopInfo if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultShopInfo = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultShopInfo).toEqual(new ShopInfo());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultShopInfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultShopInfo).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
