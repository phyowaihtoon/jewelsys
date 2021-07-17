import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGemsPriceRate } from '../gems-price-rate.model';

@Component({
  selector: 'jhi-gems-price-rate-detail',
  templateUrl: './gems-price-rate-detail.component.html',
})
export class GemsPriceRateDetailComponent implements OnInit {
  gemsPriceRate: IGemsPriceRate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gemsPriceRate }) => {
      this.gemsPriceRate = gemsPriceRate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
