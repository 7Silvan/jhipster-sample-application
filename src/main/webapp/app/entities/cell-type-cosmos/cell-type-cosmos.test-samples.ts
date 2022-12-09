import { ICellTypeCosmos, NewCellTypeCosmos } from './cell-type-cosmos.model';

export const sampleWithRequiredData: ICellTypeCosmos = {
  id: 95828,
  cellSourceInfo: 'Intelligent Quality Moldova',
  cellArchitecture: 'mint invoice Garden',
  vendor: 'lavender SCSI Rial',
};

export const sampleWithPartialData: ICellTypeCosmos = {
  id: 23794,
  cellSourceInfo: 'Bedfordshire model Frozen',
  cellArchitecture: 'hacking Borders architect',
  vendor: 'ivory',
};

export const sampleWithFullData: ICellTypeCosmos = {
  id: 35949,
  cellSourceInfo: 'projection',
  cellArchitecture: 'Fish',
  vendor: 'Missouri',
};

export const sampleWithNewData: NewCellTypeCosmos = {
  cellSourceInfo: 'Borders Incredible',
  cellArchitecture: 'moratorium',
  vendor: 'Crossroad',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
