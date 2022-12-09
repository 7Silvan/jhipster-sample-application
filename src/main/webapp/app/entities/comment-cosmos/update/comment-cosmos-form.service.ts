import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICommentCosmos, NewCommentCosmos } from '../comment-cosmos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommentCosmos for edit and NewCommentCosmosFormGroupInput for create.
 */
type CommentCosmosFormGroupInput = ICommentCosmos | PartialWithRequiredKeyOf<NewCommentCosmos>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICommentCosmos | NewCommentCosmos> = Omit<T, 'timedate'> & {
  timedate?: string | null;
};

type CommentCosmosFormRawValue = FormValueOf<ICommentCosmos>;

type NewCommentCosmosFormRawValue = FormValueOf<NewCommentCosmos>;

type CommentCosmosFormDefaults = Pick<NewCommentCosmos, 'id' | 'timedate'>;

type CommentCosmosFormGroupContent = {
  id: FormControl<CommentCosmosFormRawValue['id'] | NewCommentCosmos['id']>;
  text: FormControl<CommentCosmosFormRawValue['text']>;
  timedate: FormControl<CommentCosmosFormRawValue['timedate']>;
  cellModel: FormControl<CommentCosmosFormRawValue['cellModel']>;
  user: FormControl<CommentCosmosFormRawValue['user']>;
};

export type CommentCosmosFormGroup = FormGroup<CommentCosmosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommentCosmosFormService {
  createCommentCosmosFormGroup(comment: CommentCosmosFormGroupInput = { id: null }): CommentCosmosFormGroup {
    const commentRawValue = this.convertCommentCosmosToCommentCosmosRawValue({
      ...this.getFormDefaults(),
      ...comment,
    });
    return new FormGroup<CommentCosmosFormGroupContent>({
      id: new FormControl(
        { value: commentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      text: new FormControl(commentRawValue.text, {
        validators: [Validators.required],
      }),
      timedate: new FormControl(commentRawValue.timedate, {
        validators: [Validators.required],
      }),
      cellModel: new FormControl(commentRawValue.cellModel),
      user: new FormControl(commentRawValue.user),
    });
  }

  getCommentCosmos(form: CommentCosmosFormGroup): ICommentCosmos | NewCommentCosmos {
    return this.convertCommentCosmosRawValueToCommentCosmos(form.getRawValue() as CommentCosmosFormRawValue | NewCommentCosmosFormRawValue);
  }

  resetForm(form: CommentCosmosFormGroup, comment: CommentCosmosFormGroupInput): void {
    const commentRawValue = this.convertCommentCosmosToCommentCosmosRawValue({ ...this.getFormDefaults(), ...comment });
    form.reset(
      {
        ...commentRawValue,
        id: { value: commentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CommentCosmosFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timedate: currentTime,
    };
  }

  private convertCommentCosmosRawValueToCommentCosmos(
    rawCommentCosmos: CommentCosmosFormRawValue | NewCommentCosmosFormRawValue
  ): ICommentCosmos | NewCommentCosmos {
    return {
      ...rawCommentCosmos,
      timedate: dayjs(rawCommentCosmos.timedate, DATE_TIME_FORMAT),
    };
  }

  private convertCommentCosmosToCommentCosmosRawValue(
    comment: ICommentCosmos | (Partial<NewCommentCosmos> & CommentCosmosFormDefaults)
  ): CommentCosmosFormRawValue | PartialWithRequiredKeyOf<NewCommentCosmosFormRawValue> {
    return {
      ...comment,
      timedate: comment.timedate ? comment.timedate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
