import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import * as dayjs from 'dayjs';
import { MY_DATE_FORMAT } from 'app/config/input.constants';
import { IMortgageEntry, MortgageEntry } from '../mortgage-entry.model';
import { MortgageEntryService } from '../service/mortgage-entry.service';
import { IMortgageItem } from 'app/entities/mortgage-item/mortgage-item.model';
import { MortgageItemService } from 'app/entities/mortgage-item/service/mortgage-item.service';
import { DataCategoryService } from 'app/entities/data-category/service/data-category.service';
import { IDataCategory } from 'app/entities/data-category/data-category.model';

@Component({
  selector: 'jhi-mortgage-entry-update',
  templateUrl: './mortgage-entry-update.component.html',
})
export class MortgageEntryUpdateComponent implements OnInit {
  isSaving = false;

  orgMortgItemCollection: IMortgageItem[] = [];
  mortgageItemCollection: IMortgageItem[] = [];

  mmCalendarCollection: IDataCategory[] = [];
  mmYearCollection: IDataCategory[] = [];
  mmMonthCollection: IDataCategory[] = [];
  mmDayGroupCollection: IDataCategory[] = [];
  mmDayCollection: IDataCategory[] = [];

  mortgageStatusCollection: IDataCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    address: [null, [Validators.required]],
    phone: [],
    groupCode: [null, [Validators.required]],
    itemCode: [null, [Validators.required]],
    damageType: [],
    wInKyat: [],
    wInPae: [],
    wInYway: [],
    principalAmount: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    interestRate: [],
    mmYear: [],
    mmMonth: [],
    mmDayGR: [],
    mmDay: [],
    mortgageStatus: [],
    delFlg: [],
  });

  constructor(
    protected mortgageEntryService: MortgageEntryService,
    protected mortgageItemService: MortgageItemService,
    protected dataCategoryService: DataCategoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mortgageEntry }) => {
      if (mortgageEntry.id === undefined) {
        const today = dayjs().startOf('day');
        mortgageEntry.startDate = today;
      }
      this.updateForm(mortgageEntry);
      this.loadRelationshipsOptions(mortgageEntry.groupCode);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mortgageEntry = this.createFromForm();
    if (mortgageEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.mortgageEntryService.update(mortgageEntry));
    } else {
      this.subscribeToSaveResponse(this.mortgageEntryService.create(mortgageEntry));
    }
  }

  trackMortgageItemByCode(index: number, item: IMortgageItem): string {
    return item.code!;
  }

  onItemGroupChange(): void {
    const selectedGroupCode = this.editForm.get(['groupCode'])!.value;
    this.mortgageItemCollection = this.orgMortgItemCollection.filter(value => value.groupCode === selectedGroupCode);
  }

  trackCategoryByCode(index: number, item: IDataCategory): string {
    return item.categoryCode!;
  }

  onMMYearChange(): void {
    const selectedMMYear = this.editForm.get(['mmYear'])!.value;
    this.mmMonthCollection = [];
    if (selectedMMYear !== undefined && selectedMMYear !== null) {
      const yearArr = selectedMMYear.split('_');
      const monthType = yearArr[0] === 'SW' ? 'EVEN_MM' : 'ODD_MM';
      this.mmMonthCollection = this.mmCalendarCollection.filter(value => value.categoryType === monthType);
    }
  }

  /*
  onDateSelect(event:any) {
    const year = event.year;
    const month = event.month <= 9 ? '0' + event.month : event.month;;
    const day = event.day <= 9 ? '0' + event.day : event.day;;
    const finalDate = day + "-" + month + "-" + year;
   }
  */

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMortgageEntry>>): void {
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

  protected loadRelationshipsOptions(groupCode: string): void {
    this.mortgageItemService
      .loadAll()
      .pipe(map((res: HttpResponse<IMortgageItem[]>) => res.body ?? []))
      .pipe(
        map((mortgageItems: IMortgageItem[]) =>
          this.mortgageItemService.addMortgageItemToCollectionIfMissing(mortgageItems, this.editForm.get('itemCode')!.value)
        )
      )
      .subscribe((mortgageItems: IMortgageItem[]) => {
        this.orgMortgItemCollection = mortgageItems;
        console.log('Group Code saved');
        console.log(groupCode);
        this.mortgageItemCollection = this.orgMortgItemCollection.filter(value => value.groupCode === groupCode);
      });

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

    this.dataCategoryService
      .loadAllByCategoryType('MS_TYPE')
      .pipe(map((res: HttpResponse<IDataCategory[]>) => res.body ?? []))
      .subscribe((mStatusCollection: IDataCategory[]) => {
        this.mortgageStatusCollection = mStatusCollection;
      });
  }

  protected updateForm(mortgageEntry: IMortgageEntry): void {
    this.editForm.patchValue({
      id: mortgageEntry.id,
      name: mortgageEntry.name,
      address: mortgageEntry.address,
      phone: mortgageEntry.phone,
      groupCode: mortgageEntry.groupCode,
      itemCode: mortgageEntry.itemCode,
      damageType: mortgageEntry.damageType,
      wInKyat: mortgageEntry.wInKyat,
      wInPae: mortgageEntry.wInPae,
      wInYway: mortgageEntry.wInYway,
      principalAmount: mortgageEntry.principalAmount,
      startDate: mortgageEntry.startDate,
      interestRate: mortgageEntry.interestRate,
      mmYear: mortgageEntry.mmYear,
      mmMonth: mortgageEntry.mmMonth,
      mmDayGR: mortgageEntry.mmDayGR,
      mmDay: mortgageEntry.mmDay,
      mortgageStatus: mortgageEntry.mortgageStatus,
      delFlg: mortgageEntry.delFlg,
    });
  }

  protected createFromForm(): IMortgageEntry {
    return {
      ...new MortgageEntry(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      groupCode: this.editForm.get(['groupCode'])!.value,
      itemCode: this.editForm.get(['itemCode'])!.value,
      damageType: this.editForm.get(['damageType'])!.value,
      wInKyat: this.editForm.get(['wInKyat'])!.value,
      wInPae: this.editForm.get(['wInPae'])!.value,
      wInYway: this.editForm.get(['wInYway'])!.value,
      principalAmount: this.editForm.get(['principalAmount'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      interestRate: this.editForm.get(['interestRate'])!.value,
      mmYear: this.editForm.get(['mmYear'])!.value,
      mmMonth: this.editForm.get(['mmMonth'])!.value,
      mmDayGR: this.editForm.get(['mmDayGR'])!.value,
      mmDay: this.editForm.get(['mmDay'])!.value,
      mortgageStatus: this.editForm.get(['mortgageStatus'])!.value,
      delFlg: 'N',
    };
  }
}
