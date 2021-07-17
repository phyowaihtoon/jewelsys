import { IMenuGroup } from 'app/entities/menu-group/menu-group.model';
import { MenuStatus } from 'app/entities/enumerations/menu-status.model';

export interface IMenu {
  id?: number;
  menuCode?: string;
  menuName?: string;
  routerLink?: string | null;
  status?: MenuStatus;
  menuGroup?: IMenuGroup | null;
}

export class Menu implements IMenu {
  constructor(
    public id?: number,
    public menuCode?: string,
    public menuName?: string,
    public routerLink?: string | null,
    public status?: MenuStatus,
    public menuGroup?: IMenuGroup | null
  ) {}
}

export function getMenuIdentifier(menu: IMenu): number | undefined {
  return menu.id;
}
