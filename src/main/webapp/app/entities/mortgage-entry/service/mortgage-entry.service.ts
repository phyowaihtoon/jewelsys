import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMortgageEntry, getMortgageEntryIdentifier } from '../mortgage-entry.model';

export type EntityResponseType = HttpResponse<IMortgageEntry>;
export type EntityArrayResponseType = HttpResponse<IMortgageEntry[]>;

@Injectable({ providedIn: 'root' })
export class MortgageEntryService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/mortgage-entries');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(mortgageEntry: IMortgageEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mortgageEntry);
    return this.http
      .post<IMortgageEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mortgageEntry: IMortgageEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mortgageEntry);
    return this.http
      .put<IMortgageEntry>(`${this.resourceUrl}/${getMortgageEntryIdentifier(mortgageEntry) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(mortgageEntry: IMortgageEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mortgageEntry);
    return this.http
      .patch<IMortgageEntry>(`${this.resourceUrl}/${getMortgageEntryIdentifier(mortgageEntry) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMortgageEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMortgageEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMortgageEntryToCollectionIfMissing(
    mortgageEntryCollection: IMortgageEntry[],
    ...mortgageEntriesToCheck: (IMortgageEntry | null | undefined)[]
  ): IMortgageEntry[] {
    const mortgageEntries: IMortgageEntry[] = mortgageEntriesToCheck.filter(isPresent);
    if (mortgageEntries.length > 0) {
      const mortgageEntryCollectionIdentifiers = mortgageEntryCollection.map(
        mortgageEntryItem => getMortgageEntryIdentifier(mortgageEntryItem)!
      );
      const mortgageEntriesToAdd = mortgageEntries.filter(mortgageEntryItem => {
        const mortgageEntryIdentifier = getMortgageEntryIdentifier(mortgageEntryItem);
        if (mortgageEntryIdentifier == null || mortgageEntryCollectionIdentifiers.includes(mortgageEntryIdentifier)) {
          return false;
        }
        mortgageEntryCollectionIdentifiers.push(mortgageEntryIdentifier);
        return true;
      });
      return [...mortgageEntriesToAdd, ...mortgageEntryCollection];
    }
    return mortgageEntryCollection;
  }

  protected convertDateFromClient(mortgageEntry: IMortgageEntry): IMortgageEntry {
    return Object.assign({}, mortgageEntry, {
      startDate: mortgageEntry.startDate?.isValid() ? mortgageEntry.startDate.format('YYYY-MM-DD') + 'T00:00:00.000Z' : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mortgageEntry: IMortgageEntry) => {
        mortgageEntry.startDate = mortgageEntry.startDate ? dayjs(mortgageEntry.startDate) : undefined;
      });
    }
    return res;
  }
}
