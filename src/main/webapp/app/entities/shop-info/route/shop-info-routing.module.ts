import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShopInfoComponent } from '../list/shop-info.component';
import { ShopInfoDetailComponent } from '../detail/shop-info-detail.component';
import { ShopInfoUpdateComponent } from '../update/shop-info-update.component';
import { ShopInfoRoutingResolveService } from './shop-info-routing-resolve.service';

const shopInfoRoute: Routes = [
  {
    path: '',
    component: ShopInfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShopInfoDetailComponent,
    resolve: {
      shopInfo: ShopInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShopInfoUpdateComponent,
    resolve: {
      shopInfo: ShopInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShopInfoUpdateComponent,
    resolve: {
      shopInfo: ShopInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(shopInfoRoute)],
  exports: [RouterModule],
})
export class ShopInfoRoutingModule {}
