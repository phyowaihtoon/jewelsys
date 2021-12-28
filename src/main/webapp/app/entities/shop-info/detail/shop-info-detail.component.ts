import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShopInfo } from '../shop-info.model';

@Component({
  selector: 'jhi-shop-info-detail',
  templateUrl: './shop-info-detail.component.html',
})
export class ShopInfoDetailComponent implements OnInit {
  shopInfo: IShopInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shopInfo }) => {
      this.shopInfo = shopInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
