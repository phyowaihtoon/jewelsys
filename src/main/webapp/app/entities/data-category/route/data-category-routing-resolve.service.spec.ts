jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDataCategory, DataCategory } from '../data-category.model';
import { DataCategoryService } from '../service/data-category.service';

import { DataCategoryRoutingResolveService } from './data-category-routing-resolve.service';

describe('Service Tests', () => {
  describe('DataCategory routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DataCategoryRoutingResolveService;
    let service: DataCategoryService;
    let resultDataCategory: IDataCategory | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DataCategoryRoutingResolveService);
      service = TestBed.inject(DataCategoryService);
      resultDataCategory = undefined;
    });

    describe('resolve', () => {
      it('should return IDataCategory returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDataCategory = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDataCategory).toEqual({ id: 123 });
      });

      it('should return new IDataCategory if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDataCategory = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDataCategory).toEqual(new DataCategory());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDataCategory = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDataCategory).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
