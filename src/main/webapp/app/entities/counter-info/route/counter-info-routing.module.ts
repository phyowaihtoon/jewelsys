import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CounterInfoComponent } from '../list/counter-info.component';
import { CounterInfoDetailComponent } from '../detail/counter-info-detail.component';
import { CounterInfoUpdateComponent } from '../update/counter-info-update.component';
import { CounterInfoRoutingResolveService } from './counter-info-routing-resolve.service';

const counterInfoRoute: Routes = [
  {
    path: '',
    component: CounterInfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CounterInfoDetailComponent,
    resolve: {
      counterInfo: CounterInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CounterInfoUpdateComponent,
    resolve: {
      counterInfo: CounterInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CounterInfoUpdateComponent,
    resolve: {
      counterInfo: CounterInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(counterInfoRoute)],
  exports: [RouterModule],
})
export class CounterInfoRoutingModule {}
