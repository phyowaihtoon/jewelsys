export interface IMenuGroup {
  id?: number;
  name?: string;
  sequenceNo?: number | null;
}

export class MenuGroup implements IMenuGroup {
  constructor(public id?: number, public name?: string, public sequenceNo?: number | null) {}
}

export function getMenuGroupIdentifier(menuGroup: IMenuGroup): number | undefined {
  return menuGroup.id;
}
