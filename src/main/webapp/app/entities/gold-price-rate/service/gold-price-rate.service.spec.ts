import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGoldPriceRate, GoldPriceRate } from '../gold-price-rate.model';

import { GoldPriceRateService } from './gold-price-rate.service';

describe('Service Tests', () => {
  describe('GoldPriceRate Service', () => {
    let service: GoldPriceRateService;
    let httpMock: HttpTestingController;
    let elemDefault: IGoldPriceRate;
    let expectedResult: IGoldPriceRate | IGoldPriceRate[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(GoldPriceRateService);
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

      it('should create a GoldPriceRate', () => {
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

        service.create(new GoldPriceRate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GoldPriceRate', () => {
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

      it('should partial update a GoldPriceRate', () => {
        const patchObject = Object.assign(
          {
            rateSrNo: 1,
          },
          new GoldPriceRate()
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

      it('should return a list of GoldPriceRate', () => {
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

      it('should delete a GoldPriceRate', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addGoldPriceRateToCollectionIfMissing', () => {
        it('should add a GoldPriceRate to an empty array', () => {
          const goldPriceRate: IGoldPriceRate = { id: 123 };
          expectedResult = service.addGoldPriceRateToCollectionIfMissing([], goldPriceRate);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(goldPriceRate);
        });

        it('should not add a GoldPriceRate to an array that contains it', () => {
          const goldPriceRate: IGoldPriceRate = { id: 123 };
          const goldPriceRateCollection: IGoldPriceRate[] = [
            {
              ...goldPriceRate,
            },
            { id: 456 },
          ];
          expectedResult = service.addGoldPriceRateToCollectionIfMissing(goldPriceRateCollection, goldPriceRate);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a GoldPriceRate to an array that doesn't contain it", () => {
          const goldPriceRate: IGoldPriceRate = { id: 123 };
          const goldPriceRateCollection: IGoldPriceRate[] = [{ id: 456 }];
          expectedResult = service.addGoldPriceRateToCollectionIfMissing(goldPriceRateCollection, goldPriceRate);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(goldPriceRate);
        });

        it('should add only unique GoldPriceRate to an array', () => {
          const goldPriceRateArray: IGoldPriceRate[] = [{ id: 123 }, { id: 456 }, { id: 99004 }];
          const goldPriceRateCollection: IGoldPriceRate[] = [{ id: 123 }];
          expectedResult = service.addGoldPriceRateToCollectionIfMissing(goldPriceRateCollection, ...goldPriceRateArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const goldPriceRate: IGoldPriceRate = { id: 123 };
          const goldPriceRate2: IGoldPriceRate = { id: 456 };
          expectedResult = service.addGoldPriceRateToCollectionIfMissing([], goldPriceRate, goldPriceRate2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(goldPriceRate);
          expect(expectedResult).toContain(goldPriceRate2);
        });

        it('should accept null and undefined values', () => {
          const goldPriceRate: IGoldPriceRate = { id: 123 };
          expectedResult = service.addGoldPriceRateToCollectionIfMissing([], null, goldPriceRate, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(goldPriceRate);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
