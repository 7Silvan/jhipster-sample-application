import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cell-type-cosmos.test-samples';

import { CellTypeCosmosFormService } from './cell-type-cosmos-form.service';

describe('CellTypeCosmos Form Service', () => {
  let service: CellTypeCosmosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CellTypeCosmosFormService);
  });

  describe('Service methods', () => {
    describe('createCellTypeCosmosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCellTypeCosmosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cellSourceInfo: expect.any(Object),
            cellArchitecture: expect.any(Object),
            vendor: expect.any(Object),
            cellModel: expect.any(Object),
          })
        );
      });

      it('passing ICellTypeCosmos should create a new form with FormGroup', () => {
        const formGroup = service.createCellTypeCosmosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cellSourceInfo: expect.any(Object),
            cellArchitecture: expect.any(Object),
            vendor: expect.any(Object),
            cellModel: expect.any(Object),
          })
        );
      });
    });

    describe('getCellTypeCosmos', () => {
      it('should return NewCellTypeCosmos for default CellTypeCosmos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCellTypeCosmosFormGroup(sampleWithNewData);

        const cellType = service.getCellTypeCosmos(formGroup) as any;

        expect(cellType).toMatchObject(sampleWithNewData);
      });

      it('should return NewCellTypeCosmos for empty CellTypeCosmos initial value', () => {
        const formGroup = service.createCellTypeCosmosFormGroup();

        const cellType = service.getCellTypeCosmos(formGroup) as any;

        expect(cellType).toMatchObject({});
      });

      it('should return ICellTypeCosmos', () => {
        const formGroup = service.createCellTypeCosmosFormGroup(sampleWithRequiredData);

        const cellType = service.getCellTypeCosmos(formGroup) as any;

        expect(cellType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICellTypeCosmos should not enable id FormControl', () => {
        const formGroup = service.createCellTypeCosmosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCellTypeCosmos should disable id FormControl', () => {
        const formGroup = service.createCellTypeCosmosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
