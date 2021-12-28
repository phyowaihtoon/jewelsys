import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShopInfo, ShopInfo } from '../shop-info.model';
import { ShopInfoService } from '../service/shop-info.service';

@Injectable({ providedIn: 'root' })
export class ShopInfoRoutingResolveService implements Resolve<IShopInfo> {
  constructor(protected service: ShopInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShopInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((shopInfo: HttpResponse<ShopInfo>) => {
          if (shopInfo.body) {
            return of(shopInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ShopInfo());
  }
}
