import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMortgageItem } from '../mortgage-item.model';
import { MortgageItemService } from '../service/mortgage-item.service';

@Component({
  templateUrl: './mortgage-item-delete-dialog.component.html',
})
export class MortgageItemDeleteDialogComponent {
  mortgageItem?: IMortgageItem;

  constructor(protected mortgageItemService: MortgageItemService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mortgageItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
