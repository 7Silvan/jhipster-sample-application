import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPublicationCosmos, NewPublicationCosmos } from '../publication-cosmos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPublicationCosmos for edit and NewPublicationCosmosFormGroupInput for create.
 */
type PublicationCosmosFormGroupInput = IPublicationCosmos | PartialWithRequiredKeyOf<NewPublicationCosmos>;

type PublicationCosmosFormDefaults = Pick<NewPublicationCosmos, 'id'>;

type PublicationCosmosFormGroupContent = {
  id: FormControl<IPublicationCosmos['id'] | NewPublicationCosmos['id']>;
  link: FormControl<IPublicationCosmos['link']>;
  cellModel: FormControl<IPublicationCosmos['cellModel']>;
};

export type PublicationCosmosFormGroup = FormGroup<PublicationCosmosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PublicationCosmosFormService {
  createPublicationCosmosFormGroup(publication: PublicationCosmosFormGroupInput = { id: null }): PublicationCosmosFormGroup {
    const publicationRawValue = {
      ...this.getFormDefaults(),
      ...publication,
    };
    return new FormGroup<PublicationCosmosFormGroupContent>({
      id: new FormControl(
        { value: publicationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      link: new FormControl(publicationRawValue.link, {
        validators: [Validators.required],
      }),
      cellModel: new FormControl(publicationRawValue.cellModel),
    });
  }

  getPublicationCosmos(form: PublicationCosmosFormGroup): IPublicationCosmos | NewPublicationCosmos {
    return form.getRawValue() as IPublicationCosmos | NewPublicationCosmos;
  }

  resetForm(form: PublicationCosmosFormGroup, publication: PublicationCosmosFormGroupInput): void {
    const publicationRawValue = { ...this.getFormDefaults(), ...publication };
    form.reset(
      {
        ...publicationRawValue,
        id: { value: publicationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PublicationCosmosFormDefaults {
    return {
      id: null,
    };
  }
}
