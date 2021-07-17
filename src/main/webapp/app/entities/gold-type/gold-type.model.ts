export interface IGoldType {
  id?: number;
  code?: string;
  name?: string;
  delFlg?: string | null;
}

export class GoldType implements IGoldType {
  constructor(public id?: number, public code?: string, public name?: string, public delFlg?: string | null) {}
}

export function getGoldTypeIdentifier(goldType: IGoldType): number | undefined {
  return goldType.id;
}
