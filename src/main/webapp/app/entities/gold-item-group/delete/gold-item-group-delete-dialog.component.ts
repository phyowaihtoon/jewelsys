import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoldItemGroup } from '../gold-item-group.model';
import { GoldItemGroupService } from '../service/gold-item-group.service';

@Component({
  templateUrl: './gold-item-group-delete-dialog.component.html',
})
export class GoldItemGroupDeleteDialogComponent {
  goldItemGroup?: IGoldItemGroup;

  constructor(protected goldItemGroupService: GoldItemGroupService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goldItemGroupService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
