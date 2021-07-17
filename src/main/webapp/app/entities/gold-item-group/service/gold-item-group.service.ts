import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGoldItemGroup, getGoldItemGroupIdentifier } from '../gold-item-group.model';

export type EntityResponseType = HttpResponse<IGoldItemGroup>;
export type EntityArrayResponseType = HttpResponse<IGoldItemGroup[]>;

@Injectable({ providedIn: 'root' })
export class GoldItemGroupService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/gold-item-groups');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(goldItemGroup: IGoldItemGroup): Observable<EntityResponseType> {
    return this.http.post<IGoldItemGroup>(this.resourceUrl, goldItemGroup, { observe: 'response' });
  }

  update(goldItemGroup: IGoldItemGroup): Observable<EntityResponseType> {
    return this.http.put<IGoldItemGroup>(`${this.resourceUrl}/${getGoldItemGroupIdentifier(goldItemGroup) as number}`, goldItemGroup, {
      observe: 'response',
    });
  }

  partialUpdate(goldItemGroup: IGoldItemGroup): Observable<EntityResponseType> {
    return this.http.patch<IGoldItemGroup>(`${this.resourceUrl}/${getGoldItemGroupIdentifier(goldItemGroup) as number}`, goldItemGroup, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoldItemGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoldItemGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGoldItemGroupToCollectionIfMissing(
    goldItemGroupCollection: IGoldItemGroup[],
    ...goldItemGroupsToCheck: (IGoldItemGroup | null | undefined)[]
  ): IGoldItemGroup[] {
    const goldItemGroups: IGoldItemGroup[] = goldItemGroupsToCheck.filter(isPresent);
    if (goldItemGroups.length > 0) {
      const goldItemGroupCollectionIdentifiers = goldItemGroupCollection.map(
        goldItemGroupItem => getGoldItemGroupIdentifier(goldItemGroupItem)!
      );
      const goldItemGroupsToAdd = goldItemGroups.filter(goldItemGroupItem => {
        const goldItemGroupIdentifier = getGoldItemGroupIdentifier(goldItemGroupItem);
        if (goldItemGroupIdentifier == null || goldItemGroupCollectionIdentifiers.includes(goldItemGroupIdentifier)) {
          return false;
        }
        goldItemGroupCollectionIdentifiers.push(goldItemGroupIdentifier);
        return true;
      });
      return [...goldItemGroupsToAdd, ...goldItemGroupCollection];
    }
    return goldItemGroupCollection;
  }
}
