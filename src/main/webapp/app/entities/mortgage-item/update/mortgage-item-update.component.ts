import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMortgageItem, MortgageItem } from '../mortgage-item.model';
import { MortgageItemService } from '../service/mortgage-item.service';

@Component({
  selector: 'jhi-mortgage-item-update',
  templateUrl: './mortgage-item-update.component.html',
})
export class MortgageItemUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    groupCode: [null, [Validators.required]],
    code: [null, [Validators.required]],
    itemName: [null, [Validators.required]],
    delFlg: [],
  });

  constructor(protected mortgageItemService: MortgageItemService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mortgageItem }) => {
      this.updateForm(mortgageItem);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mortgageItem = this.createFromForm();
    if (mortgageItem.id !== undefined) {
      this.subscribeToSaveResponse(this.mortgageItemService.update(mortgageItem));
    } else {
      this.subscribeToSaveResponse(this.mortgageItemService.create(mortgageItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMortgageItem>>): void {
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

  protected updateForm(mortgageItem: IMortgageItem): void {
    this.editForm.patchValue({
      id: mortgageItem.id,
      groupCode: mortgageItem.groupCode,
      code: mortgageItem.code,
      itemName: mortgageItem.itemName,
      delFlg: mortgageItem.delFlg,
    });
  }

  protected createFromForm(): IMortgageItem {
    return {
      ...new MortgageItem(),
      id: this.editForm.get(['id'])!.value,
      groupCode: this.editForm.get(['groupCode'])!.value,
      code: this.editForm.get(['code'])!.value,
      itemName: this.editForm.get(['itemName'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
    };
  }
}
