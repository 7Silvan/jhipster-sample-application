import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';

export interface IProjectSupportedCosmos {
  id: number;
  decisionMaking?: string | null;
  isRegulatory?: boolean | null;
  projectNameOrThemeNumber?: string | null;
  cellModel?: Pick<ICellModelCosmos, 'id'> | null;
}

export type NewProjectSupportedCosmos = Omit<IProjectSupportedCosmos, 'id'> & { id: null };
