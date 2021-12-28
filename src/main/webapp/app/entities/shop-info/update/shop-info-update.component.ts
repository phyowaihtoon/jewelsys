import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IShopInfo, ShopInfo } from '../shop-info.model';
import { ShopInfoService } from '../service/shop-info.service';

@Component({
  selector: 'jhi-shop-info-update',
  templateUrl: './shop-info-update.component.html',
})
export class ShopInfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    shopCode: [null, [Validators.required]],
    nameEng: [null, [Validators.required]],
    nameMyan: [],
    addrEng: [],
    addrMyan: [],
    phone: [],
    delFlg: [],
  });

  constructor(protected shopInfoService: ShopInfoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shopInfo }) => {
      this.updateForm(shopInfo);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shopInfo = this.createFromForm();
    if (shopInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.shopInfoService.update(shopInfo));
    } else {
      this.subscribeToSaveResponse(this.shopInfoService.create(shopInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShopInfo>>): void {
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

  protected updateForm(shopInfo: IShopInfo): void {
    this.editForm.patchValue({
      id: shopInfo.id,
      shopCode: shopInfo.shopCode,
      nameEng: shopInfo.nameEng,
      nameMyan: shopInfo.nameMyan,
      addrEng: shopInfo.addrEng,
      addrMyan: shopInfo.addrMyan,
      phone: shopInfo.phone,
      delFlg: shopInfo.delFlg,
    });
  }

  protected createFromForm(): IShopInfo {
    return {
      ...new ShopInfo(),
      id: this.editForm.get(['id'])!.value,
      shopCode: this.editForm.get(['shopCode'])!.value,
      nameEng: this.editForm.get(['nameEng'])!.value,
      nameMyan: this.editForm.get(['nameMyan'])!.value,
      addrEng: this.editForm.get(['addrEng'])!.value,
      addrMyan: this.editForm.get(['addrMyan'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
    };
  }
}
