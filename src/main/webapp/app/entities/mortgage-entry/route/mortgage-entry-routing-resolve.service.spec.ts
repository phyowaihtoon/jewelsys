jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMortgageEntry, MortgageEntry } from '../mortgage-entry.model';
import { MortgageEntryService } from '../service/mortgage-entry.service';

import { MortgageEntryRoutingResolveService } from './mortgage-entry-routing-resolve.service';

describe('Service Tests', () => {
  describe('MortgageEntry routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MortgageEntryRoutingResolveService;
    let service: MortgageEntryService;
    let resultMortgageEntry: IMortgageEntry | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MortgageEntryRoutingResolveService);
      service = TestBed.inject(MortgageEntryService);
      resultMortgageEntry = undefined;
    });

    describe('resolve', () => {
      it('should return IMortgageEntry returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMortgageEntry = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMortgageEntry).toEqual({ id: 123 });
      });

      it('should return new IMortgageEntry if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMortgageEntry = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMortgageEntry).toEqual(new MortgageEntry());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMortgageEntry = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMortgageEntry).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
