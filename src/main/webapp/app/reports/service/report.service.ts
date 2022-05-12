import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Observable } from 'rxjs';
import { IRptParamsDTO } from '../report.model';

export type EntityResponseType = HttpResponse<IRptParamsDTO>;

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  public generateRptUrl = this.applicationConfigService.getEndpointFor('api/user-list-rpt');
  public generateMortgageRptUrl = this.applicationConfigService.getEndpointFor('api/mortgage-list-rpt');
  public showPDFUrl = this.applicationConfigService.getEndpointFor('api/viewPdf');
  public downloadUrl = this.applicationConfigService.getEndpointFor('api/download');
  public testAPI = this.applicationConfigService.getEndpointFor('api/test');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  generateUserListRpt(reportDTO: IRptParamsDTO): Observable<EntityResponseType> {
    return this.http.post<IRptParamsDTO>(this.generateRptUrl, reportDTO, { observe: 'response' });
  }

  generateMortgageListRpt(reportDTO: IRptParamsDTO): Observable<EntityResponseType> {
    return this.http.post<IRptParamsDTO>(this.generateMortgageRptUrl, reportDTO, { observe: 'response' });
  }

  showPDF(fileName: string): Observable<Blob> {
    return this.http.get(`${this.showPDFUrl}/${fileName}`, { responseType: 'blob' });
  }

  downloadFile(fileName: string): Observable<Blob> {
    return this.http.get(`${this.downloadUrl}/${fileName}`, { responseType: 'blob' });
  }

  testApi(): Observable<EntityResponseType> {
    return this.http.get<IRptParamsDTO>(`${this.testAPI}`, { observe: 'response' });
  }
}
