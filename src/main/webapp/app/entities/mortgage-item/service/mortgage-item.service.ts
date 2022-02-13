import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMortgageItem, getMortgageItemIdentifier } from '../mortgage-item.model';

export type EntityResponseType = HttpResponse<IMortgageItem>;
export type EntityArrayResponseType = HttpResponse<IMortgageItem[]>;

@Injectable({ providedIn: 'root' })
export class MortgageItemService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/mortgage-items');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(mortgageItem: IMortgageItem): Observable<EntityResponseType> {
    return this.http.post<IMortgageItem>(this.resourceUrl, mortgageItem, { observe: 'response' });
  }

  update(mortgageItem: IMortgageItem): Observable<EntityResponseType> {
    return this.http.put<IMortgageItem>(`${this.resourceUrl}/${getMortgageItemIdentifier(mortgageItem) as number}`, mortgageItem, {
      observe: 'response',
    });
  }

  partialUpdate(mortgageItem: IMortgageItem): Observable<EntityResponseType> {
    return this.http.patch<IMortgageItem>(`${this.resourceUrl}/${getMortgageItemIdentifier(mortgageItem) as number}`, mortgageItem, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMortgageItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMortgageItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  loadAll(): Observable<EntityArrayResponseType> {
    return this.http.get<IMortgageItem[]>(`${this.resourceUrl}/loadall`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMortgageItemToCollectionIfMissing(
    mortgageItemCollection: IMortgageItem[],
    ...mortgageItemsToCheck: (IMortgageItem | null | undefined)[]
  ): IMortgageItem[] {
    const mortgageItems: IMortgageItem[] = mortgageItemsToCheck.filter(isPresent);
    if (mortgageItems.length > 0) {
      const mortgageItemCollectionIdentifiers = mortgageItemCollection.map(
        mortgageItemItem => getMortgageItemIdentifier(mortgageItemItem)!
      );
      const mortgageItemsToAdd = mortgageItems.filter(mortgageItemItem => {
        const mortgageItemIdentifier = getMortgageItemIdentifier(mortgageItemItem);
        if (mortgageItemIdentifier == null || mortgageItemCollectionIdentifiers.includes(mortgageItemIdentifier)) {
          return false;
        }
        mortgageItemCollectionIdentifiers.push(mortgageItemIdentifier);
        return true;
      });
      return [...mortgageItemsToAdd, ...mortgageItemCollection];
    }
    return mortgageItemCollection;
  }
}
