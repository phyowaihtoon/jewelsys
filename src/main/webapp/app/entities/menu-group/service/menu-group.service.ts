import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMenuGroup, getMenuGroupIdentifier } from '../menu-group.model';

export type EntityResponseType = HttpResponse<IMenuGroup>;
export type EntityArrayResponseType = HttpResponse<IMenuGroup[]>;

@Injectable({ providedIn: 'root' })
export class MenuGroupService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/menu-groups');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(menuGroup: IMenuGroup): Observable<EntityResponseType> {
    return this.http.post<IMenuGroup>(this.resourceUrl, menuGroup, { observe: 'response' });
  }

  update(menuGroup: IMenuGroup): Observable<EntityResponseType> {
    return this.http.put<IMenuGroup>(`${this.resourceUrl}/${getMenuGroupIdentifier(menuGroup) as number}`, menuGroup, {
      observe: 'response',
    });
  }

  partialUpdate(menuGroup: IMenuGroup): Observable<EntityResponseType> {
    return this.http.patch<IMenuGroup>(`${this.resourceUrl}/${getMenuGroupIdentifier(menuGroup) as number}`, menuGroup, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMenuGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMenuGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMenuGroupToCollectionIfMissing(
    menuGroupCollection: IMenuGroup[],
    ...menuGroupsToCheck: (IMenuGroup | null | undefined)[]
  ): IMenuGroup[] {
    const menuGroups: IMenuGroup[] = menuGroupsToCheck.filter(isPresent);
    if (menuGroups.length > 0) {
      const menuGroupCollectionIdentifiers = menuGroupCollection.map(menuGroupItem => getMenuGroupIdentifier(menuGroupItem)!);
      const menuGroupsToAdd = menuGroups.filter(menuGroupItem => {
        const menuGroupIdentifier = getMenuGroupIdentifier(menuGroupItem);
        if (menuGroupIdentifier == null || menuGroupCollectionIdentifiers.includes(menuGroupIdentifier)) {
          return false;
        }
        menuGroupCollectionIdentifiers.push(menuGroupIdentifier);
        return true;
      });
      return [...menuGroupsToAdd, ...menuGroupCollection];
    }
    return menuGroupCollection;
  }
}
