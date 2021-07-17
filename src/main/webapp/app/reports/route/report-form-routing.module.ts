import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReportViewerComponent } from '../container/report-viewer.component';
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
    ]),
  ],
})
export class ReportFormRoutingModule {}
