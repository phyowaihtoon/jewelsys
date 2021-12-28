import * as dayjs from 'dayjs';

export interface IMortgageEntry {
  id?: number;
  name?: string;
  address?: string;
  phone?: string | null;
  itemName?: string;
  wInKyat?: number | null;
  wInPae?: number | null;
  wInYway?: number | null;
  principalAmount?: number;
  interestRate?: number;
  term?: number | null;
  startDate?: dayjs.Dayjs;
  delFlg?: string | null;
}

export class MortgageEntry implements IMortgageEntry {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public phone?: string | null,
    public itemName?: string,
    public wInKyat?: number | null,
    public wInPae?: number | null,
    public wInYway?: number | null,
    public principalAmount?: number,
    public interestRate?: number,
    public term?: number | null,
    public startDate?: dayjs.Dayjs,
    public delFlg?: string | null
  ) {}
}

export function getMortgageEntryIdentifier(mortgageEntry: IMortgageEntry): number | undefined {
  return mortgageEntry.id;
}
