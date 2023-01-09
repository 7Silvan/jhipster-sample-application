import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProjectSupportedCosmos, NewProjectSupportedCosmos } from '../project-supported-cosmos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjectSupportedCosmos for edit and NewProjectSupportedCosmosFormGroupInput for create.
 */
type ProjectSupportedCosmosFormGroupInput = IProjectSupportedCosmos | PartialWithRequiredKeyOf<NewProjectSupportedCosmos>;

type ProjectSupportedCosmosFormDefaults = Pick<NewProjectSupportedCosmos, 'id' | 'isRegulatory'>;

type ProjectSupportedCosmosFormGroupContent = {
  id: FormControl<IProjectSupportedCosmos['id'] | NewProjectSupportedCosmos['id']>;
  decisionMaking: FormControl<IProjectSupportedCosmos['decisionMaking']>;
  isRegulatory: FormControl<IProjectSupportedCosmos['isRegulatory']>;
  projectNameOrThemeNumber: FormControl<IProjectSupportedCosmos['projectNameOrThemeNumber']>;
  cellModel: FormControl<IProjectSupportedCosmos['cellModel']>;
};

export type ProjectSupportedCosmosFormGroup = FormGroup<ProjectSupportedCosmosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectSupportedCosmosFormService {
  createProjectSupportedCosmosFormGroup(
    projectSupported: ProjectSupportedCosmosFormGroupInput = { id: null }
  ): ProjectSupportedCosmosFormGroup {
    const projectSupportedRawValue = {
      ...this.getFormDefaults(),
      ...projectSupported,
    };
    return new FormGroup<ProjectSupportedCosmosFormGroupContent>({
      id: new FormControl(
        { value: projectSupportedRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      decisionMaking: new FormControl(projectSupportedRawValue.decisionMaking, {
        validators: [Validators.required],
      }),
      isRegulatory: new FormControl(projectSupportedRawValue.isRegulatory, {
        validators: [Validators.required],
      }),
      projectNameOrThemeNumber: new FormControl(projectSupportedRawValue.projectNameOrThemeNumber, {
        validators: [Validators.required],
      }),
      cellModel: new FormControl(projectSupportedRawValue.cellModel),
    });
  }

  getProjectSupportedCosmos(form: ProjectSupportedCosmosFormGroup): IProjectSupportedCosmos | NewProjectSupportedCosmos {
    return form.getRawValue() as IProjectSupportedCosmos | NewProjectSupportedCosmos;
  }

  resetForm(form: ProjectSupportedCosmosFormGroup, projectSupported: ProjectSupportedCosmosFormGroupInput): void {
    const projectSupportedRawValue = { ...this.getFormDefaults(), ...projectSupported };
    form.reset(
      {
        ...projectSupportedRawValue,
        id: { value: projectSupportedRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectSupportedCosmosFormDefaults {
    return {
      id: null,
      isRegulatory: false,
    };
  }
}
