import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataCategoryComponent } from '../list/data-category.component';
import { DataCategoryDetailComponent } from '../detail/data-category-detail.component';
import { DataCategoryUpdateComponent } from '../update/data-category-update.component';
import { DataCategoryRoutingResolveService } from './data-category-routing-resolve.service';

const dataCategoryRoute: Routes = [
  {
    path: '',
    component: DataCategoryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DataCategoryDetailComponent,
    resolve: {
      dataCategory: DataCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DataCategoryUpdateComponent,
    resolve: {
      dataCategory: DataCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DataCategoryUpdateComponent,
    resolve: {
      dataCategory: DataCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dataCategoryRoute)],
  exports: [RouterModule],
})
export class DataCategoryRoutingModule {}
