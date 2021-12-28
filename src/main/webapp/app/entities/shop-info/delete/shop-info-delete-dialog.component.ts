import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IShopInfo } from '../shop-info.model';
import { ShopInfoService } from '../service/shop-info.service';

@Component({
  templateUrl: './shop-info-delete-dialog.component.html',
})
export class ShopInfoDeleteDialogComponent {
  shopInfo?: IShopInfo;

  constructor(protected shopInfoService: ShopInfoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shopInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
