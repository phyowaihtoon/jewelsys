import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICounterInfo, CounterInfo } from '../counter-info.model';
import { CounterInfoService } from '../service/counter-info.service';
import { IShopInfo } from 'app/entities/shop-info/shop-info.model';
import { ShopInfoService } from 'app/entities/shop-info/service/shop-info.service';

@Component({
  selector: 'jhi-counter-info-update',
  templateUrl: './counter-info-update.component.html',
})
export class CounterInfoUpdateComponent implements OnInit {
  isSaving = false;

  shopInfosSharedCollection: IShopInfo[] = [];

  editForm = this.fb.group({
    id: [],
    counterNo: [null, [Validators.required]],
    counterName: [null, [Validators.required]],
    delFlg: [],
    shopInfo: [],
  });

  constructor(
    protected counterInfoService: CounterInfoService,
    protected shopInfoService: ShopInfoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ counterInfo }) => {
      this.updateForm(counterInfo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const counterInfo = this.createFromForm();
    if (counterInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.counterInfoService.update(counterInfo));
    } else {
      this.subscribeToSaveResponse(this.counterInfoService.create(counterInfo));
    }
  }

  trackShopInfoById(index: number, item: IShopInfo): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICounterInfo>>): void {
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

  protected updateForm(counterInfo: ICounterInfo): void {
    this.editForm.patchValue({
      id: counterInfo.id,
      counterNo: counterInfo.counterNo,
      counterName: counterInfo.counterName,
      delFlg: counterInfo.delFlg,
      shopInfo: counterInfo.shopInfo,
    });

    this.shopInfosSharedCollection = this.shopInfoService.addShopInfoToCollectionIfMissing(
      this.shopInfosSharedCollection,
      counterInfo.shopInfo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.shopInfoService
      .query()
      .pipe(map((res: HttpResponse<IShopInfo[]>) => res.body ?? []))
      .pipe(
        map((shopInfos: IShopInfo[]) =>
          this.shopInfoService.addShopInfoToCollectionIfMissing(shopInfos, this.editForm.get('shopInfo')!.value)
        )
      )
      .subscribe((shopInfos: IShopInfo[]) => (this.shopInfosSharedCollection = shopInfos));
  }

  protected createFromForm(): ICounterInfo {
    return {
      ...new CounterInfo(),
      id: this.editForm.get(['id'])!.value,
      counterNo: this.editForm.get(['counterNo'])!.value,
      counterName: this.editForm.get(['counterName'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
      shopInfo: this.editForm.get(['shopInfo'])!.value,
    };
  }
}
