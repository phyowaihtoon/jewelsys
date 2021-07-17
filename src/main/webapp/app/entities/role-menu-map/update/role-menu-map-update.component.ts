import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRoleMenuMap, RoleMenuMap } from '../role-menu-map.model';
import { RoleMenuMapService } from '../service/role-menu-map.service';
import { IMenu } from 'app/entities/menu/menu.model';
import { MenuService } from 'app/entities/menu/service/menu.service';

@Component({
  selector: 'jhi-role-menu-map-update',
  templateUrl: './role-menu-map-update.component.html',
})
export class RoleMenuMapUpdateComponent implements OnInit {
  isSaving = false;

  menusSharedCollection: IMenu[] = [];

  editForm = this.fb.group({
    id: [],
    roleId: [],
    menu: [],
  });

  constructor(
    protected roleMenuMapService: RoleMenuMapService,
    protected menuService: MenuService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleMenuMap }) => {
      this.updateForm(roleMenuMap);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roleMenuMap = this.createFromForm();
    if (roleMenuMap.id !== undefined) {
      this.subscribeToSaveResponse(this.roleMenuMapService.update(roleMenuMap));
    } else {
      this.subscribeToSaveResponse(this.roleMenuMapService.create(roleMenuMap));
    }
  }

  trackMenuById(index: number, item: IMenu): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleMenuMap>>): void {
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

  protected updateForm(roleMenuMap: IRoleMenuMap): void {
    this.editForm.patchValue({
      id: roleMenuMap.id,
      roleId: roleMenuMap.roleId,
      menu: roleMenuMap.menu,
    });

    this.menusSharedCollection = this.menuService.addMenuToCollectionIfMissing(this.menusSharedCollection, roleMenuMap.menu);
  }

  protected loadRelationshipsOptions(): void {
    this.menuService
      .query()
      .pipe(map((res: HttpResponse<IMenu[]>) => res.body ?? []))
      .pipe(map((menus: IMenu[]) => this.menuService.addMenuToCollectionIfMissing(menus, this.editForm.get('menu')!.value)))
      .subscribe((menus: IMenu[]) => (this.menusSharedCollection = menus));
  }

  protected createFromForm(): IRoleMenuMap {
    return {
      ...new RoleMenuMap(),
      id: this.editForm.get(['id'])!.value,
      roleId: this.editForm.get(['roleId'])!.value,
      menu: this.editForm.get(['menu'])!.value,
    };
  }
}
