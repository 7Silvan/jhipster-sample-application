import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../project-supported-cosmos.test-samples';

import { ProjectSupportedCosmosFormService } from './project-supported-cosmos-form.service';

describe('ProjectSupportedCosmos Form Service', () => {
  let service: ProjectSupportedCosmosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectSupportedCosmosFormService);
  });

  describe('Service methods', () => {
    describe('createProjectSupportedCosmosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjectSupportedCosmosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            decisionMaking: expect.any(Object),
            isRegulatory: expect.any(Object),
            projectNameOrThemeNumber: expect.any(Object),
            cellModel: expect.any(Object),
          })
        );
      });

      it('passing IProjectSupportedCosmos should create a new form with FormGroup', () => {
        const formGroup = service.createProjectSupportedCosmosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            decisionMaking: expect.any(Object),
            isRegulatory: expect.any(Object),
            projectNameOrThemeNumber: expect.any(Object),
            cellModel: expect.any(Object),
          })
        );
      });
    });

    describe('getProjectSupportedCosmos', () => {
      it('should return NewProjectSupportedCosmos for default ProjectSupportedCosmos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProjectSupportedCosmosFormGroup(sampleWithNewData);

        const projectSupported = service.getProjectSupportedCosmos(formGroup) as any;

        expect(projectSupported).toMatchObject(sampleWithNewData);
      });

      it('should return NewProjectSupportedCosmos for empty ProjectSupportedCosmos initial value', () => {
        const formGroup = service.createProjectSupportedCosmosFormGroup();

        const projectSupported = service.getProjectSupportedCosmos(formGroup) as any;

        expect(projectSupported).toMatchObject({});
      });

      it('should return IProjectSupportedCosmos', () => {
        const formGroup = service.createProjectSupportedCosmosFormGroup(sampleWithRequiredData);

        const projectSupported = service.getProjectSupportedCosmos(formGroup) as any;

        expect(projectSupported).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProjectSupportedCosmos should not enable id FormControl', () => {
        const formGroup = service.createProjectSupportedCosmosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProjectSupportedCosmos should disable id FormControl', () => {
        const formGroup = service.createProjectSupportedCosmosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
