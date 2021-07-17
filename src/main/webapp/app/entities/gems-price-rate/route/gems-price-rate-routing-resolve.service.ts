import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGemsPriceRate, GemsPriceRate } from '../gems-price-rate.model';
import { GemsPriceRateService } from '../service/gems-price-rate.service';

@Injectable({ providedIn: 'root' })
export class GemsPriceRateRoutingResolveService implements Resolve<IGemsPriceRate> {
  constructor(protected service: GemsPriceRateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGemsPriceRate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((gemsPriceRate: HttpResponse<GemsPriceRate>) => {
          if (gemsPriceRate.body) {
            return of(gemsPriceRate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GemsPriceRate());
  }
}
