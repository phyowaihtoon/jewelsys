import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGemsType, GemsType } from '../gems-type.model';
import { GemsTypeService } from '../service/gems-type.service';

@Injectable({ providedIn: 'root' })
export class GemsTypeRoutingResolveService implements Resolve<IGemsType> {
  constructor(protected service: GemsTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGemsType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((gemsType: HttpResponse<GemsType>) => {
          if (gemsType.body) {
            return of(gemsType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GemsType());
  }
}
