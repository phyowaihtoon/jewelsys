import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGemsType } from '../gems-type.model';
import { GemsTypeService } from '../service/gems-type.service';

@Component({
  templateUrl: './gems-type-delete-dialog.component.html',
})
export class GemsTypeDeleteDialogComponent {
  gemsType?: IGemsType;

  constructor(protected gemsTypeService: GemsTypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gemsTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
