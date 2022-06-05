import * as dayjs from 'dayjs';
import { IMortgageEntry, MortgageEntry } from '../mortgage-entry/mortgage-entry.model';

export interface IMortgageRedemption {
  mortgageID?: number;
  interestAmount?: number;
  mrDate?: dayjs.Dayjs;
  mrTime?: string;
  mrMMYear?: string;
  mrMMMonth?: string;
  mrMMDayGR?: string;
  mrMMDay?: string;
  entryDTO?: IMortgageEntry;
}

export class MortgageRedemption implements IMortgageRedemption {
  constructor(
    public mortgageID?: number,
    public interestAmount?: number,
    public mrDate?: dayjs.Dayjs,
    public mrTime?: string,
    public mrMMYear?: string,
    public mrMMMonth?: string,
    public mrMMDayGR?: string,
    public mrMMDay?: string,
    public entryDTO?: IMortgageEntry
  ) {}
}
