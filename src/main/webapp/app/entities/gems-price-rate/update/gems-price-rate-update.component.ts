import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IGemsPriceRate, GemsPriceRate } from '../gems-price-rate.model';
import { GemsPriceRateService } from '../service/gems-price-rate.service';
import { IGemsItem } from 'app/entities/gems-item/gems-item.model';
import { GemsItemService } from 'app/entities/gems-item/service/gems-item.service';

@Component({
  selector: 'jhi-gems-price-rate-update',
  templateUrl: './gems-price-rate-update.component.html',
})
export class GemsPriceRateUpdateComponent implements OnInit {
  isSaving = false;

  gemsItemsSharedCollection: IGemsItem[] = [];

  editForm = this.fb.group({
    id: [],
    effectiveDate: [null, [Validators.required]],
    rateSrNo: [null, [Validators.required]],
    rateType: [null, [Validators.required]],
    priceRate: [],
    delFlg: [],
    gemsItem: [],
  });

  constructor(
    protected gemsPriceRateService: GemsPriceRateService,
    protected gemsItemService: GemsItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gemsPriceRate }) => {
      if (gemsPriceRate.id === undefined) {
        const today = dayjs().startOf('day');
        gemsPriceRate.effectiveDate = today;
      }

      this.updateForm(gemsPriceRate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gemsPriceRate = this.createFromForm();
    if (gemsPriceRate.id !== undefined) {
      this.subscribeToSaveResponse(this.gemsPriceRateService.update(gemsPriceRate));
    } else {
      this.subscribeToSaveResponse(this.gemsPriceRateService.create(gemsPriceRate));
    }
  }

  trackGemsItemById(index: number, item: IGemsItem): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGemsPriceRate>>): void {
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

  protected updateForm(gemsPriceRate: IGemsPriceRate): void {
    this.editForm.patchValue({
      id: gemsPriceRate.id,
      effectiveDate: gemsPriceRate.effectiveDate ? gemsPriceRate.effectiveDate.format(DATE_TIME_FORMAT) : null,
      rateSrNo: gemsPriceRate.rateSrNo,
      rateType: gemsPriceRate.rateType,
      priceRate: gemsPriceRate.priceRate,
      delFlg: gemsPriceRate.delFlg,
      gemsItem: gemsPriceRate.gemsItem,
    });

    this.gemsItemsSharedCollection = this.gemsItemService.addGemsItemToCollectionIfMissing(
      this.gemsItemsSharedCollection,
      gemsPriceRate.gemsItem
    );
  }

  protected loadRelationshipsOptions(): void {
    this.gemsItemService
      .query()
      .pipe(map((res: HttpResponse<IGemsItem[]>) => res.body ?? []))
      .pipe(
        map((gemsItems: IGemsItem[]) =>
          this.gemsItemService.addGemsItemToCollectionIfMissing(gemsItems, this.editForm.get('gemsItem')!.value)
        )
      )
      .subscribe((gemsItems: IGemsItem[]) => (this.gemsItemsSharedCollection = gemsItems));
  }

  protected createFromForm(): IGemsPriceRate {
    return {
      ...new GemsPriceRate(),
      id: this.editForm.get(['id'])!.value,
      effectiveDate: this.editForm.get(['effectiveDate'])!.value
        ? dayjs(this.editForm.get(['effectiveDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      rateSrNo: this.editForm.get(['rateSrNo'])!.value,
      rateType: this.editForm.get(['rateType'])!.value,
      priceRate: this.editForm.get(['priceRate'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
      gemsItem: this.editForm.get(['gemsItem'])!.value,
    };
  }
}
