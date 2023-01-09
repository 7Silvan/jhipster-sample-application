import dayjs from 'dayjs/esm';

import { ICellModelCosmos, NewCellModelCosmos } from './cell-model-cosmos.model';

export const sampleWithRequiredData: ICellModelCosmos = {
  id: '029bde0d-fa20-4f15-9227-973bbe9a9f9e',
  species: 'Missouri and Cove',
  targetOrgan: 'Chips viral Refined',
  targetTissue: 'Handmade',
  vesselInfo: 'vertical services',
  ratingCharacterization: 50167,
  ratingComplexity: 14714,
  ratingModelCost: 73915,
  ratingThroughPutCapacity: 40517,
  ratingTurnAroundTime: 40006,
  department: 'well-modulated Regional',
  modelState: 'Towels',
  applicationADME: false,
  applicationEfficacy: true,
  applicationNone: false,
  applicationSafety: true,
};

export const sampleWithPartialData: ICellModelCosmos = {
  id: '2e1870e4-1b76-40d1-9dd0-a8a65a9218d1',
  species: 'Realigned Future',
  targetOrgan: 'Developer transparent synthesizing',
  targetTissue: 'Music',
  vesselInfo: 'killer implement Manager',
  inhouse: true,
  ratingCharacterization: 4354,
  ratingComplexity: 13576,
  ratingModelCost: 17387,
  ratingThroughPutCapacity: 36189,
  ratingTurnAroundTime: 21927,
  imageUrl: 'Corporate wireless Associate',
  department: 'client-server',
  modelState: 'Salad generating Industrial',
  applicationADME: false,
  applicationEfficacy: false,
  applicationNone: false,
  applicationSafety: false,
  experts: 'Clothing Rustic programming',
};

export const sampleWithFullData: ICellModelCosmos = {
  id: '2b6579a2-8dfd-4439-b3f0-ea3b7267d468',
  name: 'synergy SSL',
  dateAdded: dayjs('2023-01-09T02:56'),
  dateUpdated: dayjs('2023-01-09T09:01'),
  species: 'SMS',
  targetOrgan: 'bypassing User-friendly',
  targetTissue: 'Forges human-resource',
  vesselInfo: 'Wooden payment',
  matrix: true,
  protocol: 'Borders experiences',
  manufacturer: 'lime foreground card',
  matrixType: 'virtual',
  vesselType: 'architectures Tuna',
  inhouse: false,
  ratingCharacterization: 91940,
  ratingComplexity: 5481,
  ratingModelCost: 10024,
  ratingThroughPutCapacity: 12422,
  ratingTurnAroundTime: 13380,
  imageUrl: 'Account yellow',
  department: 'THX Automotive',
  modelState: 'multimedia exploit Handmade',
  applicationADME: false,
  applicationEfficacy: false,
  applicationNone: true,
  applicationSafety: true,
  animalAssayReplaced: 'engineer wireless',
  comment: 'Koruna',
  experts: 'Investment hacking Administrator',
  link: 'Alley target',
  limitationPercieved: 'Bedfordshire redundant',
};

export const sampleWithNewData: NewCellModelCosmos = {
  species: 'transmitter',
  targetOrgan: 'Sleek Sleek Tools',
  targetTissue: 'quantifying Optional Keyboard',
  vesselInfo: 'asymmetric',
  ratingCharacterization: 50408,
  ratingComplexity: 32178,
  ratingModelCost: 65066,
  ratingThroughPutCapacity: 93811,
  ratingTurnAroundTime: 31172,
  department: 'International',
  modelState: 'Avon Harbor Implementation',
  applicationADME: true,
  applicationEfficacy: false,
  applicationNone: true,
  applicationSafety: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
