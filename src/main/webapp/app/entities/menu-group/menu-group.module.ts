import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MenuGroupComponent } from './list/menu-group.component';
import { MenuGroupDetailComponent } from './detail/menu-group-detail.component';
import { MenuGroupUpdateComponent } from './update/menu-group-update.component';
import { MenuGroupDeleteDialogComponent } from './delete/menu-group-delete-dialog.component';
import { MenuGroupRoutingModule } from './route/menu-group-routing.module';

@NgModule({
  imports: [SharedModule, MenuGroupRoutingModule],
  declarations: [MenuGroupComponent, MenuGroupDetailComponent, MenuGroupUpdateComponent, MenuGroupDeleteDialogComponent],
  entryComponents: [MenuGroupDeleteDialogComponent],
})
export class MenuGroupModule {}
