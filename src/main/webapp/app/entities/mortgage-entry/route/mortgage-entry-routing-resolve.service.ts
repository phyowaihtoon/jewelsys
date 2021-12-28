import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMortgageEntry, MortgageEntry } from '../mortgage-entry.model';
import { MortgageEntryService } from '../service/mortgage-entry.service';

@Injectable({ providedIn: 'root' })
export class MortgageEntryRoutingResolveService implements Resolve<IMortgageEntry> {
  constructor(protected service: MortgageEntryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMortgageEntry> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mortgageEntry: HttpResponse<MortgageEntry>) => {
          if (mortgageEntry.body) {
            return of(mortgageEntry.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MortgageEntry());
  }
}
