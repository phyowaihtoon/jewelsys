import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGemsPriceRate, getGemsPriceRateIdentifier } from '../gems-price-rate.model';

export type EntityResponseType = HttpResponse<IGemsPriceRate>;
export type EntityArrayResponseType = HttpResponse<IGemsPriceRate[]>;

@Injectable({ providedIn: 'root' })
export class GemsPriceRateService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/gems-price-rates');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(gemsPriceRate: IGemsPriceRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gemsPriceRate);
    return this.http
      .post<IGemsPriceRate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(gemsPriceRate: IGemsPriceRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gemsPriceRate);
    return this.http
      .put<IGemsPriceRate>(`${this.resourceUrl}/${getGemsPriceRateIdentifier(gemsPriceRate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(gemsPriceRate: IGemsPriceRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gemsPriceRate);
    return this.http
      .patch<IGemsPriceRate>(`${this.resourceUrl}/${getGemsPriceRateIdentifier(gemsPriceRate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGemsPriceRate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGemsPriceRate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGemsPriceRateToCollectionIfMissing(
    gemsPriceRateCollection: IGemsPriceRate[],
    ...gemsPriceRatesToCheck: (IGemsPriceRate | null | undefined)[]
  ): IGemsPriceRate[] {
    const gemsPriceRates: IGemsPriceRate[] = gemsPriceRatesToCheck.filter(isPresent);
    if (gemsPriceRates.length > 0) {
      const gemsPriceRateCollectionIdentifiers = gemsPriceRateCollection.map(
        gemsPriceRateItem => getGemsPriceRateIdentifier(gemsPriceRateItem)!
      );
      const gemsPriceRatesToAdd = gemsPriceRates.filter(gemsPriceRateItem => {
        const gemsPriceRateIdentifier = getGemsPriceRateIdentifier(gemsPriceRateItem);
        if (gemsPriceRateIdentifier == null || gemsPriceRateCollectionIdentifiers.includes(gemsPriceRateIdentifier)) {
          return false;
        }
        gemsPriceRateCollectionIdentifiers.push(gemsPriceRateIdentifier);
        return true;
      });
      return [...gemsPriceRatesToAdd, ...gemsPriceRateCollection];
    }
    return gemsPriceRateCollection;
  }

  protected convertDateFromClient(gemsPriceRate: IGemsPriceRate): IGemsPriceRate {
    return Object.assign({}, gemsPriceRate, {
      effectiveDate: gemsPriceRate.effectiveDate?.isValid() ? gemsPriceRate.effectiveDate.toJSON() : undefined,
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
      res.body.forEach((gemsPriceRate: IGemsPriceRate) => {
        gemsPriceRate.effectiveDate = gemsPriceRate.effectiveDate ? dayjs(gemsPriceRate.effectiveDate) : undefined;
      });
    }
    return res;
  }
}
