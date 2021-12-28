import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ShopInfoComponent } from './list/shop-info.component';
import { ShopInfoDetailComponent } from './detail/shop-info-detail.component';
import { ShopInfoUpdateComponent } from './update/shop-info-update.component';
import { ShopInfoDeleteDialogComponent } from './delete/shop-info-delete-dialog.component';
import { ShopInfoRoutingModule } from './route/shop-info-routing.module';

@NgModule({
  imports: [SharedModule, ShopInfoRoutingModule],
  declarations: [ShopInfoComponent, ShopInfoDetailComponent, ShopInfoUpdateComponent, ShopInfoDeleteDialogComponent],
  entryComponents: [ShopInfoDeleteDialogComponent],
})
export class ShopInfoModule {}
