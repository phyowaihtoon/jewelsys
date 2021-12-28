import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDataCategory, DataCategory } from '../data-category.model';

import { DataCategoryService } from './data-category.service';

describe('Service Tests', () => {
  describe('DataCategory Service', () => {
    let service: DataCategoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IDataCategory;
    let expectedResult: IDataCategory | IDataCategory[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DataCategoryService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        categoryType: 'AAAAAAA',
        categoryCode: 'AAAAAAA',
        value: 'AAAAAAA',
        categoryDesc: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DataCategory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DataCategory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DataCategory', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            categoryType: 'BBBBBB',
            categoryCode: 'BBBBBB',
            value: 'BBBBBB',
            categoryDesc: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DataCategory', () => {
        const patchObject = Object.assign(
          {
            categoryCode: 'BBBBBB',
            categoryDesc: 'BBBBBB',
          },
          new DataCategory()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DataCategory', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            categoryType: 'BBBBBB',
            categoryCode: 'BBBBBB',
            value: 'BBBBBB',
            categoryDesc: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DataCategory', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDataCategoryToCollectionIfMissing', () => {
        it('should add a DataCategory to an empty array', () => {
          const dataCategory: IDataCategory = { id: 123 };
          expectedResult = service.addDataCategoryToCollectionIfMissing([], dataCategory);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dataCategory);
        });

        it('should not add a DataCategory to an array that contains it', () => {
          const dataCategory: IDataCategory = { id: 123 };
          const dataCategoryCollection: IDataCategory[] = [
            {
              ...dataCategory,
            },
            { id: 456 },
          ];
          expectedResult = service.addDataCategoryToCollectionIfMissing(dataCategoryCollection, dataCategory);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DataCategory to an array that doesn't contain it", () => {
          const dataCategory: IDataCategory = { id: 123 };
          const dataCategoryCollection: IDataCategory[] = [{ id: 456 }];
          expectedResult = service.addDataCategoryToCollectionIfMissing(dataCategoryCollection, dataCategory);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dataCategory);
        });

        it('should add only unique DataCategory to an array', () => {
          const dataCategoryArray: IDataCategory[] = [{ id: 123 }, { id: 456 }, { id: 69526 }];
          const dataCategoryCollection: IDataCategory[] = [{ id: 123 }];
          expectedResult = service.addDataCategoryToCollectionIfMissing(dataCategoryCollection, ...dataCategoryArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dataCategory: IDataCategory = { id: 123 };
          const dataCategory2: IDataCategory = { id: 456 };
          expectedResult = service.addDataCategoryToCollectionIfMissing([], dataCategory, dataCategory2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dataCategory);
          expect(expectedResult).toContain(dataCategory2);
        });

        it('should accept null and undefined values', () => {
          const dataCategory: IDataCategory = { id: 123 };
          expectedResult = service.addDataCategoryToCollectionIfMissing([], null, dataCategory, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dataCategory);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
