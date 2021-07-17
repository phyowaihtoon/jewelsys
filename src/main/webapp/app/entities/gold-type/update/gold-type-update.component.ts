import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IGoldType, GoldType } from '../gold-type.model';
import { GoldTypeService } from '../service/gold-type.service';

@Component({
  selector: 'jhi-gold-type-update',
  templateUrl: './gold-type-update.component.html',
})
export class GoldTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    name: [null, [Validators.required]],
    delFlg: [],
  });

  constructor(protected goldTypeService: GoldTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldType }) => {
      this.updateForm(goldType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goldType = this.createFromForm();
    if (goldType.id !== undefined) {
      this.subscribeToSaveResponse(this.goldTypeService.update(goldType));
    } else {
      this.subscribeToSaveResponse(this.goldTypeService.create(goldType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoldType>>): void {
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

  protected updateForm(goldType: IGoldType): void {
    this.editForm.patchValue({
      id: goldType.id,
      code: goldType.code,
      name: goldType.name,
      delFlg: goldType.delFlg,
    });
  }

  protected createFromForm(): IGoldType {
    return {
      ...new GoldType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
    };
  }
}
