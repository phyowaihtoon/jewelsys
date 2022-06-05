import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IMortgageRedemption } from '../mortgage-redemption.model';
import * as dayjs from 'dayjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export type EntityResponseType = HttpResponse<IMortgageRedemption>;
export type EntityArrayResponseType = HttpResponse<IMortgageRedemption[]>;

@Injectable({
  providedIn: 'root',
})
export class MortgageRedemptionService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/mr');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  search(mortgageID: string): Observable<EntityResponseType> {
    return this.http.get<IMortgageRedemption>(`${this.resourceUrl}/${mortgageID}`, { observe: 'response' });
  }

  create(mortgageRedemption: IMortgageRedemption): Observable<EntityResponseType> {
    const modelData = this.convertDateFromClient(mortgageRedemption);
    return this.http
      .post<IMortgageRedemption>(this.resourceUrl, modelData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  protected convertDateFromClient(mortgageEntry: IMortgageRedemption): IMortgageRedemption {
    return Object.assign({}, mortgageEntry, {
      startDate: mortgageEntry.mrDate?.isValid() ? mortgageEntry.mrDate.format('YYYY-MM-DD') + 'T00:00:00.000Z' : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.mrDate = res.body.mrDate ? dayjs(res.body.mrDate) : undefined;
    }
    return res;
  }
}
