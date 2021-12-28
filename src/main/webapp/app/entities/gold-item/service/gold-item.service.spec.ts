import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGoldItem, GoldItem } from '../gold-item.model';

import { GoldItemService } from './gold-item.service';

describe('Service Tests', () => {
  describe('GoldItem Service', () => {
    let service: GoldItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IGoldItem;
    let expectedResult: IGoldItem | IGoldItem[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(GoldItemService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        code: 'AAAAAAA',
        name: 'AAAAAAA',
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

      it('should create a GoldItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new GoldItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GoldItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            name: 'BBBBBB',
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

      it('should partial update a GoldItem', () => {
        const patchObject = Object.assign(
          {
            code: 'BBBBBB',
          },
          new GoldItem()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GoldItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            name: 'BBBBBB',
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

      it('should delete a GoldItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addGoldItemToCollectionIfMissing', () => {
        it('should add a GoldItem to an empty array', () => {
          const goldItem: IGoldItem = { id: 123 };
          expectedResult = service.addGoldItemToCollectionIfMissing([], goldItem);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(goldItem);
        });

        it('should not add a GoldItem to an array that contains it', () => {
          const goldItem: IGoldItem = { id: 123 };
          const goldItemCollection: IGoldItem[] = [
            {
              ...goldItem,
            },
            { id: 456 },
          ];
          expectedResult = service.addGoldItemToCollectionIfMissing(goldItemCollection, goldItem);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a GoldItem to an array that doesn't contain it", () => {
          const goldItem: IGoldItem = { id: 123 };
          const goldItemCollection: IGoldItem[] = [{ id: 456 }];
          expectedResult = service.addGoldItemToCollectionIfMissing(goldItemCollection, goldItem);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(goldItem);
        });

        it('should add only unique GoldItem to an array', () => {
          const goldItemArray: IGoldItem[] = [{ id: 123 }, { id: 456 }, { id: 46742 }];
          const goldItemCollection: IGoldItem[] = [{ id: 123 }];
          expectedResult = service.addGoldItemToCollectionIfMissing(goldItemCollection, ...goldItemArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const goldItem: IGoldItem = { id: 123 };
          const goldItem2: IGoldItem = { id: 456 };
          expectedResult = service.addGoldItemToCollectionIfMissing([], goldItem, goldItem2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(goldItem);
          expect(expectedResult).toContain(goldItem2);
        });

        it('should accept null and undefined values', () => {
          const goldItem: IGoldItem = { id: 123 };
          expectedResult = service.addGoldItemToCollectionIfMissing([], null, goldItem, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(goldItem);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
