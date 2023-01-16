import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICharacterizationDataCosmos, NewCharacterizationDataCosmos } from '../characterization-data-cosmos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICharacterizationDataCosmos for edit and NewCharacterizationDataCosmosFormGroupInput for create.
 */
type CharacterizationDataCosmosFormGroupInput = ICharacterizationDataCosmos | PartialWithRequiredKeyOf<NewCharacterizationDataCosmos>;

type CharacterizationDataCosmosFormDefaults = Pick<NewCharacterizationDataCosmos, 'id'>;

type CharacterizationDataCosmosFormGroupContent = {
  id: FormControl<ICharacterizationDataCosmos['id'] | NewCharacterizationDataCosmos['id']>;
  description: FormControl<ICharacterizationDataCosmos['description']>;
  link: FormControl<ICharacterizationDataCosmos['link']>;
  cellModel: FormControl<ICharacterizationDataCosmos['cellModel']>;
};

export type CharacterizationDataCosmosFormGroup = FormGroup<CharacterizationDataCosmosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CharacterizationDataCosmosFormService {
  createCharacterizationDataCosmosFormGroup(
    characterizationData: CharacterizationDataCosmosFormGroupInput = { id: null }
  ): CharacterizationDataCosmosFormGroup {
    const characterizationDataRawValue = {
      ...this.getFormDefaults(),
      ...characterizationData,
    };
    return new FormGroup<CharacterizationDataCosmosFormGroupContent>({
      id: new FormControl(
        { value: characterizationDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      description: new FormControl(characterizationDataRawValue.description, {
        validators: [Validators.required],
      }),
      link: new FormControl(characterizationDataRawValue.link, {
        validators: [Validators.required],
      }),
      cellModel: new FormControl(characterizationDataRawValue.cellModel),
    });
  }

  getCharacterizationDataCosmos(form: CharacterizationDataCosmosFormGroup): ICharacterizationDataCosmos | NewCharacterizationDataCosmos {
    return form.getRawValue() as ICharacterizationDataCosmos | NewCharacterizationDataCosmos;
  }

  resetForm(form: CharacterizationDataCosmosFormGroup, characterizationData: CharacterizationDataCosmosFormGroupInput): void {
    const characterizationDataRawValue = { ...this.getFormDefaults(), ...characterizationData };
    form.reset(
      {
        ...characterizationDataRawValue,
        id: { value: characterizationDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CharacterizationDataCosmosFormDefaults {
    return {
      id: null,
    };
  }
}
