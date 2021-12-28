import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICounterInfo, getCounterInfoIdentifier } from '../counter-info.model';

export type EntityResponseType = HttpResponse<ICounterInfo>;
export type EntityArrayResponseType = HttpResponse<ICounterInfo[]>;

@Injectable({ providedIn: 'root' })
export class CounterInfoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/counter-infos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(counterInfo: ICounterInfo): Observable<EntityResponseType> {
    return this.http.post<ICounterInfo>(this.resourceUrl, counterInfo, { observe: 'response' });
  }

  update(counterInfo: ICounterInfo): Observable<EntityResponseType> {
    return this.http.put<ICounterInfo>(`${this.resourceUrl}/${getCounterInfoIdentifier(counterInfo) as number}`, counterInfo, {
      observe: 'response',
    });
  }

  partialUpdate(counterInfo: ICounterInfo): Observable<EntityResponseType> {
    return this.http.patch<ICounterInfo>(`${this.resourceUrl}/${getCounterInfoIdentifier(counterInfo) as number}`, counterInfo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICounterInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICounterInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCounterInfoToCollectionIfMissing(
    counterInfoCollection: ICounterInfo[],
    ...counterInfosToCheck: (ICounterInfo | null | undefined)[]
  ): ICounterInfo[] {
    const counterInfos: ICounterInfo[] = counterInfosToCheck.filter(isPresent);
    if (counterInfos.length > 0) {
      const counterInfoCollectionIdentifiers = counterInfoCollection.map(counterInfoItem => getCounterInfoIdentifier(counterInfoItem)!);
      const counterInfosToAdd = counterInfos.filter(counterInfoItem => {
        const counterInfoIdentifier = getCounterInfoIdentifier(counterInfoItem);
        if (counterInfoIdentifier == null || counterInfoCollectionIdentifiers.includes(counterInfoIdentifier)) {
          return false;
        }
        counterInfoCollectionIdentifiers.push(counterInfoIdentifier);
        return true;
      });
      return [...counterInfosToAdd, ...counterInfoCollection];
    }
    return counterInfoCollection;
  }
}
