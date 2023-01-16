import { ICharacterizationDataCosmos, NewCharacterizationDataCosmos } from './characterization-data-cosmos.model';

export const sampleWithRequiredData: ICharacterizationDataCosmos = {
  id: 46297,
  description: 'Berkshire',
  link: 'sky Egypt',
};

export const sampleWithPartialData: ICharacterizationDataCosmos = {
  id: 19333,
  description: 'Streets Rial',
  link: 'ADP plum Generic',
};

export const sampleWithFullData: ICharacterizationDataCosmos = {
  id: 36245,
  description: 'Singapore Granite',
  link: 'Ethiopian',
};

export const sampleWithNewData: NewCharacterizationDataCosmos = {
  description: 'Bond',
  link: 'Administrator Course drive',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
