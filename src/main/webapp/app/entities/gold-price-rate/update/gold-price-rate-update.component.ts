import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IGoldPriceRate, GoldPriceRate } from '../gold-price-rate.model';
import { GoldPriceRateService } from '../service/gold-price-rate.service';
import { IGoldType } from 'app/entities/gold-type/gold-type.model';
import { GoldTypeService } from 'app/entities/gold-type/service/gold-type.service';

@Component({
  selector: 'jhi-gold-price-rate-update',
  templateUrl: './gold-price-rate-update.component.html',
})
export class GoldPriceRateUpdateComponent implements OnInit {
  isSaving = false;

  goldTypesSharedCollection: IGoldType[] = [];

  editForm = this.fb.group({
    id: [],
    effectiveDate: [null, [Validators.required]],
    rateSrNo: [null, [Validators.required]],
    rateType: [null, [Validators.required]],
    priceRate: [],
    delFlg: [],
    goldType: [],
  });

  constructor(
    protected goldPriceRateService: GoldPriceRateService,
    protected goldTypeService: GoldTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldPriceRate }) => {
      if (goldPriceRate.id === undefined) {
        const today = dayjs().startOf('day');
        goldPriceRate.effectiveDate = today;
      }

      this.updateForm(goldPriceRate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goldPriceRate = this.createFromForm();
    if (goldPriceRate.id !== undefined) {
      this.subscribeToSaveResponse(this.goldPriceRateService.update(goldPriceRate));
    } else {
      this.subscribeToSaveResponse(this.goldPriceRateService.create(goldPriceRate));
    }
  }

  trackGoldTypeById(index: number, item: IGoldType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoldPriceRate>>): void {
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

  protected updateForm(goldPriceRate: IGoldPriceRate): void {
    this.editForm.patchValue({
      id: goldPriceRate.id,
      effectiveDate: goldPriceRate.effectiveDate ? goldPriceRate.effectiveDate.format(DATE_TIME_FORMAT) : null,
      rateSrNo: goldPriceRate.rateSrNo,
      rateType: goldPriceRate.rateType,
      priceRate: goldPriceRate.priceRate,
      delFlg: goldPriceRate.delFlg,
      goldType: goldPriceRate.goldType,
    });

    this.goldTypesSharedCollection = this.goldTypeService.addGoldTypeToCollectionIfMissing(
      this.goldTypesSharedCollection,
      goldPriceRate.goldType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.goldTypeService
      .query()
      .pipe(map((res: HttpResponse<IGoldType[]>) => res.body ?? []))
      .pipe(
        map((goldTypes: IGoldType[]) =>
          this.goldTypeService.addGoldTypeToCollectionIfMissing(goldTypes, this.editForm.get('goldType')!.value)
        )
      )
      .subscribe((goldTypes: IGoldType[]) => (this.goldTypesSharedCollection = goldTypes));
  }

  protected createFromForm(): IGoldPriceRate {
    return {
      ...new GoldPriceRate(),
      id: this.editForm.get(['id'])!.value,
      effectiveDate: this.editForm.get(['effectiveDate'])!.value
        ? dayjs(this.editForm.get(['effectiveDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      rateSrNo: this.editForm.get(['rateSrNo'])!.value,
      rateType: this.editForm.get(['rateType'])!.value,
      priceRate: this.editForm.get(['priceRate'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
      goldType: this.editForm.get(['goldType'])!.value,
    };
  }
}
