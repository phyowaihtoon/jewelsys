import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GoldPriceRateComponent } from './list/gold-price-rate.component';
import { GoldPriceRateDetailComponent } from './detail/gold-price-rate-detail.component';
import { GoldPriceRateUpdateComponent } from './update/gold-price-rate-update.component';
import { GoldPriceRateDeleteDialogComponent } from './delete/gold-price-rate-delete-dialog.component';
import { GoldPriceRateRoutingModule } from './route/gold-price-rate-routing.module';

@NgModule({
  imports: [SharedModule, GoldPriceRateRoutingModule],
  declarations: [GoldPriceRateComponent, GoldPriceRateDetailComponent, GoldPriceRateUpdateComponent, GoldPriceRateDeleteDialogComponent],
  entryComponents: [GoldPriceRateDeleteDialogComponent],
})
export class GoldPriceRateModule {}
