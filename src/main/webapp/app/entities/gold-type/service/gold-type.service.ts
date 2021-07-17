import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGoldType, getGoldTypeIdentifier } from '../gold-type.model';

export type EntityResponseType = HttpResponse<IGoldType>;
export type EntityArrayResponseType = HttpResponse<IGoldType[]>;

@Injectable({ providedIn: 'root' })
export class GoldTypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/gold-types');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(goldType: IGoldType): Observable<EntityResponseType> {
    return this.http.post<IGoldType>(this.resourceUrl, goldType, { observe: 'response' });
  }

  update(goldType: IGoldType): Observable<EntityResponseType> {
    return this.http.put<IGoldType>(`${this.resourceUrl}/${getGoldTypeIdentifier(goldType) as number}`, goldType, { observe: 'response' });
  }

  partialUpdate(goldType: IGoldType): Observable<EntityResponseType> {
    return this.http.patch<IGoldType>(`${this.resourceUrl}/${getGoldTypeIdentifier(goldType) as number}`, goldType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoldType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoldType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGoldTypeToCollectionIfMissing(goldTypeCollection: IGoldType[], ...goldTypesToCheck: (IGoldType | null | undefined)[]): IGoldType[] {
    const goldTypes: IGoldType[] = goldTypesToCheck.filter(isPresent);
    if (goldTypes.length > 0) {
      const goldTypeCollectionIdentifiers = goldTypeCollection.map(goldTypeItem => getGoldTypeIdentifier(goldTypeItem)!);
      const goldTypesToAdd = goldTypes.filter(goldTypeItem => {
        const goldTypeIdentifier = getGoldTypeIdentifier(goldTypeItem);
        if (goldTypeIdentifier == null || goldTypeCollectionIdentifiers.includes(goldTypeIdentifier)) {
          return false;
        }
        goldTypeCollectionIdentifiers.push(goldTypeIdentifier);
        return true;
      });
      return [...goldTypesToAdd, ...goldTypeCollection];
    }
    return goldTypeCollection;
  }
}
