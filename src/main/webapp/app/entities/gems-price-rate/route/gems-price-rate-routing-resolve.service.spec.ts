jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IGemsPriceRate, GemsPriceRate } from '../gems-price-rate.model';
import { GemsPriceRateService } from '../service/gems-price-rate.service';

import { GemsPriceRateRoutingResolveService } from './gems-price-rate-routing-resolve.service';

describe('Service Tests', () => {
  describe('GemsPriceRate routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: GemsPriceRateRoutingResolveService;
    let service: GemsPriceRateService;
    let resultGemsPriceRate: IGemsPriceRate | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(GemsPriceRateRoutingResolveService);
      service = TestBed.inject(GemsPriceRateService);
      resultGemsPriceRate = undefined;
    });

    describe('resolve', () => {
      it('should return IGemsPriceRate returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGemsPriceRate = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGemsPriceRate).toEqual({ id: 123 });
      });

      it('should return new IGemsPriceRate if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGemsPriceRate = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultGemsPriceRate).toEqual(new GemsPriceRate());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGemsPriceRate = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGemsPriceRate).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
