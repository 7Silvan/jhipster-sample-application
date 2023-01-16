import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../publication-cosmos.test-samples';

import { PublicationCosmosFormService } from './publication-cosmos-form.service';

describe('PublicationCosmos Form Service', () => {
  let service: PublicationCosmosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PublicationCosmosFormService);
  });

  describe('Service methods', () => {
    describe('createPublicationCosmosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPublicationCosmosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            link: expect.any(Object),
            cellModel: expect.any(Object),
          })
        );
      });

      it('passing IPublicationCosmos should create a new form with FormGroup', () => {
        const formGroup = service.createPublicationCosmosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            link: expect.any(Object),
            cellModel: expect.any(Object),
          })
        );
      });
    });

    describe('getPublicationCosmos', () => {
      it('should return NewPublicationCosmos for default PublicationCosmos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPublicationCosmosFormGroup(sampleWithNewData);

        const publication = service.getPublicationCosmos(formGroup) as any;

        expect(publication).toMatchObject(sampleWithNewData);
      });

      it('should return NewPublicationCosmos for empty PublicationCosmos initial value', () => {
        const formGroup = service.createPublicationCosmosFormGroup();

        const publication = service.getPublicationCosmos(formGroup) as any;

        expect(publication).toMatchObject({});
      });

      it('should return IPublicationCosmos', () => {
        const formGroup = service.createPublicationCosmosFormGroup(sampleWithRequiredData);

        const publication = service.getPublicationCosmos(formGroup) as any;

        expect(publication).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPublicationCosmos should not enable id FormControl', () => {
        const formGroup = service.createPublicationCosmosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPublicationCosmos should disable id FormControl', () => {
        const formGroup = service.createPublicationCosmosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
