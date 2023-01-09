import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../comment-cosmos.test-samples';

import { CommentCosmosFormService } from './comment-cosmos-form.service';

describe('CommentCosmos Form Service', () => {
  let service: CommentCosmosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommentCosmosFormService);
  });

  describe('Service methods', () => {
    describe('createCommentCosmosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommentCosmosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            text: expect.any(Object),
            timedate: expect.any(Object),
            cellModel: expect.any(Object),
            user: expect.any(Object),
          })
        );
      });

      it('passing ICommentCosmos should create a new form with FormGroup', () => {
        const formGroup = service.createCommentCosmosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            text: expect.any(Object),
            timedate: expect.any(Object),
            cellModel: expect.any(Object),
            user: expect.any(Object),
          })
        );
      });
    });

    describe('getCommentCosmos', () => {
      it('should return NewCommentCosmos for default CommentCosmos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCommentCosmosFormGroup(sampleWithNewData);

        const comment = service.getCommentCosmos(formGroup) as any;

        expect(comment).toMatchObject(sampleWithNewData);
      });

      it('should return NewCommentCosmos for empty CommentCosmos initial value', () => {
        const formGroup = service.createCommentCosmosFormGroup();

        const comment = service.getCommentCosmos(formGroup) as any;

        expect(comment).toMatchObject({});
      });

      it('should return ICommentCosmos', () => {
        const formGroup = service.createCommentCosmosFormGroup(sampleWithRequiredData);

        const comment = service.getCommentCosmos(formGroup) as any;

        expect(comment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICommentCosmos should not enable id FormControl', () => {
        const formGroup = service.createCommentCosmosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCommentCosmos should disable id FormControl', () => {
        const formGroup = service.createCommentCosmosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
