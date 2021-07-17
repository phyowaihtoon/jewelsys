import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IGoldItemGroup, GoldItemGroup } from '../gold-item-group.model';
import { GoldItemGroupService } from '../service/gold-item-group.service';
import { IGoldType } from 'app/entities/gold-type/gold-type.model';
import { GoldTypeService } from 'app/entities/gold-type/service/gold-type.service';

@Component({
  selector: 'jhi-gold-item-group-update',
  templateUrl: './gold-item-group-update.component.html',
})
export class GoldItemGroupUpdateComponent implements OnInit {
  isSaving = false;

  goldTypesSharedCollection: IGoldType[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    name: [null, [Validators.required]],
    delFlg: [],
    goldType: [],
  });

  constructor(
    protected goldItemGroupService: GoldItemGroupService,
    protected goldTypeService: GoldTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldItemGroup }) => {
      this.updateForm(goldItemGroup);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goldItemGroup = this.createFromForm();
    if (goldItemGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.goldItemGroupService.update(goldItemGroup));
    } else {
      this.subscribeToSaveResponse(this.goldItemGroupService.create(goldItemGroup));
    }
  }

  trackGoldTypeById(index: number, item: IGoldType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoldItemGroup>>): void {
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

  protected updateForm(goldItemGroup: IGoldItemGroup): void {
    this.editForm.patchValue({
      id: goldItemGroup.id,
      code: goldItemGroup.code,
      name: goldItemGroup.name,
      delFlg: goldItemGroup.delFlg,
      goldType: goldItemGroup.goldType,
    });

    this.goldTypesSharedCollection = this.goldTypeService.addGoldTypeToCollectionIfMissing(
      this.goldTypesSharedCollection,
      goldItemGroup.goldType
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

  protected createFromForm(): IGoldItemGroup {
    return {
      ...new GoldItemGroup(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
      goldType: this.editForm.get(['goldType'])!.value,
    };
  }
}
