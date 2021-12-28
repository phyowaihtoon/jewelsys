import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICounterInfo } from '../counter-info.model';

@Component({
  selector: 'jhi-counter-info-detail',
  templateUrl: './counter-info-detail.component.html',
})
export class CounterInfoDetailComponent implements OnInit {
  counterInfo: ICounterInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ counterInfo }) => {
      this.counterInfo = counterInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
