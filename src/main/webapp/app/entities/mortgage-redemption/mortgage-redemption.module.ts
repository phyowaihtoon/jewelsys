import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MortgageRedemptionRoutingModule } from './route/mortgage-redemption-routing.module';
import { MortgageRedemptionSearchComponent } from './search/mortgage-redemption-search.component';
import { MortgageRedemptionUpdateComponent } from './update/mortgage-redemption-update.component';
import { MortgageRedemptionDetailComponent } from './detail/mortgage-redemption-detail.component';

@NgModule({
  imports: [SharedModule, MortgageRedemptionRoutingModule],
  declarations: [MortgageRedemptionSearchComponent, MortgageRedemptionUpdateComponent, MortgageRedemptionDetailComponent],
})
export class MortgageRedemptionModule {}
