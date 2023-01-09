import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PublicationCosmosFormService, PublicationCosmosFormGroup } from './publication-cosmos-form.service';
import { IPublicationCosmos } from '../publication-cosmos.model';
import { PublicationCosmosService } from '../service/publication-cosmos.service';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

@Component({
  selector: 'jhi-publication-cosmos-update',
  templateUrl: './publication-cosmos-update.component.html',
})
export class PublicationCosmosUpdateComponent implements OnInit {
  isSaving = false;
  publication: IPublicationCosmos | null = null;

  cellModelsSharedCollection: ICellModelCosmos[] = [];

  editForm: PublicationCosmosFormGroup = this.publicationFormService.createPublicationCosmosFormGroup();

  constructor(
    protected publicationService: PublicationCosmosService,
    protected publicationFormService: PublicationCosmosFormService,
    protected cellModelService: CellModelCosmosService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCellModelCosmos = (o1: ICellModelCosmos | null, o2: ICellModelCosmos | null): boolean =>
    this.cellModelService.compareCellModelCosmos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publication }) => {
      this.publication = publication;
      if (publication) {
        this.updateForm(publication);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const publication = this.publicationFormService.getPublicationCosmos(this.editForm);
    if (publication.id !== null) {
      this.subscribeToSaveResponse(this.publicationService.update(publication));
    } else {
      this.subscribeToSaveResponse(this.publicationService.create(publication));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublicationCosmos>>): void {
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

  protected updateForm(publication: IPublicationCosmos): void {
    this.publication = publication;
    this.publicationFormService.resetForm(this.editForm, publication);

    this.cellModelsSharedCollection = this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(
      this.cellModelsSharedCollection,
      publication.cellModel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cellModelService
      .query()
      .pipe(map((res: HttpResponse<ICellModelCosmos[]>) => res.body ?? []))
      .pipe(
        map((cellModels: ICellModelCosmos[]) =>
          this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(cellModels, this.publication?.cellModel)
        )
      )
      .subscribe((cellModels: ICellModelCosmos[]) => (this.cellModelsSharedCollection = cellModels));
  }
}
