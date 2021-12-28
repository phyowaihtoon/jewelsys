import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGoldItemGroup, GoldItemGroup } from '../gold-item-group.model';

import { GoldItemGroupService } from './gold-item-group.service';

describe('Service Tests', () => {
  describe('GoldItemGroup Service', () => {
    let service: GoldItemGroupService;
    let httpMock: HttpTestingController;
    let elemDefault: IGoldItemGroup;
    let expectedResult: IGoldItemGroup | IGoldItemGroup[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(GoldItemGroupService);
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

      it('should create a GoldItemGroup', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new GoldItemGroup()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GoldItemGroup', () => {
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

      it('should partial update a GoldItemGroup', () => {
        const patchObject = Object.assign(
          {
            code: 'BBBBBB',
            name: 'BBBBBB',
          },
          new GoldItemGroup()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GoldItemGroup', () => {
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

      it('should delete a GoldItemGroup', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addGoldItemGroupToCollectionIfMissing', () => {
        it('should add a GoldItemGroup to an empty array', () => {
          const goldItemGroup: IGoldItemGroup = { id: 123 };
          expectedResult = service.addGoldItemGroupToCollectionIfMissing([], goldItemGroup);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(goldItemGroup);
        });

        it('should not add a GoldItemGroup to an array that contains it', () => {
          const goldItemGroup: IGoldItemGroup = { id: 123 };
          const goldItemGroupCollection: IGoldItemGroup[] = [
            {
              ...goldItemGroup,
            },
            { id: 456 },
          ];
          expectedResult = service.addGoldItemGroupToCollectionIfMissing(goldItemGroupCollection, goldItemGroup);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a GoldItemGroup to an array that doesn't contain it", () => {
          const goldItemGroup: IGoldItemGroup = { id: 123 };
          const goldItemGroupCollection: IGoldItemGroup[] = [{ id: 456 }];
          expectedResult = service.addGoldItemGroupToCollectionIfMissing(goldItemGroupCollection, goldItemGroup);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(goldItemGroup);
        });

        it('should add only unique GoldItemGroup to an array', () => {
          const goldItemGroupArray: IGoldItemGroup[] = [{ id: 123 }, { id: 456 }, { id: 43617 }];
          const goldItemGroupCollection: IGoldItemGroup[] = [{ id: 123 }];
          expectedResult = service.addGoldItemGroupToCollectionIfMissing(goldItemGroupCollection, ...goldItemGroupArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const goldItemGroup: IGoldItemGroup = { id: 123 };
          const goldItemGroup2: IGoldItemGroup = { id: 456 };
          expectedResult = service.addGoldItemGroupToCollectionIfMissing([], goldItemGroup, goldItemGroup2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(goldItemGroup);
          expect(expectedResult).toContain(goldItemGroup2);
        });

        it('should accept null and undefined values', () => {
          const goldItemGroup: IGoldItemGroup = { id: 123 };
          expectedResult = service.addGoldItemGroupToCollectionIfMissing([], null, goldItemGroup, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(goldItemGroup);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
