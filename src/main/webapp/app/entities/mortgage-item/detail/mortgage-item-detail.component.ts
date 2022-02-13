import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMortgageItem } from '../mortgage-item.model';

@Component({
  selector: 'jhi-mortgage-item-detail',
  templateUrl: './mortgage-item-detail.component.html',
})
export class MortgageItemDetailComponent implements OnInit {
  mortgageItem: IMortgageItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mortgageItem }) => {
      this.mortgageItem = mortgageItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
