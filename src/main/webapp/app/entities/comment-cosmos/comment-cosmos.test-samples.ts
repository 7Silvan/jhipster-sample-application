import dayjs from 'dayjs/esm';

import { ICommentCosmos, NewCommentCosmos } from './comment-cosmos.model';

export const sampleWithRequiredData: ICommentCosmos = {
  id: 78899,
  text: 'Coordinator',
  timeCreated: dayjs('2023-01-15T12:07'),
};

export const sampleWithPartialData: ICommentCosmos = {
  id: 34202,
  text: 'Baby New',
  timeCreated: dayjs('2023-01-16T06:18'),
};

export const sampleWithFullData: ICommentCosmos = {
  id: 37490,
  text: 'circuit Health',
  timeCreated: dayjs('2023-01-15T09:19'),
};

export const sampleWithNewData: NewCommentCosmos = {
  text: 'Investment Macedonia Creative',
  timeCreated: dayjs('2023-01-15T09:56'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
