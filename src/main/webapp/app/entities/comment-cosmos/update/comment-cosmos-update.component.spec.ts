import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommentCosmosFormService } from './comment-cosmos-form.service';
import { CommentCosmosService } from '../service/comment-cosmos.service';
import { ICommentCosmos } from '../comment-cosmos.model';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { CommentCosmosUpdateComponent } from './comment-cosmos-update.component';

describe('CommentCosmos Management Update Component', () => {
  let comp: CommentCosmosUpdateComponent;
  let fixture: ComponentFixture<CommentCosmosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commentFormService: CommentCosmosFormService;
  let commentService: CommentCosmosService;
  let cellModelService: CellModelCosmosService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommentCosmosUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CommentCosmosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommentCosmosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commentFormService = TestBed.inject(CommentCosmosFormService);
    commentService = TestBed.inject(CommentCosmosService);
    cellModelService = TestBed.inject(CellModelCosmosService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CellModelCosmos query and add missing value', () => {
      const comment: ICommentCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: '250d660b-2bf6-435b-886e-7ea13c4db6c6' };
      comment.cellModel = cellModel;

      const cellModelCollection: ICellModelCosmos[] = [{ id: '1bf69683-974d-45a2-bbb1-d73878dc48d1' }];
      jest.spyOn(cellModelService, 'query').mockReturnValue(of(new HttpResponse({ body: cellModelCollection })));
      const additionalCellModelCosmos = [cellModel];
      const expectedCollection: ICellModelCosmos[] = [...additionalCellModelCosmos, ...cellModelCollection];
      jest.spyOn(cellModelService, 'addCellModelCosmosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comment });
      comp.ngOnInit();

      expect(cellModelService.query).toHaveBeenCalled();
      expect(cellModelService.addCellModelCosmosToCollectionIfMissing).toHaveBeenCalledWith(
        cellModelCollection,
        ...additionalCellModelCosmos.map(expect.objectContaining)
      );
      expect(comp.cellModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const comment: ICommentCosmos = { id: 456 };
      const user: IUser = { id: 19523 };
      comment.user = user;

      const userCollection: IUser[] = [{ id: 85581 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comment });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const comment: ICommentCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: 'f87952e7-7b00-44f3-95c8-cd27bfacae66' };
      comment.cellModel = cellModel;
      const user: IUser = { id: 75754 };
      comment.user = user;

      activatedRoute.data = of({ comment });
      comp.ngOnInit();

      expect(comp.cellModelsSharedCollection).toContain(cellModel);
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.comment).toEqual(comment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommentCosmos>>();
      const comment = { id: 123 };
      jest.spyOn(commentFormService, 'getCommentCosmos').mockReturnValue(comment);
      jest.spyOn(commentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comment }));
      saveSubject.complete();

      // THEN
      expect(commentFormService.getCommentCosmos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commentService.update).toHaveBeenCalledWith(expect.objectContaining(comment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommentCosmos>>();
      const comment = { id: 123 };
      jest.spyOn(commentFormService, 'getCommentCosmos').mockReturnValue({ id: null });
      jest.spyOn(commentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comment }));
      saveSubject.complete();

      // THEN
      expect(commentFormService.getCommentCosmos).toHaveBeenCalled();
      expect(commentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommentCosmos>>();
      const comment = { id: 123 };
      jest.spyOn(commentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCellModelCosmos', () => {
      it('Should forward to cellModelService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(cellModelService, 'compareCellModelCosmos');
        comp.compareCellModelCosmos(entity, entity2);
        expect(cellModelService.compareCellModelCosmos).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
