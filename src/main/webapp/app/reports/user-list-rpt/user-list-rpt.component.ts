import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { IRptParamsDTO, RptParamsDTO } from '../report.model';
import { ReportService } from '../service/report.service';

@Component({
  selector: 'jhi-user-list-rpt',
  templateUrl: './user-list-rpt.component.html',
  styleUrls: ['./user-list-rpt.component.scss'],
})
export class UserListRptComponent {
  isGenerating = false;
  rptParams: IRptParamsDTO | null = null;
  testData: IRptParamsDTO | null = null;
  rptTitleName: string = 'User List Report';
  rptFileName: string = 'UserListRpt';
  rptFormat: string = '.pdf';

  editForm = this.fb.group({
    rptTitleName: [],
    rptFileName: [],
    rptFormat: [],
  });

  constructor(private reportService: ReportService, protected fb: FormBuilder, protected router: Router) {}

  generate(): void {
    this.isGenerating = true;
    const reportData = this.createFromForm();
    this.reportService.generateUserListRpt(reportData).subscribe(
      res => {
        this.rptParams = res.body;
        if (this.rptParams != null) {
          this.router.navigate(['report/report-viewer'], {
            queryParams: {
              rptTitleName: this.rptParams.rptTitleName,
              rptFileName: this.rptParams.rptFileName,
              rptFormat: this.rptParams.rptFormat,
            },
          });
        }
      },
      err => {
        console.error('No Response - User List Report');
      }
    );
  }

  protected createFromForm(): IRptParamsDTO {
    return {
      ...new RptParamsDTO(),
      rptTitleName: this.rptTitleName,
      rptFileName: this.rptFileName,
      rptFormat: this.rptFormat,
      rptPath: '',
      rptJrxml: '',
      rptPS1: '',
      rptPS2: '',
      rptPS3: '',
      rptPS4: '',
      rptPS5: '',
    };
  }
}
