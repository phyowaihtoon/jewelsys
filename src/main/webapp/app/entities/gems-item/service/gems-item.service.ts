import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGemsItem, getGemsItemIdentifier } from '../gems-item.model';

export type EntityResponseType = HttpResponse<IGemsItem>;
export type EntityArrayResponseType = HttpResponse<IGemsItem[]>;

@Injectable({ providedIn: 'root' })
export class GemsItemService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/gems-items');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(gemsItem: IGemsItem): Observable<EntityResponseType> {
    return this.http.post<IGemsItem>(this.resourceUrl, gemsItem, { observe: 'response' });
  }

  update(gemsItem: IGemsItem): Observable<EntityResponseType> {
    return this.http.put<IGemsItem>(`${this.resourceUrl}/${getGemsItemIdentifier(gemsItem) as number}`, gemsItem, { observe: 'response' });
  }

  partialUpdate(gemsItem: IGemsItem): Observable<EntityResponseType> {
    return this.http.patch<IGemsItem>(`${this.resourceUrl}/${getGemsItemIdentifier(gemsItem) as number}`, gemsItem, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGemsItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGemsItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGemsItemToCollectionIfMissing(gemsItemCollection: IGemsItem[], ...gemsItemsToCheck: (IGemsItem | null | undefined)[]): IGemsItem[] {
    const gemsItems: IGemsItem[] = gemsItemsToCheck.filter(isPresent);
    if (gemsItems.length > 0) {
      const gemsItemCollectionIdentifiers = gemsItemCollection.map(gemsItemItem => getGemsItemIdentifier(gemsItemItem)!);
      const gemsItemsToAdd = gemsItems.filter(gemsItemItem => {
        const gemsItemIdentifier = getGemsItemIdentifier(gemsItemItem);
        if (gemsItemIdentifier == null || gemsItemCollectionIdentifiers.includes(gemsItemIdentifier)) {
          return false;
        }
        gemsItemCollectionIdentifiers.push(gemsItemIdentifier);
        return true;
      });
      return [...gemsItemsToAdd, ...gemsItemCollection];
    }
    return gemsItemCollection;
  }
}
