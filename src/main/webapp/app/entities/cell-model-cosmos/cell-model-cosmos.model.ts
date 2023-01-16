import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';

export interface ICellModelCosmos {
  id: string;
  dateAdded?: dayjs.Dayjs | null;
  dateUpdated?: dayjs.Dayjs | null;
  species?: string | null;
  targetOrgan?: string | null;
  targetTissue?: string | null;
  vesselInfo?: string | null;
  protocol?: string | null;
  manufacturer?: string | null;
  matrixType?: string | null;
  vesselType?: string | null;
  inhouse?: boolean | null;
  ratingCharacterization?: number | null;
  ratingComplexity?: number | null;
  ratingModelCost?: number | null;
  ratingThroughPutCapacity?: number | null;
  ratingTurnAroundTime?: number | null;
  imageUrl?: string | null;
  department?: string | null;
  modelState?: string | null;
  applicationADME?: boolean | null;
  applicationEfficacy?: boolean | null;
  applicationNone?: boolean | null;
  applicationSafety?: boolean | null;
  animalAssayReplaced?: string | null;
  note?: string | null;
  experts?: string | null;
  link?: string | null;
  limitationPerceived?: string | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewCellModelCosmos = Omit<ICellModelCosmos, 'id'> & { id: null };
