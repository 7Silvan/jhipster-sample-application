import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cell-model-cosmos.test-samples';

import { CellModelCosmosFormService } from './cell-model-cosmos-form.service';

describe('CellModelCosmos Form Service', () => {
  let service: CellModelCosmosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CellModelCosmosFormService);
  });

  describe('Service methods', () => {
    describe('createCellModelCosmosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCellModelCosmosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dateAdded: expect.any(Object),
            dateUpdated: expect.any(Object),
            species: expect.any(Object),
            targetOrgan: expect.any(Object),
            targetTissue: expect.any(Object),
            vesselInfo: expect.any(Object),
            protocol: expect.any(Object),
            manufacturer: expect.any(Object),
            matrixType: expect.any(Object),
            vesselType: expect.any(Object),
            inhouse: expect.any(Object),
            ratingCharacterization: expect.any(Object),
            ratingComplexity: expect.any(Object),
            ratingModelCost: expect.any(Object),
            ratingThroughPutCapacity: expect.any(Object),
            ratingTurnAroundTime: expect.any(Object),
            imageUrl: expect.any(Object),
            department: expect.any(Object),
            modelState: expect.any(Object),
            applicationADME: expect.any(Object),
            applicationEfficacy: expect.any(Object),
            applicationNone: expect.any(Object),
            applicationSafety: expect.any(Object),
            animalAssayReplaced: expect.any(Object),
            note: expect.any(Object),
            experts: expect.any(Object),
            link: expect.any(Object),
            limitationPerceived: expect.any(Object),
            user: expect.any(Object),
          })
        );
      });

      it('passing ICellModelCosmos should create a new form with FormGroup', () => {
        const formGroup = service.createCellModelCosmosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dateAdded: expect.any(Object),
            dateUpdated: expect.any(Object),
            species: expect.any(Object),
            targetOrgan: expect.any(Object),
            targetTissue: expect.any(Object),
            vesselInfo: expect.any(Object),
            protocol: expect.any(Object),
            manufacturer: expect.any(Object),
            matrixType: expect.any(Object),
            vesselType: expect.any(Object),
            inhouse: expect.any(Object),
            ratingCharacterization: expect.any(Object),
            ratingComplexity: expect.any(Object),
            ratingModelCost: expect.any(Object),
            ratingThroughPutCapacity: expect.any(Object),
            ratingTurnAroundTime: expect.any(Object),
            imageUrl: expect.any(Object),
            department: expect.any(Object),
            modelState: expect.any(Object),
            applicationADME: expect.any(Object),
            applicationEfficacy: expect.any(Object),
            applicationNone: expect.any(Object),
            applicationSafety: expect.any(Object),
            animalAssayReplaced: expect.any(Object),
            note: expect.any(Object),
            experts: expect.any(Object),
            link: expect.any(Object),
            limitationPerceived: expect.any(Object),
            user: expect.any(Object),
          })
        );
      });
    });

    describe('getCellModelCosmos', () => {
      it('should return NewCellModelCosmos for default CellModelCosmos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCellModelCosmosFormGroup(sampleWithNewData);

        const cellModel = service.getCellModelCosmos(formGroup) as any;

        expect(cellModel).toMatchObject(sampleWithNewData);
      });

      it('should return NewCellModelCosmos for empty CellModelCosmos initial value', () => {
        const formGroup = service.createCellModelCosmosFormGroup();

        const cellModel = service.getCellModelCosmos(formGroup) as any;

        expect(cellModel).toMatchObject({});
      });

      it('should return ICellModelCosmos', () => {
        const formGroup = service.createCellModelCosmosFormGroup(sampleWithRequiredData);

        const cellModel = service.getCellModelCosmos(formGroup) as any;

        expect(cellModel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICellModelCosmos should not enable id FormControl', () => {
        const formGroup = service.createCellModelCosmosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCellModelCosmos should disable id FormControl', () => {
        const formGroup = service.createCellModelCosmosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
