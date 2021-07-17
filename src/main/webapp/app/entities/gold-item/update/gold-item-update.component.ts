import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IGoldItem, GoldItem } from '../gold-item.model';
import { GoldItemService } from '../service/gold-item.service';
import { IGoldItemGroup } from 'app/entities/gold-item-group/gold-item-group.model';
import { GoldItemGroupService } from 'app/entities/gold-item-group/service/gold-item-group.service';

@Component({
  selector: 'jhi-gold-item-update',
  templateUrl: './gold-item-update.component.html',
})
export class GoldItemUpdateComponent implements OnInit {
  isSaving = false;

  goldItemGroupsSharedCollection: IGoldItemGroup[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    name: [null, [Validators.required]],
    delFlg: [],
    goldItemGroup: [],
  });

  constructor(
    protected goldItemService: GoldItemService,
    protected goldItemGroupService: GoldItemGroupService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldItem }) => {
      this.updateForm(goldItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goldItem = this.createFromForm();
    if (goldItem.id !== undefined) {
      this.subscribeToSaveResponse(this.goldItemService.update(goldItem));
    } else {
      this.subscribeToSaveResponse(this.goldItemService.create(goldItem));
    }
  }

  trackGoldItemGroupById(index: number, item: IGoldItemGroup): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoldItem>>): void {
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

  protected updateForm(goldItem: IGoldItem): void {
    this.editForm.patchValue({
      id: goldItem.id,
      code: goldItem.code,
      name: goldItem.name,
      delFlg: goldItem.delFlg,
      goldItemGroup: goldItem.goldItemGroup,
    });

    this.goldItemGroupsSharedCollection = this.goldItemGroupService.addGoldItemGroupToCollectionIfMissing(
      this.goldItemGroupsSharedCollection,
      goldItem.goldItemGroup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.goldItemGroupService
      .query()
      .pipe(map((res: HttpResponse<IGoldItemGroup[]>) => res.body ?? []))
      .pipe(
        map((goldItemGroups: IGoldItemGroup[]) =>
          this.goldItemGroupService.addGoldItemGroupToCollectionIfMissing(goldItemGroups, this.editForm.get('goldItemGroup')!.value)
        )
      )
      .subscribe((goldItemGroups: IGoldItemGroup[]) => (this.goldItemGroupsSharedCollection = goldItemGroups));
  }

  protected createFromForm(): IGoldItem {
    return {
      ...new GoldItem(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      delFlg: this.editForm.get(['delFlg'])!.value,
      goldItemGroup: this.editForm.get(['goldItemGroup'])!.value,
    };
  }
}
