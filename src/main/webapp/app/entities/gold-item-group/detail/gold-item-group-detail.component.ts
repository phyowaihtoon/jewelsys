import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoldItemGroup } from '../gold-item-group.model';

@Component({
  selector: 'jhi-gold-item-group-detail',
  templateUrl: './gold-item-group-detail.component.html',
})
export class GoldItemGroupDetailComponent implements OnInit {
  goldItemGroup: IGoldItemGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldItemGroup }) => {
      this.goldItemGroup = goldItemGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
