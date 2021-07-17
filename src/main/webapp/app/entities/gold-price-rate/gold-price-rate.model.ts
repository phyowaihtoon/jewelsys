import * as dayjs from 'dayjs';
import { IGoldType } from 'app/entities/gold-type/gold-type.model';

export interface IGoldPriceRate {
  id?: number;
  effectiveDate?: dayjs.Dayjs;
  rateSrNo?: number;
  rateType?: string;
  priceRate?: number | null;
  delFlg?: string | null;
  goldType?: IGoldType | null;
}

export class GoldPriceRate implements IGoldPriceRate {
  constructor(
    public id?: number,
    public effectiveDate?: dayjs.Dayjs,
    public rateSrNo?: number,
    public rateType?: string,
    public priceRate?: number | null,
    public delFlg?: string | null,
    public goldType?: IGoldType | null
  ) {}
}

export function getGoldPriceRateIdentifier(goldPriceRate: IGoldPriceRate): number | undefined {
  return goldPriceRate.id;
}
