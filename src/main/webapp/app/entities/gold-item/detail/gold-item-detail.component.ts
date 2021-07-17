import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoldItem } from '../gold-item.model';

@Component({
  selector: 'jhi-gold-item-detail',
  templateUrl: './gold-item-detail.component.html',
})
export class GoldItemDetailComponent implements OnInit {
  goldItem: IGoldItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldItem }) => {
      this.goldItem = goldItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
