import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GoldTypeComponent } from '../list/gold-type.component';
import { GoldTypeDetailComponent } from '../detail/gold-type-detail.component';
import { GoldTypeUpdateComponent } from '../update/gold-type-update.component';
import { GoldTypeRoutingResolveService } from './gold-type-routing-resolve.service';

const goldTypeRoute: Routes = [
  {
    path: '',
    component: GoldTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoldTypeDetailComponent,
    resolve: {
      goldType: GoldTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoldTypeUpdateComponent,
    resolve: {
      goldType: GoldTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoldTypeUpdateComponent,
    resolve: {
      goldType: GoldTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(goldTypeRoute)],
  exports: [RouterModule],
})
export class GoldTypeRoutingModule {}
