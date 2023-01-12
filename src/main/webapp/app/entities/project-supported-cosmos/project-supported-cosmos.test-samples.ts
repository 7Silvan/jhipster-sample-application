import { IProjectSupportedCosmos, NewProjectSupportedCosmos } from './project-supported-cosmos.model';

export const sampleWithRequiredData: IProjectSupportedCosmos = {
  id: 52077,
  decisionMaking: 'Tuna Soft transmitting',
  isRegulatory: true,
  projectNameOrThemeNumber: 'system',
};

export const sampleWithPartialData: IProjectSupportedCosmos = {
  id: 12163,
  decisionMaking: 'silver',
  isRegulatory: false,
  projectNameOrThemeNumber: 'Pike',
};

export const sampleWithFullData: IProjectSupportedCosmos = {
  id: 97122,
  decisionMaking: 'front-end Fresh SDD',
  isRegulatory: true,
  projectNameOrThemeNumber: 'Re-contextualized',
};

export const sampleWithNewData: NewProjectSupportedCosmos = {
  decisionMaking: 'Quality lime',
  isRegulatory: false,
  projectNameOrThemeNumber: 'Crescent',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
