import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoldPriceRate } from '../gold-price-rate.model';

@Component({
  selector: 'jhi-gold-price-rate-detail',
  templateUrl: './gold-price-rate-detail.component.html',
})
export class GoldPriceRateDetailComponent implements OnInit {
  goldPriceRate: IGoldPriceRate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldPriceRate }) => {
      this.goldPriceRate = goldPriceRate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
