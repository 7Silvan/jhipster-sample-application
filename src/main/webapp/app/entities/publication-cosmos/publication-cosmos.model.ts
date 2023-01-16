import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';

export interface IPublicationCosmos {
  id: number;
  link?: string | null;
  cellModel?: Pick<ICellModelCosmos, 'id'> | null;
}

export type NewPublicationCosmos = Omit<IPublicationCosmos, 'id'> & { id: null };
