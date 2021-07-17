import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GemsItemComponent } from './list/gems-item.component';
import { GemsItemDetailComponent } from './detail/gems-item-detail.component';
import { GemsItemUpdateComponent } from './update/gems-item-update.component';
import { GemsItemDeleteDialogComponent } from './delete/gems-item-delete-dialog.component';
import { GemsItemRoutingModule } from './route/gems-item-routing.module';

@NgModule({
  imports: [SharedModule, GemsItemRoutingModule],
  declarations: [GemsItemComponent, GemsItemDetailComponent, GemsItemUpdateComponent, GemsItemDeleteDialogComponent],
  entryComponents: [GemsItemDeleteDialogComponent],
})
export class GemsItemModule {}
