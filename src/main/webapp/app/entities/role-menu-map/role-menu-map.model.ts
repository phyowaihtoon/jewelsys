import { IMenu } from 'app/entities/menu/menu.model';

export interface IRoleMenuMap {
  id?: number;
  roleId?: string | null;
  menu?: IMenu | null;
}

export class RoleMenuMap implements IRoleMenuMap {
  constructor(public id?: number, public roleId?: string | null, public menu?: IMenu | null) {}
}

export function getRoleMenuMapIdentifier(roleMenuMap: IRoleMenuMap): number | undefined {
  return roleMenuMap.id;
}
