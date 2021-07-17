import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMenuGroup, MenuGroup } from '../menu-group.model';
import { MenuGroupService } from '../service/menu-group.service';

@Injectable({ providedIn: 'root' })
export class MenuGroupRoutingResolveService implements Resolve<IMenuGroup> {
  constructor(protected service: MenuGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMenuGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((menuGroup: HttpResponse<MenuGroup>) => {
          if (menuGroup.body) {
            return of(menuGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MenuGroup());
  }
}
