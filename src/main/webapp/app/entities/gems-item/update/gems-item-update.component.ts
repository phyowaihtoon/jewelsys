import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IGemsItem, GemsItem } from '../gems-item.model';
import { GemsItemService } from '../service/gems-item.service';
import { IGemsType } from 'app/entities/gems-type/gems-type.model';
import { GemsTypeService } from 'app/entities/gems-type/service/gems-type.service';

@Component({
  selector: 'jhi-gems-item-update',
  templateUrl: './gems-item-update.component.html',
})
export class GemsItemUpdateComponent implements OnInit {
  isSaving = false;

  gemsTypesSharedCollection: IGemsType[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    name: [null, [Validators.required]],
    delFlg: [],
    gemsType: [],
  });

  constructor(
    protected gemsItemService: GemsItemService,
    protected gemsTypeService: GemsTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gemsItem }) => {
      this.updateForm(gemsItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gemsItem = this.createFromForm();
    if (gemsItem.id !== undefined) {
      this.subscribeToSaveResponse(this.gemsItemService.update(gemsItem));
    } else {
      this.subscribeToSaveResponse(this.gemsItemService.create(gemsItem));
    }
  }

  trackGemsTypeById(index: number, item: IGemsType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGemsItem>>): void {
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

  protected updateForm(gemsItem: IGemsItem): void {
    this.editForm.patchValue({
      id: gemsItem.id,
      code: gemsItem.code,
      name: gemsItem.name,
      delFlg: gemsItem.delFlg,
      gemsType: gemsItem.gemsType,
    });

    this.gemsTypesSharedCollection = this.gemsTypeService.addGemsTypeToCollectionIfMissing(
      this.gemsTypesSharedCollection,
      gemsItem.gemsType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.gemsTypeService
      .query()
      .pipe(map((res: HttpResponse<IGemsType[]>) => res.body ?? []))
      .pipe(
        map((gemsTypes: IGemsType[]) =>
          this.gemsTypeService.addGemsTypeToCollectionIfMissing(gemsTypes, this.editForm.get('gemsType')!.value)
        )
      )
      .subscribe((gemsTypes: IGemsType[]) => (this.gemsTypesSharedCollection = gemsTypes));
  }

  protected createFromForm(): IGemsItem {
    return {
      ...new GemsItem(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
      gemsType: this.editForm.get(['gemsType'])!.value,
    };
  }
}
