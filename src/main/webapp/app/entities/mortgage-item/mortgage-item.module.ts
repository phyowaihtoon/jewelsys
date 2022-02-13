import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MortgageItemComponent } from './list/mortgage-item.component';
import { MortgageItemDetailComponent } from './detail/mortgage-item-detail.component';
import { MortgageItemUpdateComponent } from './update/mortgage-item-update.component';
import { MortgageItemDeleteDialogComponent } from './delete/mortgage-item-delete-dialog.component';
import { MortgageItemRoutingModule } from './route/mortgage-item-routing.module';

@NgModule({
  imports: [SharedModule, MortgageItemRoutingModule],
  declarations: [MortgageItemComponent, MortgageItemDetailComponent, MortgageItemUpdateComponent, MortgageItemDeleteDialogComponent],
  entryComponents: [MortgageItemDeleteDialogComponent],
})
export class MortgageItemModule {}
