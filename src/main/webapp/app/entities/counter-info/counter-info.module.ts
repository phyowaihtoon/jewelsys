import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CounterInfoComponent } from './list/counter-info.component';
import { CounterInfoDetailComponent } from './detail/counter-info-detail.component';
import { CounterInfoUpdateComponent } from './update/counter-info-update.component';
import { CounterInfoDeleteDialogComponent } from './delete/counter-info-delete-dialog.component';
import { CounterInfoRoutingModule } from './route/counter-info-routing.module';

@NgModule({
  imports: [SharedModule, CounterInfoRoutingModule],
  declarations: [CounterInfoComponent, CounterInfoDetailComponent, CounterInfoUpdateComponent, CounterInfoDeleteDialogComponent],
  entryComponents: [CounterInfoDeleteDialogComponent],
})
export class CounterInfoModule {}
