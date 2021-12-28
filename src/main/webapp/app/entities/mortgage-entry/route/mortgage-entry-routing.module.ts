import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MortgageEntryComponent } from '../list/mortgage-entry.component';
import { MortgageEntryDetailComponent } from '../detail/mortgage-entry-detail.component';
import { MortgageEntryUpdateComponent } from '../update/mortgage-entry-update.component';
import { MortgageEntryRoutingResolveService } from './mortgage-entry-routing-resolve.service';

const mortgageEntryRoute: Routes = [
  {
    path: '',
    component: MortgageEntryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MortgageEntryDetailComponent,
    resolve: {
      mortgageEntry: MortgageEntryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MortgageEntryUpdateComponent,
    resolve: {
      mortgageEntry: MortgageEntryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MortgageEntryUpdateComponent,
    resolve: {
      mortgageEntry: MortgageEntryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mortgageEntryRoute)],
  exports: [RouterModule],
})
export class MortgageEntryRoutingModule {}
