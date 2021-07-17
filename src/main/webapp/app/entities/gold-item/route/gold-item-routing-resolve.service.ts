import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGoldItem, GoldItem } from '../gold-item.model';
import { GoldItemService } from '../service/gold-item.service';

@Injectable({ providedIn: 'root' })
export class GoldItemRoutingResolveService implements Resolve<IGoldItem> {
  constructor(protected service: GoldItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoldItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((goldItem: HttpResponse<GoldItem>) => {
          if (goldItem.body) {
            return of(goldItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoldItem());
  }
}
