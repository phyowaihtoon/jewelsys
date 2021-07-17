import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDataCategory, DataCategory } from '../data-category.model';
import { DataCategoryService } from '../service/data-category.service';

@Component({
  selector: 'jhi-data-category-update',
  templateUrl: './data-category-update.component.html',
})
export class DataCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    categoryType: [],
    categoryCode: [],
    value: [],
    categoryDesc: [],
  });

  constructor(protected dataCategoryService: DataCategoryService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataCategory }) => {
      this.updateForm(dataCategory);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dataCategory = this.createFromForm();
    if (dataCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.dataCategoryService.update(dataCategory));
    } else {
      this.subscribeToSaveResponse(this.dataCategoryService.create(dataCategory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataCategory>>): void {
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

  protected updateForm(dataCategory: IDataCategory): void {
    this.editForm.patchValue({
      id: dataCategory.id,
      categoryType: dataCategory.categoryType,
      categoryCode: dataCategory.categoryCode,
      value: dataCategory.value,
      categoryDesc: dataCategory.categoryDesc,
    });
  }

  protected createFromForm(): IDataCategory {
    return {
      ...new DataCategory(),
      id: this.editForm.get(['id'])!.value,
      categoryType: this.editForm.get(['categoryType'])!.value,
      categoryCode: this.editForm.get(['categoryCode'])!.value,
      value: this.editForm.get(['value'])!.value,
      categoryDesc: this.editForm.get(['categoryDesc'])!.value,
    };
  }
}
