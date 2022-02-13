import { Component, ElementRef, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMortgageEntry, MortgageEntry } from '../mortgage-entry.model';
import { MortgageEntryService } from '../service/mortgage-entry.service';
import { IMortgageItem } from 'app/entities/mortgage-item/mortgage-item.model';
import { MortgageItemService } from 'app/entities/mortgage-item/service/mortgage-item.service';

@Component({
  selector: 'jhi-mortgage-entry-update',
  templateUrl: './mortgage-entry-update.component.html',
})
export class MortgageEntryUpdateComponent implements OnInit {
  isSaving = false;

  orgMortgItemCollection: IMortgageItem[] = [];
  mortgageItemCollection: IMortgageItem[] = [];

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
    term: [],
    delFlg: [],
  });

  constructor(
    protected mortgageEntryService: MortgageEntryService,
    protected mortgageItemService: MortgageItemService,
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
      startDate: mortgageEntry.startDate ? mortgageEntry.startDate.format(DATE_TIME_FORMAT) : null,
      interestRate: mortgageEntry.interestRate,
      term: mortgageEntry.term,
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
      startDate: this.editForm.get(['startDate'])!.value ? dayjs(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      interestRate: this.editForm.get(['interestRate'])!.value,
      term: this.editForm.get(['term'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
    };
  }
}
