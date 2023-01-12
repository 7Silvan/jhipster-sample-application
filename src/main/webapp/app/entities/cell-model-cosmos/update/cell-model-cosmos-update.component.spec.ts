import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CellModelCosmosFormService } from './cell-model-cosmos-form.service';
import { CellModelCosmosService } from '../service/cell-model-cosmos.service';
import { ICellModelCosmos } from '../cell-model-cosmos.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { CellModelCosmosUpdateComponent } from './cell-model-cosmos-update.component';

describe('CellModelCosmos Management Update Component', () => {
  let comp: CellModelCosmosUpdateComponent;
  let fixture: ComponentFixture<CellModelCosmosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cellModelFormService: CellModelCosmosFormService;
  let cellModelService: CellModelCosmosService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CellModelCosmosUpdateComponent],
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
      .overrideTemplate(CellModelCosmosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CellModelCosmosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cellModelFormService = TestBed.inject(CellModelCosmosFormService);
    cellModelService = TestBed.inject(CellModelCosmosService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const cellModel: ICellModelCosmos = { id: 'CBA' };
      const user: IUser = { id: 66728 };
      cellModel.user = user;

      const userCollection: IUser[] = [{ id: 28281 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cellModel });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cellModel: ICellModelCosmos = { id: 'CBA' };
      const user: IUser = { id: 60289 };
      cellModel.user = user;

      activatedRoute.data = of({ cellModel });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.cellModel).toEqual(cellModel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICellModelCosmos>>();
      const cellModel = { id: 'ABC' };
      jest.spyOn(cellModelFormService, 'getCellModelCosmos').mockReturnValue(cellModel);
      jest.spyOn(cellModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cellModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cellModel }));
      saveSubject.complete();

      // THEN
      expect(cellModelFormService.getCellModelCosmos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cellModelService.update).toHaveBeenCalledWith(expect.objectContaining(cellModel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICellModelCosmos>>();
      const cellModel = { id: 'ABC' };
      jest.spyOn(cellModelFormService, 'getCellModelCosmos').mockReturnValue({ id: null });
      jest.spyOn(cellModelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cellModel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cellModel }));
      saveSubject.complete();

      // THEN
      expect(cellModelFormService.getCellModelCosmos).toHaveBeenCalled();
      expect(cellModelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICellModelCosmos>>();
      const cellModel = { id: 'ABC' };
      jest.spyOn(cellModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cellModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cellModelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
