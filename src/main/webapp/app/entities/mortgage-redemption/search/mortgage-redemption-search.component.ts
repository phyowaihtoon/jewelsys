import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { IMortgageRedemption } from '../mortgage-redemption.model';
import { MortgageRedemptionService } from '../service/mortgage-redemption.service';

@Component({
  selector: 'jhi-mortgage-redemption-search',
  templateUrl: './mortgage-redemption-search.component.html',
  styleUrls: ['./mortgage-redemption-search.component.scss'],
})
export class MortgageRedemptionSearchComponent {
  isSearching = false;
  mortgageRedemption: IMortgageRedemption | null = {};

  searchForm = this.fb.group({
    searchType: [null, Validators.required],
    mortgageID: [null, Validators.required],
  });

  constructor(private fb: FormBuilder, private mrService: MortgageRedemptionService, private router: Router) {}

  save(): void {
    const searchType = this.searchForm.get('searchType')!.value;
    if (searchType) {
      const mortgageID = this.searchForm.get('mortgageID')!.value;
      this.mrService
        .search(mortgageID)
        .pipe(map((res: HttpResponse<IMortgageRedemption>) => res.body))
        .subscribe((res: IMortgageRedemption | null) => {
          this.mortgageRedemption = res;
          if (this.mortgageRedemption) {
            if (searchType === 1) {
              this.router.navigate(['/mortgage-redem/detail'], { state: { data: this.mortgageRedemption } });
            }
            if (searchType === 2) {
              this.router.navigate(['/mortgage-redem/add'], { state: { data: this.mortgageRedemption } });
            }
          }
        });
    }
  }

  previousState(): void {
    window.history.back();
  }
}
