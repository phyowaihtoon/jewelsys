import { Component, OnInit } from '@angular/core';
import { IMortgageEntry } from 'app/entities/mortgage-entry/mortgage-entry.model';
import { IMortgageRedemption } from '../mortgage-redemption.model';
import * as dayjs from 'dayjs';

@Component({
  selector: 'jhi-mortgage-redemption-detail',
  templateUrl: './mortgage-redemption-detail.component.html',
  styleUrls: ['./mortgage-redemption-detail.component.scss'],
})
export class MortgageRedemptionDetailComponent implements OnInit {
  mortgageRedemption: IMortgageRedemption | undefined = {};
  mortgageEntry: IMortgageEntry | undefined = {};

  ngOnInit(): void {
    this.mortgageRedemption = history.state.data;
    if (this.mortgageRedemption) {
      this.mortgageRedemption.mrDate = this.mortgageRedemption.mrDate ? dayjs(this.mortgageRedemption.mrDate) : undefined;
      this.mortgageEntry = this.mortgageRedemption.entryDTO;
      if (this.mortgageEntry) {
        this.mortgageEntry.startDate = this.mortgageEntry.startDate ? dayjs(this.mortgageEntry.startDate) : undefined;
      }
    }
  }

  previousState(): void {
    window.history.back();
  }
}
