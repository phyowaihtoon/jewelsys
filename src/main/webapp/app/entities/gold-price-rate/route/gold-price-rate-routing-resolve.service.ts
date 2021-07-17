import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGoldPriceRate, GoldPriceRate } from '../gold-price-rate.model';
import { GoldPriceRateService } from '../service/gold-price-rate.service';

@Injectable({ providedIn: 'root' })
export class GoldPriceRateRoutingResolveService implements Resolve<IGoldPriceRate> {
  constructor(protected service: GoldPriceRateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoldPriceRate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((goldPriceRate: HttpResponse<GoldPriceRate>) => {
          if (goldPriceRate.body) {
            return of(goldPriceRate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoldPriceRate());
  }
}
