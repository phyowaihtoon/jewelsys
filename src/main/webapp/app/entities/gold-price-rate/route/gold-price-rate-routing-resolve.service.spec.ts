jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IGoldPriceRate, GoldPriceRate } from '../gold-price-rate.model';
import { GoldPriceRateService } from '../service/gold-price-rate.service';

import { GoldPriceRateRoutingResolveService } from './gold-price-rate-routing-resolve.service';

describe('Service Tests', () => {
  describe('GoldPriceRate routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: GoldPriceRateRoutingResolveService;
    let service: GoldPriceRateService;
    let resultGoldPriceRate: IGoldPriceRate | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(GoldPriceRateRoutingResolveService);
      service = TestBed.inject(GoldPriceRateService);
      resultGoldPriceRate = undefined;
    });

    describe('resolve', () => {
      it('should return IGoldPriceRate returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldPriceRate = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGoldPriceRate).toEqual({ id: 123 });
      });

      it('should return new IGoldPriceRate if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldPriceRate = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultGoldPriceRate).toEqual(new GoldPriceRate());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGoldPriceRate = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGoldPriceRate).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
