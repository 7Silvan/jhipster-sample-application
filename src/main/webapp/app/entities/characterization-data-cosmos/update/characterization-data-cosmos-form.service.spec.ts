import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../characterization-data-cosmos.test-samples';

import { CharacterizationDataCosmosFormService } from './characterization-data-cosmos-form.service';

describe('CharacterizationDataCosmos Form Service', () => {
  let service: CharacterizationDataCosmosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CharacterizationDataCosmosFormService);
  });

  describe('Service methods', () => {
    describe('createCharacterizationDataCosmosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCharacterizationDataCosmosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            link: expect.any(Object),
            cellModel: expect.any(Object),
          })
        );
      });

      it('passing ICharacterizationDataCosmos should create a new form with FormGroup', () => {
        const formGroup = service.createCharacterizationDataCosmosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            link: expect.any(Object),
            cellModel: expect.any(Object),
          })
        );
      });
    });

    describe('getCharacterizationDataCosmos', () => {
      it('should return NewCharacterizationDataCosmos for default CharacterizationDataCosmos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCharacterizationDataCosmosFormGroup(sampleWithNewData);

        const characterizationData = service.getCharacterizationDataCosmos(formGroup) as any;

        expect(characterizationData).toMatchObject(sampleWithNewData);
      });

      it('should return NewCharacterizationDataCosmos for empty CharacterizationDataCosmos initial value', () => {
        const formGroup = service.createCharacterizationDataCosmosFormGroup();

        const characterizationData = service.getCharacterizationDataCosmos(formGroup) as any;

        expect(characterizationData).toMatchObject({});
      });

      it('should return ICharacterizationDataCosmos', () => {
        const formGroup = service.createCharacterizationDataCosmosFormGroup(sampleWithRequiredData);

        const characterizationData = service.getCharacterizationDataCosmos(formGroup) as any;

        expect(characterizationData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICharacterizationDataCosmos should not enable id FormControl', () => {
        const formGroup = service.createCharacterizationDataCosmosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCharacterizationDataCosmos should disable id FormControl', () => {
        const formGroup = service.createCharacterizationDataCosmosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
