export interface IDataCategory {
  id?: number;
  categoryType?: string | null;
  categoryCode?: string | null;
  value?: string | null;
  categoryDesc?: string | null;
}

export class DataCategory implements IDataCategory {
  constructor(
    public id?: number,
    public categoryType?: string | null,
    public categoryCode?: string | null,
    public value?: string | null,
    public categoryDesc?: string | null
  ) {}
}

export function getDataCategoryIdentifier(dataCategory: IDataCategory): number | undefined {
  return dataCategory.id;
}
