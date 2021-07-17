import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GoldItemGroupComponent } from './list/gold-item-group.component';
import { GoldItemGroupDetailComponent } from './detail/gold-item-group-detail.component';
import { GoldItemGroupUpdateComponent } from './update/gold-item-group-update.component';
import { GoldItemGroupDeleteDialogComponent } from './delete/gold-item-group-delete-dialog.component';
import { GoldItemGroupRoutingModule } from './route/gold-item-group-routing.module';

@NgModule({
  imports: [SharedModule, GoldItemGroupRoutingModule],
  declarations: [GoldItemGroupComponent, GoldItemGroupDetailComponent, GoldItemGroupUpdateComponent, GoldItemGroupDeleteDialogComponent],
  entryComponents: [GoldItemGroupDeleteDialogComponent],
})
export class GoldItemGroupModule {}
