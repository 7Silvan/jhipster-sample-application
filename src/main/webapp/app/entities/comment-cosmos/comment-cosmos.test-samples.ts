import dayjs from 'dayjs/esm';

import { ICommentCosmos, NewCommentCosmos } from './comment-cosmos.model';

export const sampleWithRequiredData: ICommentCosmos = {
  id: 78899,
  text: 'Coordinator',
  timedate: dayjs('2023-01-11T16:03'),
};

export const sampleWithPartialData: ICommentCosmos = {
  id: 34202,
  text: 'Baby New',
  timedate: dayjs('2023-01-12T10:15'),
};

export const sampleWithFullData: ICommentCosmos = {
  id: 37490,
  text: 'circuit Health',
  timedate: dayjs('2023-01-11T13:15'),
};

export const sampleWithNewData: NewCommentCosmos = {
  text: 'Investment Macedonia Creative',
  timedate: dayjs('2023-01-11T13:52'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);