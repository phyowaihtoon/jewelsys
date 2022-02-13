import { MortgageItemGroup } from 'app/entities/enumerations/mortgage-item-group.model';

export interface IMortgageItem {
  id?: number;
  groupCode?: MortgageItemGroup;
  code?: string;
  itemName?: string;
  delFlg?: string | null;
}

export class MortgageItem implements IMortgageItem {
  constructor(
    public id?: number,
    public groupCode?: MortgageItemGroup,
    public code?: string,
    public itemName?: string,
    public delFlg?: string | null
  ) {}
}

export function getMortgageItemIdentifier(mortgageItem: IMortgageItem): number | undefined {
  return mortgageItem.id;
}
