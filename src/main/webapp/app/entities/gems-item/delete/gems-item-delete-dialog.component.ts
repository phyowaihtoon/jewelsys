import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGemsItem } from '../gems-item.model';
import { GemsItemService } from '../service/gems-item.service';

@Component({
  templateUrl: './gems-item-delete-dialog.component.html',
})
export class GemsItemDeleteDialogComponent {
  gemsItem?: IGemsItem;

  constructor(protected gemsItemService: GemsItemService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gemsItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
