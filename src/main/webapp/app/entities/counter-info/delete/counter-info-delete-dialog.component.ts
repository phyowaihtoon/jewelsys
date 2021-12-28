import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICounterInfo } from '../counter-info.model';
import { CounterInfoService } from '../service/counter-info.service';

@Component({
  templateUrl: './counter-info-delete-dialog.component.html',
})
export class CounterInfoDeleteDialogComponent {
  counterInfo?: ICounterInfo;

  constructor(protected counterInfoService: CounterInfoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.counterInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
