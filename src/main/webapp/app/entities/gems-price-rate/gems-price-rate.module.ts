import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GemsPriceRateComponent } from './list/gems-price-rate.component';
import { GemsPriceRateDetailComponent } from './detail/gems-price-rate-detail.component';
import { GemsPriceRateUpdateComponent } from './update/gems-price-rate-update.component';
import { GemsPriceRateDeleteDialogComponent } from './delete/gems-price-rate-delete-dialog.component';
import { GemsPriceRateRoutingModule } from './route/gems-price-rate-routing.module';

@NgModule({
  imports: [SharedModule, GemsPriceRateRoutingModule],
  declarations: [GemsPriceRateComponent, GemsPriceRateDetailComponent, GemsPriceRateUpdateComponent, GemsPriceRateDeleteDialogComponent],
  entryComponents: [GemsPriceRateDeleteDialogComponent],
})
export class GemsPriceRateModule {}
