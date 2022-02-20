import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { MortgageItemGroup } from 'app/entities/enumerations/mortgage-item-group.model';
import { MortgageDamageType } from 'app/entities/enumerations/mortgage-damage-type.model';
import { IMortgageEntry, MortgageEntry } from '../mortgage-entry.model';

import { MortgageEntryService } from './mortgage-entry.service';

describe('Service Tests', () => {
  describe('MortgageEntry Service', () => {
    let service: MortgageEntryService;
    let httpMock: HttpTestingController;
    let elemDefault: IMortgageEntry;
    let expectedResult: IMortgageEntry | IMortgageEntry[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MortgageEntryService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        address: 'AAAAAAA',
        phone: 'AAAAAAA',
        groupCode: MortgageItemGroup.G01,
        itemCode: 'AAAAAAA',
        damageType: MortgageDamageType.DT01,
        wInKyat: 0,
        wInPae: 0,
        wInYway: 0,
        principalAmount: 0,
        startDate: currentDate,
        interestRate: 0,
        mmYear: 'AAAAAAA',
        mmMonth: 'AAAAAAA',
        mmDayGR: 'AAAAAAA',
        mmDay: 'AAAAAAA',
        delFlg: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MortgageEntry', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
          },
          returnedFromService
        );

        service.create(new MortgageEntry()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MortgageEntry', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            address: 'BBBBBB',
            phone: 'BBBBBB',
            groupCode: 'BBBBBB',
            itemCode: 'BBBBBB',
            damageType: 'BBBBBB',
            wInKyat: 1,
            wInPae: 1,
            wInYway: 1,
            principalAmount: 1,
            startDate: currentDate.format(DATE_TIME_FORMAT),
            interestRate: 1,
            mmYear: 'BBBBBB',
            mmMonth: 'BBBBBB',
            mmDayGR: 'BBBBBB',
            mmDay: 'BBBBBB',
            delFlg: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a MortgageEntry', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            address: 'BBBBBB',
            phone: 'BBBBBB',
            groupCode: 'BBBBBB',
            itemCode: 'BBBBBB',
            principalAmount: 1,
            startDate: currentDate.format(DATE_TIME_FORMAT),
            interestRate: 1,
            mmYear: 'BBBBBB',
            mmMonth: 'BBBBBB',
            mmDay: 'BBBBBB',
          },
          new MortgageEntry()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            startDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MortgageEntry', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            address: 'BBBBBB',
            phone: 'BBBBBB',
            groupCode: 'BBBBBB',
            itemCode: 'BBBBBB',
            damageType: 'BBBBBB',
            wInKyat: 1,
            wInPae: 1,
            wInYway: 1,
            principalAmount: 1,
            startDate: currentDate.format(DATE_TIME_FORMAT),
            interestRate: 1,
            mmYear: 'BBBBBB',
            mmMonth: 'BBBBBB',
            mmDayGR: 'BBBBBB',
            mmDay: 'BBBBBB',
            delFlg: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MortgageEntry', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMortgageEntryToCollectionIfMissing', () => {
        it('should add a MortgageEntry to an empty array', () => {
          const mortgageEntry: IMortgageEntry = { id: 123 };
          expectedResult = service.addMortgageEntryToCollectionIfMissing([], mortgageEntry);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(mortgageEntry);
        });

        it('should not add a MortgageEntry to an array that contains it', () => {
          const mortgageEntry: IMortgageEntry = { id: 123 };
          const mortgageEntryCollection: IMortgageEntry[] = [
            {
              ...mortgageEntry,
            },
            { id: 456 },
          ];
          expectedResult = service.addMortgageEntryToCollectionIfMissing(mortgageEntryCollection, mortgageEntry);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a MortgageEntry to an array that doesn't contain it", () => {
          const mortgageEntry: IMortgageEntry = { id: 123 };
          const mortgageEntryCollection: IMortgageEntry[] = [{ id: 456 }];
          expectedResult = service.addMortgageEntryToCollectionIfMissing(mortgageEntryCollection, mortgageEntry);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(mortgageEntry);
        });

        it('should add only unique MortgageEntry to an array', () => {
          const mortgageEntryArray: IMortgageEntry[] = [{ id: 123 }, { id: 456 }, { id: 932 }];
          const mortgageEntryCollection: IMortgageEntry[] = [{ id: 123 }];
          expectedResult = service.addMortgageEntryToCollectionIfMissing(mortgageEntryCollection, ...mortgageEntryArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const mortgageEntry: IMortgageEntry = { id: 123 };
          const mortgageEntry2: IMortgageEntry = { id: 456 };
          expectedResult = service.addMortgageEntryToCollectionIfMissing([], mortgageEntry, mortgageEntry2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(mortgageEntry);
          expect(expectedResult).toContain(mortgageEntry2);
        });

        it('should accept null and undefined values', () => {
          const mortgageEntry: IMortgageEntry = { id: 123 };
          expectedResult = service.addMortgageEntryToCollectionIfMissing([], null, mortgageEntry, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(mortgageEntry);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
