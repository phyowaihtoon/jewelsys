import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGemsPriceRate, GemsPriceRate } from '../gems-price-rate.model';

import { GemsPriceRateService } from './gems-price-rate.service';

describe('Service Tests', () => {
  describe('GemsPriceRate Service', () => {
    let service: GemsPriceRateService;
    let httpMock: HttpTestingController;
    let elemDefault: IGemsPriceRate;
    let expectedResult: IGemsPriceRate | IGemsPriceRate[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(GemsPriceRateService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        effectiveDate: currentDate,
        rateSrNo: 0,
        rateType: 'AAAAAAA',
        priceRate: 0,
        delFlg: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            effectiveDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GemsPriceRate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            effectiveDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
          },
          returnedFromService
        );

        service.create(new GemsPriceRate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GemsPriceRate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            effectiveDate: currentDate.format(DATE_TIME_FORMAT),
            rateSrNo: 1,
            rateType: 'BBBBBB',
            priceRate: 1,
            delFlg: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a GemsPriceRate', () => {
        const patchObject = Object.assign(
          {
            rateType: 'BBBBBB',
            priceRate: 1,
            delFlg: 'BBBBBB',
          },
          new GemsPriceRate()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GemsPriceRate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            effectiveDate: currentDate.format(DATE_TIME_FORMAT),
            rateSrNo: 1,
            rateType: 'BBBBBB',
            priceRate: 1,
            delFlg: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GemsPriceRate', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addGemsPriceRateToCollectionIfMissing', () => {
        it('should add a GemsPriceRate to an empty array', () => {
          const gemsPriceRate: IGemsPriceRate = { id: 123 };
          expectedResult = service.addGemsPriceRateToCollectionIfMissing([], gemsPriceRate);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(gemsPriceRate);
        });

        it('should not add a GemsPriceRate to an array that contains it', () => {
          const gemsPriceRate: IGemsPriceRate = { id: 123 };
          const gemsPriceRateCollection: IGemsPriceRate[] = [
            {
              ...gemsPriceRate,
            },
            { id: 456 },
          ];
          expectedResult = service.addGemsPriceRateToCollectionIfMissing(gemsPriceRateCollection, gemsPriceRate);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a GemsPriceRate to an array that doesn't contain it", () => {
          const gemsPriceRate: IGemsPriceRate = { id: 123 };
          const gemsPriceRateCollection: IGemsPriceRate[] = [{ id: 456 }];
          expectedResult = service.addGemsPriceRateToCollectionIfMissing(gemsPriceRateCollection, gemsPriceRate);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(gemsPriceRate);
        });

        it('should add only unique GemsPriceRate to an array', () => {
          const gemsPriceRateArray: IGemsPriceRate[] = [{ id: 123 }, { id: 456 }, { id: 70666 }];
          const gemsPriceRateCollection: IGemsPriceRate[] = [{ id: 123 }];
          expectedResult = service.addGemsPriceRateToCollectionIfMissing(gemsPriceRateCollection, ...gemsPriceRateArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const gemsPriceRate: IGemsPriceRate = { id: 123 };
          const gemsPriceRate2: IGemsPriceRate = { id: 456 };
          expectedResult = service.addGemsPriceRateToCollectionIfMissing([], gemsPriceRate, gemsPriceRate2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(gemsPriceRate);
          expect(expectedResult).toContain(gemsPriceRate2);
        });

        it('should accept null and undefined values', () => {
          const gemsPriceRate: IGemsPriceRate = { id: 123 };
          expectedResult = service.addGemsPriceRateToCollectionIfMissing([], null, gemsPriceRate, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(gemsPriceRate);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
