import { IShopInfo } from 'app/entities/shop-info/shop-info.model';

export interface ICounterInfo {
  id?: number;
  counterNo?: string;
  counterName?: string;
  delFlg?: string | null;
  shopInfo?: IShopInfo | null;
}

export class CounterInfo implements ICounterInfo {
  constructor(
    public id?: number,
    public counterNo?: string,
    public counterName?: string,
    public delFlg?: string | null,
    public shopInfo?: IShopInfo | null
  ) {}
}

export function getCounterInfoIdentifier(counterInfo: ICounterInfo): number | undefined {
  return counterInfo.id;
}
