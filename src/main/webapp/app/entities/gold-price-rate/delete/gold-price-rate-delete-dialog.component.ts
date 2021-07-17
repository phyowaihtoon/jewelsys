import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoldPriceRate } from '../gold-price-rate.model';
import { GoldPriceRateService } from '../service/gold-price-rate.service';

@Component({
  templateUrl: './gold-price-rate-delete-dialog.component.html',
})
export class GoldPriceRateDeleteDialogComponent {
  goldPriceRate?: IGoldPriceRate;

  constructor(protected goldPriceRateService: GoldPriceRateService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goldPriceRateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
