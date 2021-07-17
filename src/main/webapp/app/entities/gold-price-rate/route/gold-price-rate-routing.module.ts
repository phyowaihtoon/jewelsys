import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GoldPriceRateComponent } from '../list/gold-price-rate.component';
import { GoldPriceRateDetailComponent } from '../detail/gold-price-rate-detail.component';
import { GoldPriceRateUpdateComponent } from '../update/gold-price-rate-update.component';
import { GoldPriceRateRoutingResolveService } from './gold-price-rate-routing-resolve.service';

const goldPriceRateRoute: Routes = [
  {
    path: '',
    component: GoldPriceRateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoldPriceRateDetailComponent,
    resolve: {
      goldPriceRate: GoldPriceRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoldPriceRateUpdateComponent,
    resolve: {
      goldPriceRate: GoldPriceRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoldPriceRateUpdateComponent,
    resolve: {
      goldPriceRate: GoldPriceRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(goldPriceRateRoute)],
  exports: [RouterModule],
})
export class GoldPriceRateRoutingModule {}
