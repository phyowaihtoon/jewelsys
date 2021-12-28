import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMortgageEntry } from '../mortgage-entry.model';

@Component({
  selector: 'jhi-mortgage-entry-detail',
  templateUrl: './mortgage-entry-detail.component.html',
})
export class MortgageEntryDetailComponent implements OnInit {
  mortgageEntry: IMortgageEntry | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mortgageEntry }) => {
      this.mortgageEntry = mortgageEntry;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
