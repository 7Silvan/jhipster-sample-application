import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';

export interface ICellTypeCosmos {
  id: number;
  cellSourceInfo?: string | null;
  cellArchitecture?: string | null;
  vendor?: string | null;
  cellModel?: Pick<ICellModelCosmos, 'id'> | null;
}

export type NewCellTypeCosmos = Omit<ICellTypeCosmos, 'id'> & { id: null };
