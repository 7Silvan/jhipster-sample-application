import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CellTypeCosmosFormService, CellTypeCosmosFormGroup } from './cell-type-cosmos-form.service';
import { ICellTypeCosmos } from '../cell-type-cosmos.model';
import { CellTypeCosmosService } from '../service/cell-type-cosmos.service';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

@Component({
  selector: 'jhi-cell-type-cosmos-update',
  templateUrl: './cell-type-cosmos-update.component.html',
})
export class CellTypeCosmosUpdateComponent implements OnInit {
  isSaving = false;
  cellType: ICellTypeCosmos | null = null;

  cellModelsSharedCollection: ICellModelCosmos[] = [];

  editForm: CellTypeCosmosFormGroup = this.cellTypeFormService.createCellTypeCosmosFormGroup();

  constructor(
    protected cellTypeService: CellTypeCosmosService,
    protected cellTypeFormService: CellTypeCosmosFormService,
    protected cellModelService: CellModelCosmosService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCellModelCosmos = (o1: ICellModelCosmos | null, o2: ICellModelCosmos | null): boolean =>
    this.cellModelService.compareCellModelCosmos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cellType }) => {
      this.cellType = cellType;
      if (cellType) {
        this.updateForm(cellType);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cellType = this.cellTypeFormService.getCellTypeCosmos(this.editForm);
    if (cellType.id !== null) {
      this.subscribeToSaveResponse(this.cellTypeService.update(cellType));
    } else {
      this.subscribeToSaveResponse(this.cellTypeService.create(cellType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICellTypeCosmos>>): void {
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

  protected updateForm(cellType: ICellTypeCosmos): void {
    this.cellType = cellType;
    this.cellTypeFormService.resetForm(this.editForm, cellType);

    this.cellModelsSharedCollection = this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(
      this.cellModelsSharedCollection,
      cellType.cellModel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cellModelService
      .query()
      .pipe(map((res: HttpResponse<ICellModelCosmos[]>) => res.body ?? []))
      .pipe(
        map((cellModels: ICellModelCosmos[]) =>
          this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(cellModels, this.cellType?.cellModel)
        )
      )
      .subscribe((cellModels: ICellModelCosmos[]) => (this.cellModelsSharedCollection = cellModels));
  }
}
