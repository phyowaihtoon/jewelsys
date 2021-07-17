import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoldItem } from '../gold-item.model';
import { GoldItemService } from '../service/gold-item.service';

@Component({
  templateUrl: './gold-item-delete-dialog.component.html',
})
export class GoldItemDeleteDialogComponent {
  goldItem?: IGoldItem;

  constructor(protected goldItemService: GoldItemService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goldItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
