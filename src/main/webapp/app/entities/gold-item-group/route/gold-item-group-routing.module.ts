import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GoldItemGroupComponent } from '../list/gold-item-group.component';
import { GoldItemGroupDetailComponent } from '../detail/gold-item-group-detail.component';
import { GoldItemGroupUpdateComponent } from '../update/gold-item-group-update.component';
import { GoldItemGroupRoutingResolveService } from './gold-item-group-routing-resolve.service';

const goldItemGroupRoute: Routes = [
  {
    path: '',
    component: GoldItemGroupComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoldItemGroupDetailComponent,
    resolve: {
      goldItemGroup: GoldItemGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoldItemGroupUpdateComponent,
    resolve: {
      goldItemGroup: GoldItemGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoldItemGroupUpdateComponent,
    resolve: {
      goldItemGroup: GoldItemGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(goldItemGroupRoute)],
  exports: [RouterModule],
})
export class GoldItemGroupRoutingModule {}
