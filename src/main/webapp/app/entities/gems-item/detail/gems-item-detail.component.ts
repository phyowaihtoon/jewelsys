import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGemsItem } from '../gems-item.model';

@Component({
  selector: 'jhi-gems-item-detail',
  templateUrl: './gems-item-detail.component.html',
})
export class GemsItemDetailComponent implements OnInit {
  gemsItem: IGemsItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gemsItem }) => {
      this.gemsItem = gemsItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
