import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGemsItem, GemsItem } from '../gems-item.model';
import { GemsItemService } from '../service/gems-item.service';

@Injectable({ providedIn: 'root' })
export class GemsItemRoutingResolveService implements Resolve<IGemsItem> {
  constructor(protected service: GemsItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGemsItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((gemsItem: HttpResponse<GemsItem>) => {
          if (gemsItem.body) {
            return of(gemsItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GemsItem());
  }
}
