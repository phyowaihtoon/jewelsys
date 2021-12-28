import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MortgageEntryComponent } from './list/mortgage-entry.component';
import { MortgageEntryDetailComponent } from './detail/mortgage-entry-detail.component';
import { MortgageEntryUpdateComponent } from './update/mortgage-entry-update.component';
import { MortgageEntryDeleteDialogComponent } from './delete/mortgage-entry-delete-dialog.component';
import { MortgageEntryRoutingModule } from './route/mortgage-entry-routing.module';

@NgModule({
  imports: [SharedModule, MortgageEntryRoutingModule],
  declarations: [MortgageEntryComponent, MortgageEntryDetailComponent, MortgageEntryUpdateComponent, MortgageEntryDeleteDialogComponent],
  entryComponents: [MortgageEntryDeleteDialogComponent],
})
export class MortgageEntryModule {}
