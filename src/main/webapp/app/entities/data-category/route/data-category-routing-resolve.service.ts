import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataCategory, DataCategory } from '../data-category.model';
import { DataCategoryService } from '../service/data-category.service';

@Injectable({ providedIn: 'root' })
export class DataCategoryRoutingResolveService implements Resolve<IDataCategory> {
  constructor(protected service: DataCategoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDataCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dataCategory: HttpResponse<DataCategory>) => {
          if (dataCategory.body) {
            return of(dataCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DataCategory());
  }
}
