import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGoldType, GoldType } from '../gold-type.model';
import { GoldTypeService } from '../service/gold-type.service';

@Injectable({ providedIn: 'root' })
export class GoldTypeRoutingResolveService implements Resolve<IGoldType> {
  constructor(protected service: GoldTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoldType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((goldType: HttpResponse<GoldType>) => {
          if (goldType.body) {
            return of(goldType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoldType());
  }
}
