import { Component, OnInit } from '@angular/core';
import { IMortgageEntry } from 'app/entities/mortgage-entry/mortgage-entry.model';
import { IMortgageRedemption, MortgageRedemption } from '../mortgage-redemption.model';
import * as dayjs from 'dayjs';
import { FormBuilder, Validators } from '@angular/forms';
import { IDataCategory } from 'app/entities/data-category/data-category.model';
import { HttpResponse } from '@angular/common/http';
import { DataCategoryService } from 'app/entities/data-category/service/data-category.service';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { MortgageRedemptionService } from '../service/mortgage-redemption.service';

@Component({
  selector: 'jhi-mortgage-redemption-update',
  templateUrl: './mortgage-redemption-update.component.html',
  styleUrls: ['./mortgage-redemption-update.component.scss'],
})
export class MortgageRedemptionUpdateComponent implements OnInit {
  isSaving = false;
  meridian = true;
  mortgageRedemption: IMortgageRedemption | null = {};
  mortgageEntry: IMortgageEntry | undefined = {};
  mmCalendarCollection: IDataCategory[] = [];
  mmYearCollection: IDataCategory[] = [];
  mmMonthCollection: IDataCategory[] = [];
  mmDayGroupCollection: IDataCategory[] = [];
  mmDayCollection: IDataCategory[] = [];

  mrTime = { hour: 0, minute: 0 };

  redemptionForm = this.fb.group({
    interestAmount: [null, Validators.required],
    mrDate: [null],
    mrTime: [{ hour: '00', minute: '00' }],
    mrMMYear: [null],
    mrMMMonth: [null],
    mrMMDayGR: [null],
    mrMMDay: [null],
  });

  constructor(
    protected fb: FormBuilder,
    protected dataCategoryService: DataCategoryService,
    protected mrService: MortgageRedemptionService
  ) {}

  ngOnInit(): void {
    this.mortgageRedemption = history.state.data;
    if (this.mortgageRedemption) {
      this.mortgageEntry = this.mortgageRedemption.entryDTO;
      if (this.mortgageEntry) {
        this.mortgageEntry.startDate = this.mortgageEntry.startDate ? dayjs(this.mortgageEntry.startDate) : undefined;
      }
    }

    this.loadRelationshipsOptions();
  }

  save(): void {
    this.isSaving = true;
    const mortgageRedemptionData = this.createFromForm();
    this.subscribeToSaveResponse(this.mrService.create(mortgageRedemptionData));
  }

  onMMYearChange(): void {
    const selectedMMYear = this.redemptionForm.get(['mrMMYear'])!.value;
    this.mmMonthCollection = [];
    if (selectedMMYear !== undefined && selectedMMYear !== null) {
      const yearArr = selectedMMYear.split('_');
      const monthType = yearArr[0] === 'SW' ? 'EVEN_MM' : 'ODD_MM';
      this.mmMonthCollection = this.mmCalendarCollection.filter(value => value.categoryType === monthType);
    }
  }

  trackCategoryByCode(index: number, item: IDataCategory): string {
    return item.categoryCode!;
  }

  previousState(): void {
    window.history.back();
  }

  protected loadRelationshipsOptions(): void {
    this.dataCategoryService
      .loadMMCalendar()
      .pipe(map((res: HttpResponse<IDataCategory[]>) => res.body ?? []))
      .subscribe((calendarData: IDataCategory[]) => {
        this.mmCalendarCollection = calendarData;
        this.mmYearCollection = this.mmCalendarCollection.filter(value => value.categoryType === 'MM_YEAR');
        this.mmMonthCollection = this.mmCalendarCollection.filter(value => value.categoryType === 'EVEN_MM');
        this.mmDayGroupCollection = this.mmCalendarCollection.filter(value => value.categoryType === 'MM_DAY_GR');
        this.mmDayCollection = this.mmCalendarCollection.filter(value => value.categoryType === 'MM_DAY');
      });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMortgageRedemption>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected createFromForm(): IMortgageRedemption {
    return {
      ...new MortgageRedemption(),
      mortgageID: this.mortgageEntry?.id,
      interestAmount: this.redemptionForm.get(['interestAmount'])!.value,
      mrDate: this.redemptionForm.get(['mrDate'])!.value,
      mrTime: this.redemptionForm.get(['mrTime'])!.value,
      mrMMYear: this.redemptionForm.get(['mrMMYear'])!.value,
      mrMMMonth: this.redemptionForm.get(['mrMMMonth'])!.value,
      mrMMDayGR: this.redemptionForm.get(['mrMMDayGR'])!.value,
      mrMMDay: this.redemptionForm.get(['mrMMDay'])!.value,
    };
  }
}
