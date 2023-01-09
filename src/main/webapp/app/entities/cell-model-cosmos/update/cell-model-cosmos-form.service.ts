import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICellModelCosmos, NewCellModelCosmos } from '../cell-model-cosmos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICellModelCosmos for edit and NewCellModelCosmosFormGroupInput for create.
 */
type CellModelCosmosFormGroupInput = ICellModelCosmos | PartialWithRequiredKeyOf<NewCellModelCosmos>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICellModelCosmos | NewCellModelCosmos> = Omit<T, 'dateAdded' | 'dateUpdated'> & {
  dateAdded?: string | null;
  dateUpdated?: string | null;
};

type CellModelCosmosFormRawValue = FormValueOf<ICellModelCosmos>;

type NewCellModelCosmosFormRawValue = FormValueOf<NewCellModelCosmos>;

type CellModelCosmosFormDefaults = Pick<
  NewCellModelCosmos,
  | 'id'
  | 'dateAdded'
  | 'dateUpdated'
  | 'matrix'
  | 'inhouse'
  | 'applicationADME'
  | 'applicationEfficacy'
  | 'applicationNone'
  | 'applicationSafety'
>;

type CellModelCosmosFormGroupContent = {
  id: FormControl<CellModelCosmosFormRawValue['id'] | NewCellModelCosmos['id']>;
  name: FormControl<CellModelCosmosFormRawValue['name']>;
  dateAdded: FormControl<CellModelCosmosFormRawValue['dateAdded']>;
  dateUpdated: FormControl<CellModelCosmosFormRawValue['dateUpdated']>;
  species: FormControl<CellModelCosmosFormRawValue['species']>;
  targetOrgan: FormControl<CellModelCosmosFormRawValue['targetOrgan']>;
  targetTissue: FormControl<CellModelCosmosFormRawValue['targetTissue']>;
  vesselInfo: FormControl<CellModelCosmosFormRawValue['vesselInfo']>;
  matrix: FormControl<CellModelCosmosFormRawValue['matrix']>;
  protocol: FormControl<CellModelCosmosFormRawValue['protocol']>;
  manufacturer: FormControl<CellModelCosmosFormRawValue['manufacturer']>;
  matrixType: FormControl<CellModelCosmosFormRawValue['matrixType']>;
  vesselType: FormControl<CellModelCosmosFormRawValue['vesselType']>;
  inhouse: FormControl<CellModelCosmosFormRawValue['inhouse']>;
  ratingCharacterization: FormControl<CellModelCosmosFormRawValue['ratingCharacterization']>;
  ratingComplexity: FormControl<CellModelCosmosFormRawValue['ratingComplexity']>;
  ratingModelCost: FormControl<CellModelCosmosFormRawValue['ratingModelCost']>;
  ratingThroughPutCapacity: FormControl<CellModelCosmosFormRawValue['ratingThroughPutCapacity']>;
  ratingTurnAroundTime: FormControl<CellModelCosmosFormRawValue['ratingTurnAroundTime']>;
  imageUrl: FormControl<CellModelCosmosFormRawValue['imageUrl']>;
  department: FormControl<CellModelCosmosFormRawValue['department']>;
  modelState: FormControl<CellModelCosmosFormRawValue['modelState']>;
  applicationADME: FormControl<CellModelCosmosFormRawValue['applicationADME']>;
  applicationEfficacy: FormControl<CellModelCosmosFormRawValue['applicationEfficacy']>;
  applicationNone: FormControl<CellModelCosmosFormRawValue['applicationNone']>;
  applicationSafety: FormControl<CellModelCosmosFormRawValue['applicationSafety']>;
  animalAssayReplaced: FormControl<CellModelCosmosFormRawValue['animalAssayReplaced']>;
  comment: FormControl<CellModelCosmosFormRawValue['comment']>;
  experts: FormControl<CellModelCosmosFormRawValue['experts']>;
  link: FormControl<CellModelCosmosFormRawValue['link']>;
  limitationPercieved: FormControl<CellModelCosmosFormRawValue['limitationPercieved']>;
  user: FormControl<CellModelCosmosFormRawValue['user']>;
};

export type CellModelCosmosFormGroup = FormGroup<CellModelCosmosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CellModelCosmosFormService {
  createCellModelCosmosFormGroup(cellModel: CellModelCosmosFormGroupInput = { id: null }): CellModelCosmosFormGroup {
    const cellModelRawValue = this.convertCellModelCosmosToCellModelCosmosRawValue({
      ...this.getFormDefaults(),
      ...cellModel,
    });
    return new FormGroup<CellModelCosmosFormGroupContent>({
      id: new FormControl(
        { value: cellModelRawValue.id, disabled: cellModelRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(cellModelRawValue.name),
      dateAdded: new FormControl(cellModelRawValue.dateAdded),
      dateUpdated: new FormControl(cellModelRawValue.dateUpdated),
      species: new FormControl(cellModelRawValue.species, {
        validators: [Validators.required],
      }),
      targetOrgan: new FormControl(cellModelRawValue.targetOrgan, {
        validators: [Validators.required],
      }),
      targetTissue: new FormControl(cellModelRawValue.targetTissue, {
        validators: [Validators.required],
      }),
      vesselInfo: new FormControl(cellModelRawValue.vesselInfo, {
        validators: [Validators.required],
      }),
      matrix: new FormControl(cellModelRawValue.matrix),
      protocol: new FormControl(cellModelRawValue.protocol),
      manufacturer: new FormControl(cellModelRawValue.manufacturer),
      matrixType: new FormControl(cellModelRawValue.matrixType),
      vesselType: new FormControl(cellModelRawValue.vesselType),
      inhouse: new FormControl(cellModelRawValue.inhouse),
      ratingCharacterization: new FormControl(cellModelRawValue.ratingCharacterization, {
        validators: [Validators.required],
      }),
      ratingComplexity: new FormControl(cellModelRawValue.ratingComplexity, {
        validators: [Validators.required],
      }),
      ratingModelCost: new FormControl(cellModelRawValue.ratingModelCost, {
        validators: [Validators.required],
      }),
      ratingThroughPutCapacity: new FormControl(cellModelRawValue.ratingThroughPutCapacity, {
        validators: [Validators.required],
      }),
      ratingTurnAroundTime: new FormControl(cellModelRawValue.ratingTurnAroundTime, {
        validators: [Validators.required],
      }),
      imageUrl: new FormControl(cellModelRawValue.imageUrl),
      department: new FormControl(cellModelRawValue.department, {
        validators: [Validators.required],
      }),
      modelState: new FormControl(cellModelRawValue.modelState, {
        validators: [Validators.required],
      }),
      applicationADME: new FormControl(cellModelRawValue.applicationADME, {
        validators: [Validators.required],
      }),
      applicationEfficacy: new FormControl(cellModelRawValue.applicationEfficacy, {
        validators: [Validators.required],
      }),
      applicationNone: new FormControl(cellModelRawValue.applicationNone, {
        validators: [Validators.required],
      }),
      applicationSafety: new FormControl(cellModelRawValue.applicationSafety, {
        validators: [Validators.required],
      }),
      animalAssayReplaced: new FormControl(cellModelRawValue.animalAssayReplaced),
      comment: new FormControl(cellModelRawValue.comment),
      experts: new FormControl(cellModelRawValue.experts),
      link: new FormControl(cellModelRawValue.link),
      limitationPercieved: new FormControl(cellModelRawValue.limitationPercieved),
      user: new FormControl(cellModelRawValue.user),
    });
  }

  getCellModelCosmos(form: CellModelCosmosFormGroup): ICellModelCosmos | NewCellModelCosmos {
    return this.convertCellModelCosmosRawValueToCellModelCosmos(
      form.getRawValue() as CellModelCosmosFormRawValue | NewCellModelCosmosFormRawValue
    );
  }

  resetForm(form: CellModelCosmosFormGroup, cellModel: CellModelCosmosFormGroupInput): void {
    const cellModelRawValue = this.convertCellModelCosmosToCellModelCosmosRawValue({ ...this.getFormDefaults(), ...cellModel });
    form.reset(
      {
        ...cellModelRawValue,
        id: { value: cellModelRawValue.id, disabled: cellModelRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CellModelCosmosFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateAdded: currentTime,
      dateUpdated: currentTime,
      matrix: false,
      inhouse: false,
      applicationADME: false,
      applicationEfficacy: false,
      applicationNone: false,
      applicationSafety: false,
    };
  }

  private convertCellModelCosmosRawValueToCellModelCosmos(
    rawCellModelCosmos: CellModelCosmosFormRawValue | NewCellModelCosmosFormRawValue
  ): ICellModelCosmos | NewCellModelCosmos {
    return {
      ...rawCellModelCosmos,
      dateAdded: dayjs(rawCellModelCosmos.dateAdded, DATE_TIME_FORMAT),
      dateUpdated: dayjs(rawCellModelCosmos.dateUpdated, DATE_TIME_FORMAT),
    };
  }

  private convertCellModelCosmosToCellModelCosmosRawValue(
    cellModel: ICellModelCosmos | (Partial<NewCellModelCosmos> & CellModelCosmosFormDefaults)
  ): CellModelCosmosFormRawValue | PartialWithRequiredKeyOf<NewCellModelCosmosFormRawValue> {
    return {
      ...cellModel,
      dateAdded: cellModel.dateAdded ? cellModel.dateAdded.format(DATE_TIME_FORMAT) : undefined,
      dateUpdated: cellModel.dateUpdated ? cellModel.dateUpdated.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
