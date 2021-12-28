import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShopInfo, getShopInfoIdentifier } from '../shop-info.model';

export type EntityResponseType = HttpResponse<IShopInfo>;
export type EntityArrayResponseType = HttpResponse<IShopInfo[]>;

@Injectable({ providedIn: 'root' })
export class ShopInfoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/shop-infos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(shopInfo: IShopInfo): Observable<EntityResponseType> {
    return this.http.post<IShopInfo>(this.resourceUrl, shopInfo, { observe: 'response' });
  }

  update(shopInfo: IShopInfo): Observable<EntityResponseType> {
    return this.http.put<IShopInfo>(`${this.resourceUrl}/${getShopInfoIdentifier(shopInfo) as number}`, shopInfo, { observe: 'response' });
  }

  partialUpdate(shopInfo: IShopInfo): Observable<EntityResponseType> {
    return this.http.patch<IShopInfo>(`${this.resourceUrl}/${getShopInfoIdentifier(shopInfo) as number}`, shopInfo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShopInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShopInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addShopInfoToCollectionIfMissing(shopInfoCollection: IShopInfo[], ...shopInfosToCheck: (IShopInfo | null | undefined)[]): IShopInfo[] {
    const shopInfos: IShopInfo[] = shopInfosToCheck.filter(isPresent);
    if (shopInfos.length > 0) {
      const shopInfoCollectionIdentifiers = shopInfoCollection.map(shopInfoItem => getShopInfoIdentifier(shopInfoItem)!);
      const shopInfosToAdd = shopInfos.filter(shopInfoItem => {
        const shopInfoIdentifier = getShopInfoIdentifier(shopInfoItem);
        if (shopInfoIdentifier == null || shopInfoCollectionIdentifiers.includes(shopInfoIdentifier)) {
          return false;
        }
        shopInfoCollectionIdentifiers.push(shopInfoIdentifier);
        return true;
      });
      return [...shopInfosToAdd, ...shopInfoCollection];
    }
    return shopInfoCollection;
  }
}
