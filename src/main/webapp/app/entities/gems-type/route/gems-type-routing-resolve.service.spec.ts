jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IGemsType, GemsType } from '../gems-type.model';
import { GemsTypeService } from '../service/gems-type.service';

import { GemsTypeRoutingResolveService } from './gems-type-routing-resolve.service';

describe('Service Tests', () => {
  describe('GemsType routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: GemsTypeRoutingResolveService;
    let service: GemsTypeService;
    let resultGemsType: IGemsType | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(GemsTypeRoutingResolveService);
      service = TestBed.inject(GemsTypeService);
      resultGemsType = undefined;
    });

    describe('resolve', () => {
      it('should return IGemsType returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGemsType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGemsType).toEqual({ id: 123 });
      });

      it('should return new IGemsType if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGemsType = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultGemsType).toEqual(new GemsType());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGemsType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGemsType).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
