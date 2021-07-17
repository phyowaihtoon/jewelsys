import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoldType } from '../gold-type.model';

@Component({
  selector: 'jhi-gold-type-detail',
  templateUrl: './gold-type-detail.component.html',
})
export class GoldTypeDetailComponent implements OnInit {
  goldType: IGoldType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldType }) => {
      this.goldType = goldType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
