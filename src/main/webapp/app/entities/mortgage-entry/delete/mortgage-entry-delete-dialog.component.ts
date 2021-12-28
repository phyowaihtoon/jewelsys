import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMortgageEntry } from '../mortgage-entry.model';
import { MortgageEntryService } from '../service/mortgage-entry.service';

@Component({
  templateUrl: './mortgage-entry-delete-dialog.component.html',
})
export class MortgageEntryDeleteDialogComponent {
  mortgageEntry?: IMortgageEntry;

  constructor(protected mortgageEntryService: MortgageEntryService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mortgageEntryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
