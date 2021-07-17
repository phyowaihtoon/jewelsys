export interface IMenuAccess {
  id?: number;
  roleId?: string;
  menuId?: number;
  menuName?: string;
  routerLink?: string;
  menuGroupId?: number;
  menuGroupName?: string;
  menuList?: any;
}

export class MenuAccess implements IMenuAccess {
  constructor(
    public id?: number,
    public roleId?: string,
    public menuId?: number,
    public menuName?: string,
    routerLink?: string,
    menuGroupId?: number,
    menuGroupName?: string,
    menuList?: any
  ) {}
}
