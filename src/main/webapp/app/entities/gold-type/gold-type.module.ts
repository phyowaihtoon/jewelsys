import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GoldTypeComponent } from './list/gold-type.component';
import { GoldTypeDetailComponent } from './detail/gold-type-detail.component';
import { GoldTypeUpdateComponent } from './update/gold-type-update.component';
import { GoldTypeDeleteDialogComponent } from './delete/gold-type-delete-dialog.component';
import { GoldTypeRoutingModule } from './route/gold-type-routing.module';

@NgModule({
  imports: [SharedModule, GoldTypeRoutingModule],
  declarations: [GoldTypeComponent, GoldTypeDetailComponent, GoldTypeUpdateComponent, GoldTypeDeleteDialogComponent],
  entryComponents: [GoldTypeDeleteDialogComponent],
})
export class GoldTypeModule {}
