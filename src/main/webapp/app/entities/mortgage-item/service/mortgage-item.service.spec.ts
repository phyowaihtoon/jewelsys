import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MortgageItemGroup } from 'app/entities/enumerations/mortgage-item-group.model';
import { IMortgageItem, MortgageItem } from '../mortgage-item.model';

import { MortgageItemService } from './mortgage-item.service';

describe('Service Tests', () => {
  describe('MortgageItem Service', () => {
    let service: MortgageItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IMortgageItem;
    let expectedResult: IMortgageItem | IMortgageItem[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MortgageItemService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        groupCode: MortgageItemGroup.G01,
        code: 'AAAAAAA',
        itemName: 'AAAAAAA',
        delFlg: 'AAAAAAA',
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

      it('should create a MortgageItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MortgageItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MortgageItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            groupCode: 'BBBBBB',
            code: 'BBBBBB',
            itemName: 'BBBBBB',
            delFlg: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a MortgageItem', () => {
        const patchObject = Object.assign(
          {
            groupCode: 'BBBBBB',
            itemName: 'BBBBBB',
          },
          new MortgageItem()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MortgageItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            groupCode: 'BBBBBB',
            code: 'BBBBBB',
            itemName: 'BBBBBB',
            delFlg: 'BBBBBB',
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

      it('should delete a MortgageItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMortgageItemToCollectionIfMissing', () => {
        it('should add a MortgageItem to an empty array', () => {
          const mortgageItem: IMortgageItem = { id: 123 };
          expectedResult = service.addMortgageItemToCollectionIfMissing([], mortgageItem);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(mortgageItem);
        });

        it('should not add a MortgageItem to an array that contains it', () => {
          const mortgageItem: IMortgageItem = { id: 123 };
          const mortgageItemCollection: IMortgageItem[] = [
            {
              ...mortgageItem,
            },
            { id: 456 },
          ];
          expectedResult = service.addMortgageItemToCollectionIfMissing(mortgageItemCollection, mortgageItem);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a MortgageItem to an array that doesn't contain it", () => {
          const mortgageItem: IMortgageItem = { id: 123 };
          const mortgageItemCollection: IMortgageItem[] = [{ id: 456 }];
          expectedResult = service.addMortgageItemToCollectionIfMissing(mortgageItemCollection, mortgageItem);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(mortgageItem);
        });

        it('should add only unique MortgageItem to an array', () => {
          const mortgageItemArray: IMortgageItem[] = [{ id: 123 }, { id: 456 }, { id: 22059 }];
          const mortgageItemCollection: IMortgageItem[] = [{ id: 123 }];
          expectedResult = service.addMortgageItemToCollectionIfMissing(mortgageItemCollection, ...mortgageItemArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const mortgageItem: IMortgageItem = { id: 123 };
          const mortgageItem2: IMortgageItem = { id: 456 };
          expectedResult = service.addMortgageItemToCollectionIfMissing([], mortgageItem, mortgageItem2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(mortgageItem);
          expect(expectedResult).toContain(mortgageItem2);
        });

        it('should accept null and undefined values', () => {
          const mortgageItem: IMortgageItem = { id: 123 };
          expectedResult = service.addMortgageItemToCollectionIfMissing([], null, mortgageItem, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(mortgageItem);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
