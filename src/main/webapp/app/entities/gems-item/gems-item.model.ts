import { IGemsType } from 'app/entities/gems-type/gems-type.model';

export interface IGemsItem {
  id?: number;
  code?: string;
  name?: string;
  delFlg?: string | null;
  gemsType?: IGemsType | null;
}

export class GemsItem implements IGemsItem {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public delFlg?: string | null,
    public gemsType?: IGemsType | null
  ) {}
}

export function getGemsItemIdentifier(gemsItem: IGemsItem): number | undefined {
  return gemsItem.id;
}
