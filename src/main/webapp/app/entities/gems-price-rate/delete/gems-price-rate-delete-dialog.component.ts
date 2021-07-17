import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGemsPriceRate } from '../gems-price-rate.model';
import { GemsPriceRateService } from '../service/gems-price-rate.service';

@Component({
  templateUrl: './gems-price-rate-delete-dialog.component.html',
})
export class GemsPriceRateDeleteDialogComponent {
  gemsPriceRate?: IGemsPriceRate;

  constructor(protected gemsPriceRateService: GemsPriceRateService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gemsPriceRateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
