import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMenuGroup } from '../menu-group.model';

@Component({
  selector: 'jhi-menu-group-detail',
  templateUrl: './menu-group-detail.component.html',
})
export class MenuGroupDetailComponent implements OnInit {
  menuGroup: IMenuGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ menuGroup }) => {
      this.menuGroup = menuGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
