import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGemsType } from '../gems-type.model';

@Component({
  selector: 'jhi-gems-type-detail',
  templateUrl: './gems-type-detail.component.html',
})
export class GemsTypeDetailComponent implements OnInit {
  gemsType: IGemsType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gemsType }) => {
      this.gemsType = gemsType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
