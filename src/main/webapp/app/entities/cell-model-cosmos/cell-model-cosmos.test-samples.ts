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
  id: '402e1870-e41b-4760-915d-d0a8a65a9218',
  species: 'Rubber program Optimized',
  targetOrgan: 'Pula quantifying back-end',
  targetTissue: 'Cotton',
  vesselInfo: 'initiatives Home',
  ratingCharacterization: 22306,
  ratingComplexity: 24530,
  ratingModelCost: 74925,
  ratingThroughPutCapacity: 4354,
  ratingTurnAroundTime: 13576,
  department: 'one-to-one',
  modelState: 'Ball card Licensed',
  applicationADME: false,
  applicationEfficacy: true,
  applicationNone: false,
  applicationSafety: true,
  animalAssayReplaced: 'generating Industrial Chair',
  note: 'Euro',
  limitationPerceived: 'Soap',
};

export const sampleWithFullData: ICellModelCosmos = {
  id: 'de82b657-9a28-4dfd-8393-3f0ea3b7267d',
  dateAdded: dayjs('2023-01-16T00:29'),
  dateUpdated: dayjs('2023-01-15T20:53'),
  species: 'withdrawal Human',
  targetOrgan: 'sensor',
  targetTissue: 'matrix bypassing User-friendly',
  vesselInfo: 'Forges human-resource',
  protocol: 'Wooden payment',
  manufacturer: 'sexy system',
  matrixType: 'Krone',
  vesselType: 'foreground card Fundamental',
  inhouse: true,
  ratingCharacterization: 45094,
  ratingComplexity: 41575,
  ratingModelCost: 54609,
  ratingThroughPutCapacity: 43175,
  ratingTurnAroundTime: 6746,
  imageUrl: 'Antilles Valley',
  department: 'Wooden',
  modelState: 'Wallis Plastic',
  applicationADME: true,
  applicationEfficacy: true,
  applicationNone: true,
  applicationSafety: false,
  animalAssayReplaced: 'synthesize withdrawal JBOD',
  note: 'Unbranded THX engineer',
  experts: 'Naira Koruna generating',
  link: 'hacking',
  limitationPerceived: 'e-commerce Alley target',
};

export const sampleWithNewData: NewCellModelCosmos = {
  species: 'Bedfordshire redundant',
  targetOrgan: 'transmitter',
  targetTissue: 'Sleek Sleek Tools',
  vesselInfo: 'quantifying Optional Keyboard',
  ratingCharacterization: 28033,
  ratingComplexity: 26734,
  ratingModelCost: 8381,
  ratingThroughPutCapacity: 92585,
  ratingTurnAroundTime: 50408,
  department: 'invoice',
  modelState: 'International',
  applicationADME: true,
  applicationEfficacy: true,
  applicationNone: false,
  applicationSafety: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
