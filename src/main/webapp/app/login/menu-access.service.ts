import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IMenuAccess } from 'app/shared/model/menu-access';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Observable } from 'rxjs';

export type EntityArrayResponseType = HttpResponse<IMenuAccess[]>;

@Injectable({
  providedIn: 'root',
})
export class MenuAccessService {
  //  public menuAccessAPI = SERVER_API_URL + 'api/role-menu-maps/menus';
  public menuAccessAPI = this.applicationConfigService.getEndpointFor('api/role-menu-maps/menus');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  loadMenuAccess(): Observable<EntityArrayResponseType> {
    return this.http.get<IMenuAccess[]>(this.menuAccessAPI, { observe: 'response' });
  }
}
