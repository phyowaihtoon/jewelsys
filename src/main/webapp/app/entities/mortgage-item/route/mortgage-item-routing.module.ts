import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MortgageItemComponent } from '../list/mortgage-item.component';
import { MortgageItemDetailComponent } from '../detail/mortgage-item-detail.component';
import { MortgageItemUpdateComponent } from '../update/mortgage-item-update.component';
import { MortgageItemRoutingResolveService } from './mortgage-item-routing-resolve.service';

const mortgageItemRoute: Routes = [
  {
    path: '',
    component: MortgageItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MortgageItemDetailComponent,
    resolve: {
      mortgageItem: MortgageItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MortgageItemUpdateComponent,
    resolve: {
      mortgageItem: MortgageItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MortgageItemUpdateComponent,
    resolve: {
      mortgageItem: MortgageItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mortgageItemRoute)],
  exports: [RouterModule],
})
export class MortgageItemRoutingModule {}
