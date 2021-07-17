import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GoldItemComponent } from './list/gold-item.component';
import { GoldItemDetailComponent } from './detail/gold-item-detail.component';
import { GoldItemUpdateComponent } from './update/gold-item-update.component';
import { GoldItemDeleteDialogComponent } from './delete/gold-item-delete-dialog.component';
import { GoldItemRoutingModule } from './route/gold-item-routing.module';

@NgModule({
  imports: [SharedModule, GoldItemRoutingModule],
  declarations: [GoldItemComponent, GoldItemDetailComponent, GoldItemUpdateComponent, GoldItemDeleteDialogComponent],
  entryComponents: [GoldItemDeleteDialogComponent],
})
export class GoldItemModule {}
