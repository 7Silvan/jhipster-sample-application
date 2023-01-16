import dayjs from 'dayjs/esm';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { IUser } from 'app/entities/user/user.model';

export interface ICommentCosmos {
  id: number;
  text?: string | null;
  timeCreated?: dayjs.Dayjs | null;
  cellModel?: Pick<ICellModelCosmos, 'id'> | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewCommentCosmos = Omit<ICommentCosmos, 'id'> & { id: null };
