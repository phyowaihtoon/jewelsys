import * as dayjs from 'dayjs';
import { IGemsItem } from 'app/entities/gems-item/gems-item.model';

export interface IGemsPriceRate {
  id?: number;
  effectiveDate?: dayjs.Dayjs;
  rateSrNo?: number;
  rateType?: string;
  priceRate?: number | null;
  delFlg?: string | null;
  gemsItem?: IGemsItem | null;
}

export class GemsPriceRate implements IGemsPriceRate {
  constructor(
    public id?: number,
    public effectiveDate?: dayjs.Dayjs,
    public rateSrNo?: number,
    public rateType?: string,
    public priceRate?: number | null,
    public delFlg?: string | null,
    public gemsItem?: IGemsItem | null
  ) {}
}

export function getGemsPriceRateIdentifier(gemsPriceRate: IGemsPriceRate): number | undefined {
  return gemsPriceRate.id;
}
