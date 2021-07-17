import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoldType } from '../gold-type.model';
import { GoldTypeService } from '../service/gold-type.service';

@Component({
  templateUrl: './gold-type-delete-dialog.component.html',
})
export class GoldTypeDeleteDialogComponent {
  goldType?: IGoldType;

  constructor(protected goldTypeService: GoldTypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goldTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
