export interface IGemsType {
  id?: number;
  code?: string;
  name?: string;
  delFlg?: string | null;
}

export class GemsType implements IGemsType {
  constructor(public id?: number, public code?: string, public name?: string, public delFlg?: string | null) {}
}

export function getGemsTypeIdentifier(gemsType: IGemsType): number | undefined {
  return gemsType.id;
}
