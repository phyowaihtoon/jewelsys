import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReportViewerComponent } from '../container/report-viewer.component';
import { MortgageListRptComponent } from '../mortgage-list-rpt/mortgage-list-rpt.component';
import { UserListRptComponent } from '../user-list-rpt/user-list-rpt.component';

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild([
      {
        path: 'user-list-rpt',
        component: UserListRptComponent,
      },
      {
        path: 'report-viewer',
        component: ReportViewerComponent,
      },
      {
        path: 'mortgage-list-rpt',
        component: MortgageListRptComponent,
      },
    ]),
  ],
})
export class ReportFormRoutingModule {}
