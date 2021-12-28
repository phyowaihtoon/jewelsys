import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICounterInfo, CounterInfo } from '../counter-info.model';
import { CounterInfoService } from '../service/counter-info.service';

@Injectable({ providedIn: 'root' })
export class CounterInfoRoutingResolveService implements Resolve<ICounterInfo> {
  constructor(protected service: CounterInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICounterInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((counterInfo: HttpResponse<CounterInfo>) => {
          if (counterInfo.body) {
            return of(counterInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CounterInfo());
  }
}
