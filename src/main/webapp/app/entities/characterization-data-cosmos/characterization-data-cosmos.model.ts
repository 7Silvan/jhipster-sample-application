import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';

export interface ICharacterizationDataCosmos {
  id: number;
  description?: string | null;
  link?: string | null;
  cellModel?: Pick<ICellModelCosmos, 'id'> | null;
}

export type NewCharacterizationDataCosmos = Omit<ICharacterizationDataCosmos, 'id'> & { id: null };
