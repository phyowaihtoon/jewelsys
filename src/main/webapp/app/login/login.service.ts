import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { Login } from './login.model';
import { IMenuAccess } from 'app/shared/model/menu-access';
import { SessionStorageService } from 'ngx-webstorage';

@Injectable({ providedIn: 'root' })
export class LoginService {
  menuAccess: IMenuAccess[] | null = null;
  storedMenuAccess: any | null = null;

  constructor(
    private accountService: AccountService,
    private $sessionStorage: SessionStorageService,
    private authServerProvider: AuthServerProvider
  ) {}

  login(credentials: Login): Observable<Account | null> {
    return this.authServerProvider.login(credentials).pipe(mergeMap(() => this.accountService.identity(true)));
  }

  logout(): void {
    this.authServerProvider.logout().subscribe({ complete: () => this.accountService.authenticate(null) });
  }

  storeMenuAccess(menuAccessArr: any): void {
    this.menuAccess = menuAccessArr;
    this.$sessionStorage.store('menuAccess', JSON.stringify(this.menuAccess));
  }

  refreshMenuAccess(): void {
    this.storedMenuAccess = this.$sessionStorage.retrieve('menuAccess') || [];
    if (this.storedMenuAccess !== null && this.storedMenuAccess !== undefined) {
      this.menuAccess = JSON.parse(this.storedMenuAccess);
    }
  }
}
