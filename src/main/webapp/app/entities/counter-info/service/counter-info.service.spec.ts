import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICounterInfo, CounterInfo } from '../counter-info.model';

import { CounterInfoService } from './counter-info.service';

describe('Service Tests', () => {
  describe('CounterInfo Service', () => {
    let service: CounterInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICounterInfo;
    let expectedResult: ICounterInfo | ICounterInfo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CounterInfoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        counterNo: 'AAAAAAA',
        counterName: 'AAAAAAA',
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

      it('should create a CounterInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CounterInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CounterInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            counterNo: 'BBBBBB',
            counterName: 'BBBBBB',
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

      it('should partial update a CounterInfo', () => {
        const patchObject = Object.assign(
          {
            counterNo: 'BBBBBB',
            counterName: 'BBBBBB',
            delFlg: 'BBBBBB',
          },
          new CounterInfo()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CounterInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            counterNo: 'BBBBBB',
            counterName: 'BBBBBB',
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

      it('should delete a CounterInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCounterInfoToCollectionIfMissing', () => {
        it('should add a CounterInfo to an empty array', () => {
          const counterInfo: ICounterInfo = { id: 123 };
          expectedResult = service.addCounterInfoToCollectionIfMissing([], counterInfo);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(counterInfo);
        });

        it('should not add a CounterInfo to an array that contains it', () => {
          const counterInfo: ICounterInfo = { id: 123 };
          const counterInfoCollection: ICounterInfo[] = [
            {
              ...counterInfo,
            },
            { id: 456 },
          ];
          expectedResult = service.addCounterInfoToCollectionIfMissing(counterInfoCollection, counterInfo);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CounterInfo to an array that doesn't contain it", () => {
          const counterInfo: ICounterInfo = { id: 123 };
          const counterInfoCollection: ICounterInfo[] = [{ id: 456 }];
          expectedResult = service.addCounterInfoToCollectionIfMissing(counterInfoCollection, counterInfo);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(counterInfo);
        });

        it('should add only unique CounterInfo to an array', () => {
          const counterInfoArray: ICounterInfo[] = [{ id: 123 }, { id: 456 }, { id: 41835 }];
          const counterInfoCollection: ICounterInfo[] = [{ id: 123 }];
          expectedResult = service.addCounterInfoToCollectionIfMissing(counterInfoCollection, ...counterInfoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const counterInfo: ICounterInfo = { id: 123 };
          const counterInfo2: ICounterInfo = { id: 456 };
          expectedResult = service.addCounterInfoToCollectionIfMissing([], counterInfo, counterInfo2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(counterInfo);
          expect(expectedResult).toContain(counterInfo2);
        });

        it('should accept null and undefined values', () => {
          const counterInfo: ICounterInfo = { id: 123 };
          expectedResult = service.addCounterInfoToCollectionIfMissing([], null, counterInfo, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(counterInfo);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
