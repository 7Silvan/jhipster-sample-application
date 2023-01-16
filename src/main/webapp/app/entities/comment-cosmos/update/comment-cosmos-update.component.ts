import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CommentCosmosFormService, CommentCosmosFormGroup } from './comment-cosmos-form.service';
import { ICommentCosmos } from '../comment-cosmos.model';
import { CommentCosmosService } from '../service/comment-cosmos.service';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-comment-cosmos-update',
  templateUrl: './comment-cosmos-update.component.html',
})
export class CommentCosmosUpdateComponent implements OnInit {
  isSaving = false;
  comment: ICommentCosmos | null = null;

  cellModelsSharedCollection: ICellModelCosmos[] = [];
  usersSharedCollection: IUser[] = [];

  editForm: CommentCosmosFormGroup = this.commentFormService.createCommentCosmosFormGroup();

  constructor(
    protected commentService: CommentCosmosService,
    protected commentFormService: CommentCosmosFormService,
    protected cellModelService: CellModelCosmosService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCellModelCosmos = (o1: ICellModelCosmos | null, o2: ICellModelCosmos | null): boolean =>
    this.cellModelService.compareCellModelCosmos(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.comment = comment;
      if (comment) {
        this.updateForm(comment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comment = this.commentFormService.getCommentCosmos(this.editForm);
    if (comment.id !== null) {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommentCosmos>>): void {
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

  protected updateForm(comment: ICommentCosmos): void {
    this.comment = comment;
    this.commentFormService.resetForm(this.editForm, comment);

    this.cellModelsSharedCollection = this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(
      this.cellModelsSharedCollection,
      comment.cellModel
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, comment.user);
  }

  protected loadRelationshipsOptions(): void {
    this.cellModelService
      .query()
      .pipe(map((res: HttpResponse<ICellModelCosmos[]>) => res.body ?? []))
      .pipe(
        map((cellModels: ICellModelCosmos[]) =>
          this.cellModelService.addCellModelCosmosToCollectionIfMissing<ICellModelCosmos>(cellModels, this.comment?.cellModel)
        )
      )
      .subscribe((cellModels: ICellModelCosmos[]) => (this.cellModelsSharedCollection = cellModels));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.comment?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
