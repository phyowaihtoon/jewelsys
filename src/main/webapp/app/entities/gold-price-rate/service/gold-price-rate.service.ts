import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGoldPriceRate, getGoldPriceRateIdentifier } from '../gold-price-rate.model';

export type EntityResponseType = HttpResponse<IGoldPriceRate>;
export type EntityArrayResponseType = HttpResponse<IGoldPriceRate[]>;

@Injectable({ providedIn: 'root' })
export class GoldPriceRateService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/gold-price-rates');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(goldPriceRate: IGoldPriceRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goldPriceRate);
    return this.http
      .post<IGoldPriceRate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(goldPriceRate: IGoldPriceRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goldPriceRate);
    return this.http
      .put<IGoldPriceRate>(`${this.resourceUrl}/${getGoldPriceRateIdentifier(goldPriceRate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(goldPriceRate: IGoldPriceRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goldPriceRate);
    return this.http
      .patch<IGoldPriceRate>(`${this.resourceUrl}/${getGoldPriceRateIdentifier(goldPriceRate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGoldPriceRate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGoldPriceRate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGoldPriceRateToCollectionIfMissing(
    goldPriceRateCollection: IGoldPriceRate[],
    ...goldPriceRatesToCheck: (IGoldPriceRate | null | undefined)[]
  ): IGoldPriceRate[] {
    const goldPriceRates: IGoldPriceRate[] = goldPriceRatesToCheck.filter(isPresent);
    if (goldPriceRates.length > 0) {
      const goldPriceRateCollectionIdentifiers = goldPriceRateCollection.map(
        goldPriceRateItem => getGoldPriceRateIdentifier(goldPriceRateItem)!
      );
      const goldPriceRatesToAdd = goldPriceRates.filter(goldPriceRateItem => {
        const goldPriceRateIdentifier = getGoldPriceRateIdentifier(goldPriceRateItem);
        if (goldPriceRateIdentifier == null || goldPriceRateCollectionIdentifiers.includes(goldPriceRateIdentifier)) {
          return false;
        }
        goldPriceRateCollectionIdentifiers.push(goldPriceRateIdentifier);
        return true;
      });
      return [...goldPriceRatesToAdd, ...goldPriceRateCollection];
    }
    return goldPriceRateCollection;
  }

  protected convertDateFromClient(goldPriceRate: IGoldPriceRate): IGoldPriceRate {
    return Object.assign({}, goldPriceRate, {
      effectiveDate: goldPriceRate.effectiveDate?.isValid() ? goldPriceRate.effectiveDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.effectiveDate = res.body.effectiveDate ? dayjs(res.body.effectiveDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((goldPriceRate: IGoldPriceRate) => {
        goldPriceRate.effectiveDate = goldPriceRate.effectiveDate ? dayjs(goldPriceRate.effectiveDate) : undefined;
      });
    }
    return res;
  }
}
