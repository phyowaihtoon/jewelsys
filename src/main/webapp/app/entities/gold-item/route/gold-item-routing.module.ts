import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GoldItemComponent } from '../list/gold-item.component';
import { GoldItemDetailComponent } from '../detail/gold-item-detail.component';
import { GoldItemUpdateComponent } from '../update/gold-item-update.component';
import { GoldItemRoutingResolveService } from './gold-item-routing-resolve.service';

const goldItemRoute: Routes = [
  {
    path: '',
    component: GoldItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoldItemDetailComponent,
    resolve: {
      goldItem: GoldItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoldItemUpdateComponent,
    resolve: {
      goldItem: GoldItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoldItemUpdateComponent,
    resolve: {
      goldItem: GoldItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(goldItemRoute)],
  exports: [RouterModule],
})
export class GoldItemRoutingModule {}
