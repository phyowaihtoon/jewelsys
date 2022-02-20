import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDataCategory, getDataCategoryIdentifier } from '../data-category.model';

export type EntityResponseType = HttpResponse<IDataCategory>;
export type EntityArrayResponseType = HttpResponse<IDataCategory[]>;

@Injectable({ providedIn: 'root' })
export class DataCategoryService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/data-categories');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dataCategory: IDataCategory): Observable<EntityResponseType> {
    return this.http.post<IDataCategory>(this.resourceUrl, dataCategory, { observe: 'response' });
  }

  update(dataCategory: IDataCategory): Observable<EntityResponseType> {
    return this.http.put<IDataCategory>(`${this.resourceUrl}/${getDataCategoryIdentifier(dataCategory) as number}`, dataCategory, {
      observe: 'response',
    });
  }

  partialUpdate(dataCategory: IDataCategory): Observable<EntityResponseType> {
    return this.http.patch<IDataCategory>(`${this.resourceUrl}/${getDataCategoryIdentifier(dataCategory) as number}`, dataCategory, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  loadMMCalendar(): Observable<EntityArrayResponseType> {
    return this.http.get<IDataCategory[]>(`${this.resourceUrl}/loadMMC`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDataCategoryToCollectionIfMissing(
    dataCategoryCollection: IDataCategory[],
    ...dataCategoriesToCheck: (IDataCategory | null | undefined)[]
  ): IDataCategory[] {
    const dataCategories: IDataCategory[] = dataCategoriesToCheck.filter(isPresent);
    if (dataCategories.length > 0) {
      const dataCategoryCollectionIdentifiers = dataCategoryCollection.map(
        dataCategoryItem => getDataCategoryIdentifier(dataCategoryItem)!
      );
      const dataCategoriesToAdd = dataCategories.filter(dataCategoryItem => {
        const dataCategoryIdentifier = getDataCategoryIdentifier(dataCategoryItem);
        if (dataCategoryIdentifier == null || dataCategoryCollectionIdentifiers.includes(dataCategoryIdentifier)) {
          return false;
        }
        dataCategoryCollectionIdentifiers.push(dataCategoryIdentifier);
        return true;
      });
      return [...dataCategoriesToAdd, ...dataCategoryCollection];
    }
    return dataCategoryCollection;
  }
}
