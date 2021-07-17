import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGoldItem, getGoldItemIdentifier } from '../gold-item.model';

export type EntityResponseType = HttpResponse<IGoldItem>;
export type EntityArrayResponseType = HttpResponse<IGoldItem[]>;

@Injectable({ providedIn: 'root' })
export class GoldItemService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/gold-items');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(goldItem: IGoldItem): Observable<EntityResponseType> {
    return this.http.post<IGoldItem>(this.resourceUrl, goldItem, { observe: 'response' });
  }

  update(goldItem: IGoldItem): Observable<EntityResponseType> {
    return this.http.put<IGoldItem>(`${this.resourceUrl}/${getGoldItemIdentifier(goldItem) as number}`, goldItem, { observe: 'response' });
  }

  partialUpdate(goldItem: IGoldItem): Observable<EntityResponseType> {
    return this.http.patch<IGoldItem>(`${this.resourceUrl}/${getGoldItemIdentifier(goldItem) as number}`, goldItem, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoldItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoldItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGoldItemToCollectionIfMissing(goldItemCollection: IGoldItem[], ...goldItemsToCheck: (IGoldItem | null | undefined)[]): IGoldItem[] {
    const goldItems: IGoldItem[] = goldItemsToCheck.filter(isPresent);
    if (goldItems.length > 0) {
      const goldItemCollectionIdentifiers = goldItemCollection.map(goldItemItem => getGoldItemIdentifier(goldItemItem)!);
      const goldItemsToAdd = goldItems.filter(goldItemItem => {
        const goldItemIdentifier = getGoldItemIdentifier(goldItemItem);
        if (goldItemIdentifier == null || goldItemCollectionIdentifiers.includes(goldItemIdentifier)) {
          return false;
        }
        goldItemCollectionIdentifiers.push(goldItemIdentifier);
        return true;
      });
      return [...goldItemsToAdd, ...goldItemCollection];
    }
    return goldItemCollection;
  }
}
