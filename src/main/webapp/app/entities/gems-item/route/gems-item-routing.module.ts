import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GemsItemComponent } from '../list/gems-item.component';
import { GemsItemDetailComponent } from '../detail/gems-item-detail.component';
import { GemsItemUpdateComponent } from '../update/gems-item-update.component';
import { GemsItemRoutingResolveService } from './gems-item-routing-resolve.service';

const gemsItemRoute: Routes = [
  {
    path: '',
    component: GemsItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GemsItemDetailComponent,
    resolve: {
      gemsItem: GemsItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GemsItemUpdateComponent,
    resolve: {
      gemsItem: GemsItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GemsItemUpdateComponent,
    resolve: {
      gemsItem: GemsItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(gemsItemRoute)],
  exports: [RouterModule],
})
export class GemsItemRoutingModule {}
