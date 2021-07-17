import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GemsTypeComponent } from './list/gems-type.component';
import { GemsTypeDetailComponent } from './detail/gems-type-detail.component';
import { GemsTypeUpdateComponent } from './update/gems-type-update.component';
import { GemsTypeDeleteDialogComponent } from './delete/gems-type-delete-dialog.component';
import { GemsTypeRoutingModule } from './route/gems-type-routing.module';

@NgModule({
  imports: [SharedModule, GemsTypeRoutingModule],
  declarations: [GemsTypeComponent, GemsTypeDetailComponent, GemsTypeUpdateComponent, GemsTypeDeleteDialogComponent],
  entryComponents: [GemsTypeDeleteDialogComponent],
})
export class GemsTypeModule {}
