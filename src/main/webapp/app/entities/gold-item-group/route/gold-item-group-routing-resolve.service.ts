import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGoldItemGroup, GoldItemGroup } from '../gold-item-group.model';
import { GoldItemGroupService } from '../service/gold-item-group.service';

@Injectable({ providedIn: 'root' })
export class GoldItemGroupRoutingResolveService implements Resolve<IGoldItemGroup> {
  constructor(protected service: GoldItemGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoldItemGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((goldItemGroup: HttpResponse<GoldItemGroup>) => {
          if (goldItemGroup.body) {
            return of(goldItemGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoldItemGroup());
  }
}
