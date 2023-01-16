import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICellTypeCosmos, NewCellTypeCosmos } from '../cell-type-cosmos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICellTypeCosmos for edit and NewCellTypeCosmosFormGroupInput for create.
 */
type CellTypeCosmosFormGroupInput = ICellTypeCosmos | PartialWithRequiredKeyOf<NewCellTypeCosmos>;

type CellTypeCosmosFormDefaults = Pick<NewCellTypeCosmos, 'id'>;

type CellTypeCosmosFormGroupContent = {
  id: FormControl<ICellTypeCosmos['id'] | NewCellTypeCosmos['id']>;
  cellSourceInfo: FormControl<ICellTypeCosmos['cellSourceInfo']>;
  cellArchitecture: FormControl<ICellTypeCosmos['cellArchitecture']>;
  vendor: FormControl<ICellTypeCosmos['vendor']>;
  cellModel: FormControl<ICellTypeCosmos['cellModel']>;
};

export type CellTypeCosmosFormGroup = FormGroup<CellTypeCosmosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CellTypeCosmosFormService {
  createCellTypeCosmosFormGroup(cellType: CellTypeCosmosFormGroupInput = { id: null }): CellTypeCosmosFormGroup {
    const cellTypeRawValue = {
      ...this.getFormDefaults(),
      ...cellType,
    };
    return new FormGroup<CellTypeCosmosFormGroupContent>({
      id: new FormControl(
        { value: cellTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cellSourceInfo: new FormControl(cellTypeRawValue.cellSourceInfo, {
        validators: [Validators.required],
      }),
      cellArchitecture: new FormControl(cellTypeRawValue.cellArchitecture, {
        validators: [Validators.required],
      }),
      vendor: new FormControl(cellTypeRawValue.vendor, {
        validators: [Validators.required],
      }),
      cellModel: new FormControl(cellTypeRawValue.cellModel),
    });
  }

  getCellTypeCosmos(form: CellTypeCosmosFormGroup): ICellTypeCosmos | NewCellTypeCosmos {
    return form.getRawValue() as ICellTypeCosmos | NewCellTypeCosmos;
  }

  resetForm(form: CellTypeCosmosFormGroup, cellType: CellTypeCosmosFormGroupInput): void {
    const cellTypeRawValue = { ...this.getFormDefaults(), ...cellType };
    form.reset(
      {
        ...cellTypeRawValue,
        id: { value: cellTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CellTypeCosmosFormDefaults {
    return {
      id: null,
    };
  }
}
