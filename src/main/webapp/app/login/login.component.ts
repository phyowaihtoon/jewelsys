import { Component, ViewChild, OnInit, AfterViewInit, ElementRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { IMenuAccess } from 'app/shared/model/menu-access';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false })
  username?: ElementRef;
  authenticationError = false;
  menuAccess: IMenuAccess[] | null = null;
  public menuAccessAPI = SERVER_API_URL + 'api/role-menu-maps/menus';

  loginForm = this.fb.group({
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
    rememberMe: [false],
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    public http: HttpClient,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    if (this.username) {
      this.username.nativeElement.focus();
    }
  }

  login(): void {
    this.loginService
      .login({
        username: this.loginForm.get('username')!.value,
        password: this.loginForm.get('password')!.value,
        rememberMe: this.loginForm.get('rememberMe')!.value,
      })
      .subscribe(
        () => {
          this.loadMenus();
        },
        () => (this.authenticationError = true)
      );
  }

  loadMenus(): void {
    this.subscribeToMenuAccessResponse(this.getMenus());
  }

  getMenus(): Observable<HttpResponse<IMenuAccess[]>> {
    return this.http.get<IMenuAccess[]>(`${this.menuAccessAPI}`, { observe: 'response' });
  }

  protected subscribeToMenuAccessResponse(result: Observable<HttpResponse<IMenuAccess[]>>): void {
    result.subscribe(
      res => {
        this.menuAccess = res.body;
        this.loginService.storeMenuAccess(this.menuAccess);

        this.authenticationError = false;
        if (!this.router.getCurrentNavigation()) {
          // There were no routing during login (eg from navigationToStoredUrl)
          this.router.navigate(['']);
        }
      },
      () => (this.authenticationError = true)
    );
  }
}
