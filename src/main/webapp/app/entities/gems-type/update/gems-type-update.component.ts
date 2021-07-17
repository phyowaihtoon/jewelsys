import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IGemsType, GemsType } from '../gems-type.model';
import { GemsTypeService } from '../service/gems-type.service';

@Component({
  selector: 'jhi-gems-type-update',
  templateUrl: './gems-type-update.component.html',
})
export class GemsTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    name: [null, [Validators.required]],
    delFlg: [],
  });

  constructor(protected gemsTypeService: GemsTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gemsType }) => {
      this.updateForm(gemsType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gemsType = this.createFromForm();
    if (gemsType.id !== undefined) {
      this.subscribeToSaveResponse(this.gemsTypeService.update(gemsType));
    } else {
      this.subscribeToSaveResponse(this.gemsTypeService.create(gemsType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGemsType>>): void {
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

  protected updateForm(gemsType: IGemsType): void {
    this.editForm.patchValue({
      id: gemsType.id,
      code: gemsType.code,
      name: gemsType.name,
      delFlg: gemsType.delFlg,
    });
  }

  protected createFromForm(): IGemsType {
    return {
      ...new GemsType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
    };
  }
}
