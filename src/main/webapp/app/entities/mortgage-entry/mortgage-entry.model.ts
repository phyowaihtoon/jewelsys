import * as dayjs from 'dayjs';
import { MortgageItemGroup } from 'app/entities/enumerations/mortgage-item-group.model';
import { MortgageDamageType } from 'app/entities/enumerations/mortgage-damage-type.model';

export interface IMortgageEntry {
  id?: number;
  name?: string;
  address?: string;
  phone?: string | null;
  groupCode?: MortgageItemGroup;
  itemCode?: string;
  itemName?: string;
  damageType?: MortgageDamageType | null;
  wInKyat?: number | null;
  wInPae?: number | null;
  wInYway?: number | null;
  principalAmount?: number;
  startDate?: dayjs.Dayjs;
  interestRate?: number | null;
  term?: number | null;
  delFlg?: string | null;
}

export class MortgageEntry implements IMortgageEntry {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public phone?: string | null,
    public groupCode?: MortgageItemGroup,
    public itemCode?: string,
    public damageType?: MortgageDamageType | null,
    public wInKyat?: number | null,
    public wInPae?: number | null,
    public wInYway?: number | null,
    public principalAmount?: number,
    public startDate?: dayjs.Dayjs,
    public interestRate?: number | null,
    public term?: number | null,
    public delFlg?: string | null
  ) {}
}

export function getMortgageEntryIdentifier(mortgageEntry: IMortgageEntry): number | undefined {
  return mortgageEntry.id;
}
