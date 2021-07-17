import { IGoldItemGroup } from 'app/entities/gold-item-group/gold-item-group.model';

export interface IGoldItem {
  id?: number;
  code?: string;
  name?: string;
  delFlg?: string | null;
  goldItemGroup?: IGoldItemGroup | null;
}

export class GoldItem implements IGoldItem {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public delFlg?: string | null,
    public goldItemGroup?: IGoldItemGroup | null
  ) {}
}

export function getGoldItemIdentifier(goldItem: IGoldItem): number | undefined {
  return goldItem.id;
}
