import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShopInfo, ShopInfo } from '../shop-info.model';

import { ShopInfoService } from './shop-info.service';

describe('Service Tests', () => {
  describe('ShopInfo Service', () => {
    let service: ShopInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IShopInfo;
    let expectedResult: IShopInfo | IShopInfo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ShopInfoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        shopCode: 'AAAAAAA',
        nameEng: 'AAAAAAA',
        nameMyan: 'AAAAAAA',
        addrEng: 'AAAAAAA',
        addrMyan: 'AAAAAAA',
        phone: 'AAAAAAA',
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

      it('should create a ShopInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ShopInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ShopInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            shopCode: 'BBBBBB',
            nameEng: 'BBBBBB',
            nameMyan: 'BBBBBB',
            addrEng: 'BBBBBB',
            addrMyan: 'BBBBBB',
            phone: 'BBBBBB',
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

      it('should partial update a ShopInfo', () => {
        const patchObject = Object.assign(
          {
            shopCode: 'BBBBBB',
            addrEng: 'BBBBBB',
            addrMyan: 'BBBBBB',
          },
          new ShopInfo()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ShopInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            shopCode: 'BBBBBB',
            nameEng: 'BBBBBB',
            nameMyan: 'BBBBBB',
            addrEng: 'BBBBBB',
            addrMyan: 'BBBBBB',
            phone: 'BBBBBB',
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

      it('should delete a ShopInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addShopInfoToCollectionIfMissing', () => {
        it('should add a ShopInfo to an empty array', () => {
          const shopInfo: IShopInfo = { id: 123 };
          expectedResult = service.addShopInfoToCollectionIfMissing([], shopInfo);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(shopInfo);
        });

        it('should not add a ShopInfo to an array that contains it', () => {
          const shopInfo: IShopInfo = { id: 123 };
          const shopInfoCollection: IShopInfo[] = [
            {
              ...shopInfo,
            },
            { id: 456 },
          ];
          expectedResult = service.addShopInfoToCollectionIfMissing(shopInfoCollection, shopInfo);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ShopInfo to an array that doesn't contain it", () => {
          const shopInfo: IShopInfo = { id: 123 };
          const shopInfoCollection: IShopInfo[] = [{ id: 456 }];
          expectedResult = service.addShopInfoToCollectionIfMissing(shopInfoCollection, shopInfo);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(shopInfo);
        });

        it('should add only unique ShopInfo to an array', () => {
          const shopInfoArray: IShopInfo[] = [{ id: 123 }, { id: 456 }, { id: 98754 }];
          const shopInfoCollection: IShopInfo[] = [{ id: 123 }];
          expectedResult = service.addShopInfoToCollectionIfMissing(shopInfoCollection, ...shopInfoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const shopInfo: IShopInfo = { id: 123 };
          const shopInfo2: IShopInfo = { id: 456 };
          expectedResult = service.addShopInfoToCollectionIfMissing([], shopInfo, shopInfo2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(shopInfo);
          expect(expectedResult).toContain(shopInfo2);
        });

        it('should accept null and undefined values', () => {
          const shopInfo: IShopInfo = { id: 123 };
          expectedResult = service.addShopInfoToCollectionIfMissing([], null, shopInfo, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(shopInfo);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
