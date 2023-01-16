import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CharacterizationDataCosmosFormService, CharacterizationDataCosmosFormGroup } from './characterization-data-cosmos-form.service';
import { ICharacterizationDataCosmos } from '../characterization-data-cosmos.model';
import { CharacterizationDataCosmosService } from '../service/characterization-data-cosmos.service';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

@Component({
  selector: 'jhi-characterization-data-cosmos-update',
  templateUrl: './characterization-data-cosmos-update.component.html',
})
export class CharacterizationDataCosmosUpdateComponent implements OnInit {
  isSaving = false;
  characterizationData: ICharacterizationDataCosmos | null = null;

  cellModelsSharedCollection: ICellModelCosmos[] = [];

  editForm: CharacterizationDataCosmosFormGroup = this.characterizationDataFormService.createCharacterizationDataCosmosFormGroup();

  constructor(
    protected characterizationDataService: CharacterizationDataCosmosService,
    protected characterizationDataFormService: CharacterizationDataCosmosFormService,
    protected cellModelService: CellModelCosmosService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCellModelCosmos = (o1: ICellModelCosmos | null, o2: ICellModelCosmos | null): boolean =>
    this.cellModelService.compareCellModelCosmos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ characterizationData }) => {
      this.characterizationData = characterizationData;
      if (characterizationData) {
        this.updateForm(characterizationData);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const characterizationData = this.characterizationDataFormService.getCharacterizationDataCosmos(this.editForm);
    if (characterizationData.id !== null) {
      this.subscribeToSaveResponse(this.characterizationDataService.update(characterizationData));
    } else {
      this.subscribeToSaveResponse(this.characterizationDataService.create(characterizationData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICharacterizationDataCosmos>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(characterizationData: ICharacterizationDataCosmos): void {
    this.characterizationData = characterizationData;
    this.characterizationDataFormService.resetForm(this.editForm, characterizationData);

    this.cellModelsSharedCollection = this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(
      this.cellModelsSharedCollection,
      characterizationData.cellModel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cellModelService
      .query()
      .pipe(map((res: HttpResponse<ICellModelCosmos[]>) => res.body ?? []))
      .pipe(
        map((cellModels: ICellModelCosmos[]) =>
          this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(cellModels, this.characterizationData?.cellModel)
        )
      )
      .subscribe((cellModels: ICellModelCosmos[]) => (this.cellModelsSharedCollection = cellModels));
  }
}
