import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataCategory } from '../data-category.model';

@Component({
  selector: 'jhi-data-category-detail',
  templateUrl: './data-category-detail.component.html',
})
export class DataCategoryDetailComponent implements OnInit {
  dataCategory: IDataCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataCategory }) => {
      this.dataCategory = dataCategory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
