import { Component, OnInit } from '@angular/core';
import { IMortgageEntry } from 'app/entities/mortgage-entry/mortgage-entry.model';
import { IMortgageRedemption } from '../mortgage-redemption.model';
import * as dayjs from 'dayjs';
import { ITimeStructure } from 'app/shared/model/time-structure';

@Component({
  selector: 'jhi-mortgage-redemption-detail',
  templateUrl: './mortgage-redemption-detail.component.html',
  styleUrls: ['./mortgage-redemption-detail.component.scss'],
})
export class MortgageRedemptionDetailComponent implements OnInit {
  mortgageRedemption: IMortgageRedemption | undefined = {};
  mortgageEntry: IMortgageEntry | undefined = {};
  mrTimeSpinners=false;
  mrTimeMeridian=true;
  mrTimeReadOnly=true;
  mrTimeModel:ITimeStructure={};

  ngOnInit(): void {
    this.mortgageRedemption = history.state.data;
    if (this.mortgageRedemption) {
      this.mortgageRedemption.mrDate = this.mortgageRedemption.mrDate ? dayjs(this.mortgageRedemption.mrDate) : undefined;
      const mrTimeData=this.mortgageRedemption.mrTime;
      this.mrTimeModel=this.toTimeStructure(mrTimeData);
      this.mortgageEntry = this.mortgageRedemption.entryDTO;
      if (this.mortgageEntry) {
        this.mortgageEntry.startDate = this.mortgageEntry.startDate ? dayjs(this.mortgageEntry.startDate) : undefined;
      }
    }
  }

  previousState(): void {
    window.history.back();
  }

  private toTimeStructure(mrTimeData: string | undefined):ITimeStructure {
    if (mrTimeData?.includes(":")) {
      const mrTimeString = mrTimeData.split(":");
      const mrHour = mrTimeString[0];
      const mrMinute = mrTimeString[1];
      return { hour: +mrHour, minute: +mrMinute };
    }else{
      return {};
    }
  }


}
