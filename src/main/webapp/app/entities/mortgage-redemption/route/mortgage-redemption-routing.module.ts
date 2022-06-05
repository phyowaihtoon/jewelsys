import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MortgageRedemptionDetailComponent } from '../detail/mortgage-redemption-detail.component';
import { MortgageRedemptionSearchComponent } from '../search/mortgage-redemption-search.component';
import { MortgageRedemptionUpdateComponent } from '../update/mortgage-redemption-update.component';

const mortgageRedemptionRoutes: Routes = [
  {
    path: '',
    component: MortgageRedemptionSearchComponent,
  },
  {
    path: 'add',
    component: MortgageRedemptionUpdateComponent,
  },
  {
    path: 'detail',
    component: MortgageRedemptionDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(mortgageRedemptionRoutes)],
  exports: [RouterModule],
})
export class MortgageRedemptionRoutingModule {}
