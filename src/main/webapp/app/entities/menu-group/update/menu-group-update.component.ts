import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { IMenuGroup, MenuGroup } from '../menu-group.model';
import { MenuGroupService } from '../service/menu-group.service';

@Component({
  selector: 'jhi-menu-group-update',
  templateUrl: './menu-group-update.component.html',
})
export class MenuGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    sequenceNo: [],
  });

  constructor(protected menuGroupService: MenuGroupService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ menuGroup }) => {
      this.updateForm(menuGroup);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const menuGroup = this.createFromForm();
    if (menuGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.menuGroupService.update(menuGroup));
    } else {
      this.subscribeToSaveResponse(this.menuGroupService.create(menuGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenuGroup>>): void {
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

  protected updateForm(menuGroup: IMenuGroup): void {
    this.editForm.patchValue({
      id: menuGroup.id,
      name: menuGroup.name,
      sequenceNo: menuGroup.sequenceNo,
    });
  }

  protected createFromForm(): IMenuGroup {
    return {
      ...new MenuGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
    };
  }
}
