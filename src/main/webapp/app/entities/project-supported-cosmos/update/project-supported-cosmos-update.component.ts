import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProjectSupportedCosmosFormService, ProjectSupportedCosmosFormGroup } from './project-supported-cosmos-form.service';
import { IProjectSupportedCosmos } from '../project-supported-cosmos.model';
import { ProjectSupportedCosmosService } from '../service/project-supported-cosmos.service';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

@Component({
  selector: 'jhi-project-supported-cosmos-update',
  templateUrl: './project-supported-cosmos-update.component.html',
})
export class ProjectSupportedCosmosUpdateComponent implements OnInit {
  isSaving = false;
  projectSupported: IProjectSupportedCosmos | null = null;

  cellModelsSharedCollection: ICellModelCosmos[] = [];

  editForm: ProjectSupportedCosmosFormGroup = this.projectSupportedFormService.createProjectSupportedCosmosFormGroup();

  constructor(
    protected projectSupportedService: ProjectSupportedCosmosService,
    protected projectSupportedFormService: ProjectSupportedCosmosFormService,
    protected cellModelService: CellModelCosmosService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCellModelCosmos = (o1: ICellModelCosmos | null, o2: ICellModelCosmos | null): boolean =>
    this.cellModelService.compareCellModelCosmos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectSupported }) => {
      this.projectSupported = projectSupported;
      if (projectSupported) {
        this.updateForm(projectSupported);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectSupported = this.projectSupportedFormService.getProjectSupportedCosmos(this.editForm);
    if (projectSupported.id !== null) {
      this.subscribeToSaveResponse(this.projectSupportedService.update(projectSupported));
    } else {
      this.subscribeToSaveResponse(this.projectSupportedService.create(projectSupported));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectSupportedCosmos>>): void {
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

  protected updateForm(projectSupported: IProjectSupportedCosmos): void {
    this.projectSupported = projectSupported;
    this.projectSupportedFormService.resetForm(this.editForm, projectSupported);

    this.cellModelsSharedCollection = this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(
      this.cellModelsSharedCollection,
      projectSupported.cellModel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cellModelService
      .query()
      .pipe(map((res: HttpResponse<ICellModelCosmos[]>) => res.body ?? []))
      .pipe(
        map((cellModels: ICellModelCosmos[]) =>
          this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(cellModels, this.projectSupported?.cellModel)
        )
      )
      .subscribe((cellModels: ICellModelCosmos[]) => (this.cellModelsSharedCollection = cellModels));
  }
}
