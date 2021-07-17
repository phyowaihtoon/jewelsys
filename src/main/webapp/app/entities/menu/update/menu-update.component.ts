import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMenu, Menu } from '../menu.model';
import { MenuService } from '../service/menu.service';
import { IMenuGroup } from 'app/entities/menu-group/menu-group.model';
import { MenuGroupService } from 'app/entities/menu-group/service/menu-group.service';

@Component({
  selector: 'jhi-menu-update',
  templateUrl: './menu-update.component.html',
})
export class MenuUpdateComponent implements OnInit {
  isSaving = false;

  menuGroupsSharedCollection: IMenuGroup[] = [];

  editForm = this.fb.group({
    id: [],
    menuCode: [null, [Validators.required]],
    menuName: [null, [Validators.required]],
    routerLink: [],
    status: [null, [Validators.required]],
    menuGroup: [],
  });

  constructor(
    protected menuService: MenuService,
    protected menuGroupService: MenuGroupService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ menu }) => {
      this.updateForm(menu);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const menu = this.createFromForm();
    if (menu.id !== undefined) {
      this.subscribeToSaveResponse(this.menuService.update(menu));
    } else {
      this.subscribeToSaveResponse(this.menuService.create(menu));
    }
  }

  trackMenuGroupById(index: number, item: IMenuGroup): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>): void {
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

  protected updateForm(menu: IMenu): void {
    this.editForm.patchValue({
      id: menu.id,
      menuCode: menu.menuCode,
      menuName: menu.menuName,
      routerLink: menu.routerLink,
      status: menu.status,
      menuGroup: menu.menuGroup,
    });

    this.menuGroupsSharedCollection = this.menuGroupService.addMenuGroupToCollectionIfMissing(
      this.menuGroupsSharedCollection,
      menu.menuGroup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.menuGroupService
      .query()
      .pipe(map((res: HttpResponse<IMenuGroup[]>) => res.body ?? []))
      .pipe(
        map((menuGroups: IMenuGroup[]) =>
          this.menuGroupService.addMenuGroupToCollectionIfMissing(menuGroups, this.editForm.get('menuGroup')!.value)
        )
      )
      .subscribe((menuGroups: IMenuGroup[]) => (this.menuGroupsSharedCollection = menuGroups));
  }

  protected createFromForm(): IMenu {
    return {
      ...new Menu(),
      id: this.editForm.get(['id'])!.value,
      menuCode: this.editForm.get(['menuCode'])!.value,
      menuName: this.editForm.get(['menuName'])!.value,
      routerLink: this.editForm.get(['routerLink'])!.value,
      status: this.editForm.get(['status'])!.value,
      menuGroup: this.editForm.get(['menuGroup'])!.value,
    };
  }
}
