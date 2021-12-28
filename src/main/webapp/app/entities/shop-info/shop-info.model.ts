export interface IShopInfo {
  id?: number;
  shopCode?: string;
  nameEng?: string;
  nameMyan?: string | null;
  addrEng?: string | null;
  addrMyan?: string | null;
  phone?: string | null;
  delFlg?: string | null;
}

export class ShopInfo implements IShopInfo {
  constructor(
    public id?: number,
    public shopCode?: string,
    public nameEng?: string,
    public nameMyan?: string | null,
    public addrEng?: string | null,
    public addrMyan?: string | null,
    public phone?: string | null,
    public delFlg?: string | null
  ) {}
}

export function getShopInfoIdentifier(shopInfo: IShopInfo): number | undefined {
  return shopInfo.id;
}
