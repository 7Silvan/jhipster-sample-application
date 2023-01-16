import { IPublicationCosmos, NewPublicationCosmos } from './publication-cosmos.model';

export const sampleWithRequiredData: IPublicationCosmos = {
  id: 38974,
  link: 'Concrete optimize',
};

export const sampleWithPartialData: IPublicationCosmos = {
  id: 94896,
  link: '(E.M.U.-6) Account Home',
};

export const sampleWithFullData: IPublicationCosmos = {
  id: 71586,
  link: 'Table',
};

export const sampleWithNewData: NewPublicationCosmos = {
  link: 'Montana Texas parse',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
