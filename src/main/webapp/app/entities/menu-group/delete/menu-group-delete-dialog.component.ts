import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMenuGroup } from '../menu-group.model';
import { MenuGroupService } from '../service/menu-group.service';

@Component({
  templateUrl: './menu-group-delete-dialog.component.html',
})
export class MenuGroupDeleteDialogComponent {
  menuGroup?: IMenuGroup;

  constructor(protected menuGroupService: MenuGroupService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.menuGroupService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
