import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDataCategory } from '../data-category.model';
import { DataCategoryService } from '../service/data-category.service';

@Component({
  templateUrl: './data-category-delete-dialog.component.html',
})
export class DataCategoryDeleteDialogComponent {
  dataCategory?: IDataCategory;

  constructor(protected dataCategoryService: DataCategoryService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dataCategoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
