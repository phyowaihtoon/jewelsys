import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MenuGroupComponent } from '../list/menu-group.component';
import { MenuGroupDetailComponent } from '../detail/menu-group-detail.component';
import { MenuGroupUpdateComponent } from '../update/menu-group-update.component';
import { MenuGroupRoutingResolveService } from './menu-group-routing-resolve.service';

const menuGroupRoute: Routes = [
  {
    path: '',
    component: MenuGroupComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MenuGroupDetailComponent,
    resolve: {
      menuGroup: MenuGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MenuGroupUpdateComponent,
    resolve: {
      menuGroup: MenuGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MenuGroupUpdateComponent,
    resolve: {
      menuGroup: MenuGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(menuGroupRoute)],
  exports: [RouterModule],
})
export class MenuGroupRoutingModule {}
