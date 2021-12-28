import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMortgageEntry, MortgageEntry } from '../mortgage-entry.model';
import { MortgageEntryService } from '../service/mortgage-entry.service';

@Component({
  selector: 'jhi-mortgage-entry-update',
  templateUrl: './mortgage-entry-update.component.html',
})
export class MortgageEntryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    address: [null, [Validators.required]],
    phone: [],
    itemName: [null, [Validators.required]],
    wInKyat: [],
    wInPae: [],
    wInYway: [],
    principalAmount: [null, [Validators.required]],
    interestRate: [null, [Validators.required]],
    term: [],
    startDate: [null, [Validators.required]],
    delFlg: [],
  });

  constructor(protected mortgageEntryService: MortgageEntryService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mortgageEntry }) => {
      if (mortgageEntry.id === undefined) {
        const today = dayjs().startOf('day');
        mortgageEntry.startDate = today;
      }

      this.updateForm(mortgageEntry);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mortgageEntry = this.createFromForm();
    if (mortgageEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.mortgageEntryService.update(mortgageEntry));
    } else {
      this.subscribeToSaveResponse(this.mortgageEntryService.create(mortgageEntry));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMortgageEntry>>): void {
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

  protected updateForm(mortgageEntry: IMortgageEntry): void {
    this.editForm.patchValue({
      id: mortgageEntry.id,
      name: mortgageEntry.name,
      address: mortgageEntry.address,
      phone: mortgageEntry.phone,
      itemName: mortgageEntry.itemName,
      wInKyat: mortgageEntry.wInKyat,
      wInPae: mortgageEntry.wInPae,
      wInYway: mortgageEntry.wInYway,
      principalAmount: mortgageEntry.principalAmount,
      interestRate: mortgageEntry.interestRate,
      term: mortgageEntry.term,
      startDate: mortgageEntry.startDate ? mortgageEntry.startDate.format(DATE_TIME_FORMAT) : null,
      delFlg: mortgageEntry.delFlg,
    });
  }

  protected createFromForm(): IMortgageEntry {
    return {
      ...new MortgageEntry(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      itemName: this.editForm.get(['itemName'])!.value,
      wInKyat: this.editForm.get(['wInKyat'])!.value,
      wInPae: this.editForm.get(['wInPae'])!.value,
      wInYway: this.editForm.get(['wInYway'])!.value,
      principalAmount: this.editForm.get(['principalAmount'])!.value,
      interestRate: this.editForm.get(['interestRate'])!.value,
      term: this.editForm.get(['term'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? dayjs(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      delFlg: 'N',
    };
  }
}
