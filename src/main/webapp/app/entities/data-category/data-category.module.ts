import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DataCategoryComponent } from './list/data-category.component';
import { DataCategoryDetailComponent } from './detail/data-category-detail.component';
import { DataCategoryUpdateComponent } from './update/data-category-update.component';
import { DataCategoryDeleteDialogComponent } from './delete/data-category-delete-dialog.component';
import { DataCategoryRoutingModule } from './route/data-category-routing.module';

@NgModule({
  imports: [SharedModule, DataCategoryRoutingModule],
  declarations: [DataCategoryComponent, DataCategoryDetailComponent, DataCategoryUpdateComponent, DataCategoryDeleteDialogComponent],
  entryComponents: [DataCategoryDeleteDialogComponent],
})
export class DataCategoryModule {}
