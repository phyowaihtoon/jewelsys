import { IGoldType } from 'app/entities/gold-type/gold-type.model';

export interface IGoldItemGroup {
  id?: number;
  code?: string;
  name?: string;
  delFlg?: string | null;
  goldType?: IGoldType | null;
}

export class GoldItemGroup implements IGoldItemGroup {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public delFlg?: string | null,
    public goldType?: IGoldType | null
  ) {}
}

export function getGoldItemGroupIdentifier(goldItemGroup: IGoldItemGroup): number | undefined {
  return goldItemGroup.id;
}
