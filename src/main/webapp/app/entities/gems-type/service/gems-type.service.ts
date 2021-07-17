import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGemsType, getGemsTypeIdentifier } from '../gems-type.model';

export type EntityResponseType = HttpResponse<IGemsType>;
export type EntityArrayResponseType = HttpResponse<IGemsType[]>;

@Injectable({ providedIn: 'root' })
export class GemsTypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/gems-types');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(gemsType: IGemsType): Observable<EntityResponseType> {
    return this.http.post<IGemsType>(this.resourceUrl, gemsType, { observe: 'response' });
  }

  update(gemsType: IGemsType): Observable<EntityResponseType> {
    return this.http.put<IGemsType>(`${this.resourceUrl}/${getGemsTypeIdentifier(gemsType) as number}`, gemsType, { observe: 'response' });
  }

  partialUpdate(gemsType: IGemsType): Observable<EntityResponseType> {
    return this.http.patch<IGemsType>(`${this.resourceUrl}/${getGemsTypeIdentifier(gemsType) as number}`, gemsType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGemsType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGemsType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGemsTypeToCollectionIfMissing(gemsTypeCollection: IGemsType[], ...gemsTypesToCheck: (IGemsType | null | undefined)[]): IGemsType[] {
    const gemsTypes: IGemsType[] = gemsTypesToCheck.filter(isPresent);
    if (gemsTypes.length > 0) {
      const gemsTypeCollectionIdentifiers = gemsTypeCollection.map(gemsTypeItem => getGemsTypeIdentifier(gemsTypeItem)!);
      const gemsTypesToAdd = gemsTypes.filter(gemsTypeItem => {
        const gemsTypeIdentifier = getGemsTypeIdentifier(gemsTypeItem);
        if (gemsTypeIdentifier == null || gemsTypeCollectionIdentifiers.includes(gemsTypeIdentifier)) {
          return false;
        }
        gemsTypeCollectionIdentifiers.push(gemsTypeIdentifier);
        return true;
      });
      return [...gemsTypesToAdd, ...gemsTypeCollection];
    }
    return gemsTypeCollection;
  }
}
