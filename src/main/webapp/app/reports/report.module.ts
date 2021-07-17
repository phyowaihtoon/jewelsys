import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserListRptComponent } from './user-list-rpt/user-list-rpt.component';
import { ReportViewerComponent } from './container/report-viewer.component';
import { ReportFormRoutingModule } from './route/report-form-routing.module';

@NgModule({
  imports: [SharedModule, ReportFormRoutingModule],
  declarations: [UserListRptComponent, ReportViewerComponent],
})
export class ReportModule {}
