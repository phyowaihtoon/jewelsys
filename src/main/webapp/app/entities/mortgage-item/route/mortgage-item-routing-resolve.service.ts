import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMortgageItem, MortgageItem } from '../mortgage-item.model';
import { MortgageItemService } from '../service/mortgage-item.service';

@Injectable({ providedIn: 'root' })
export class MortgageItemRoutingResolveService implements Resolve<IMortgageItem> {
  constructor(protected service: MortgageItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMortgageItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mortgageItem: HttpResponse<MortgageItem>) => {
          if (mortgageItem.body) {
            return of(mortgageItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MortgageItem());
  }
}
