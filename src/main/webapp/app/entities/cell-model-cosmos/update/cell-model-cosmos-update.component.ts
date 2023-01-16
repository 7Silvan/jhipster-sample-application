import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CellModelCosmosFormService, CellModelCosmosFormGroup } from './cell-model-cosmos-form.service';
import { ICellModelCosmos } from '../cell-model-cosmos.model';
import { CellModelCosmosService } from '../service/cell-model-cosmos.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-cell-model-cosmos-update',
  templateUrl: './cell-model-cosmos-update.component.html',
})
export class CellModelCosmosUpdateComponent implements OnInit {
  isSaving = false;
  cellModel: ICellModelCosmos | null = null;

  usersSharedCollection: IUser[] = [];

  editForm: CellModelCosmosFormGroup = this.cellModelFormService.createCellModelCosmosFormGroup();

  constructor(
    protected cellModelService: CellModelCosmosService,
    protected cellModelFormService: CellModelCosmosFormService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cellModel }) => {
      this.cellModel = cellModel;
      if (cellModel) {
        this.updateForm(cellModel);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cellModel = this.cellModelFormService.getCellModelCosmos(this.editForm);
    if (cellModel.id !== null) {
      this.subscribeToSaveResponse(this.cellModelService.update(cellModel));
    } else {
      this.subscribeToSaveResponse(this.cellModelService.create(cellModel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICellModelCosmos>>): void {
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

  protected updateForm(cellModel: ICellModelCosmos): void {
    this.cellModel = cellModel;
    this.cellModelFormService.resetForm(this.editForm, cellModel);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, cellModel.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.cellModel?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
