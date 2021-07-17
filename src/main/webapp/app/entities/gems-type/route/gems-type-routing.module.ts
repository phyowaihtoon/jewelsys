import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GemsTypeComponent } from '../list/gems-type.component';
import { GemsTypeDetailComponent } from '../detail/gems-type-detail.component';
import { GemsTypeUpdateComponent } from '../update/gems-type-update.component';
import { GemsTypeRoutingResolveService } from './gems-type-routing-resolve.service';

const gemsTypeRoute: Routes = [
  {
    path: '',
    component: GemsTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GemsTypeDetailComponent,
    resolve: {
      gemsType: GemsTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GemsTypeUpdateComponent,
    resolve: {
      gemsType: GemsTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GemsTypeUpdateComponent,
    resolve: {
      gemsType: GemsTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(gemsTypeRoute)],
  exports: [RouterModule],
})
export class GemsTypeRoutingModule {}
