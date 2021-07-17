import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GemsPriceRateComponent } from '../list/gems-price-rate.component';
import { GemsPriceRateDetailComponent } from '../detail/gems-price-rate-detail.component';
import { GemsPriceRateUpdateComponent } from '../update/gems-price-rate-update.component';
import { GemsPriceRateRoutingResolveService } from './gems-price-rate-routing-resolve.service';

const gemsPriceRateRoute: Routes = [
  {
    path: '',
    component: GemsPriceRateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GemsPriceRateDetailComponent,
    resolve: {
      gemsPriceRate: GemsPriceRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GemsPriceRateUpdateComponent,
    resolve: {
      gemsPriceRate: GemsPriceRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GemsPriceRateUpdateComponent,
    resolve: {
      gemsPriceRate: GemsPriceRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(gemsPriceRateRoute)],
  exports: [RouterModule],
})
export class GemsPriceRateRoutingModule {}
