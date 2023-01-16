import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CellTypeCosmosFormService } from './cell-type-cosmos-form.service';
import { CellTypeCosmosService } from '../service/cell-type-cosmos.service';
import { ICellTypeCosmos } from '../cell-type-cosmos.model';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

import { CellTypeCosmosUpdateComponent } from './cell-type-cosmos-update.component';

describe('CellTypeCosmos Management Update Component', () => {
  let comp: CellTypeCosmosUpdateComponent;
  let fixture: ComponentFixture<CellTypeCosmosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cellTypeFormService: CellTypeCosmosFormService;
  let cellTypeService: CellTypeCosmosService;
  let cellModelService: CellModelCosmosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CellTypeCosmosUpdateComponent],
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
      .overrideTemplate(CellTypeCosmosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CellTypeCosmosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cellTypeFormService = TestBed.inject(CellTypeCosmosFormService);
    cellTypeService = TestBed.inject(CellTypeCosmosService);
    cellModelService = TestBed.inject(CellModelCosmosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CellModelCosmos query and add missing value', () => {
      const cellType: ICellTypeCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: 'a8117b93-02fe-4994-9c5b-0463aa22d6a9' };
      cellType.cellModel = cellModel;

      const cellModelCollection: ICellModelCosmos[] = [{ id: 'cf4809b8-68dd-478d-a6bc-9410820b1aca' }];
      jest.spyOn(cellModelService, 'query').mockReturnValue(of(new HttpResponse({ body: cellModelCollection })));
      const additionalCellModelCosmos = [cellModel];
      const expectedCollection: ICellModelCosmos[] = [...additionalCellModelCosmos, ...cellModelCollection];
      jest.spyOn(cellModelService, 'addCellModelCosmosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cellType });
      comp.ngOnInit();

      expect(cellModelService.query).toHaveBeenCalled();
      expect(cellModelService.addCellModelCosmosToCollectionIfMissing).toHaveBeenCalledWith(
        cellModelCollection,
        ...additionalCellModelCosmos.map(expect.objectContaining)
      );
      expect(comp.cellModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cellType: ICellTypeCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: 'a3abda5d-ca1d-449d-813f-85e941b0da6d' };
      cellType.cellModel = cellModel;

      activatedRoute.data = of({ cellType });
      comp.ngOnInit();

      expect(comp.cellModelsSharedCollection).toContain(cellModel);
      expect(comp.cellType).toEqual(cellType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICellTypeCosmos>>();
      const cellType = { id: 123 };
      jest.spyOn(cellTypeFormService, 'getCellTypeCosmos').mockReturnValue(cellType);
      jest.spyOn(cellTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cellType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cellType }));
      saveSubject.complete();

      // THEN
      expect(cellTypeFormService.getCellTypeCosmos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cellTypeService.update).toHaveBeenCalledWith(expect.objectContaining(cellType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICellTypeCosmos>>();
      const cellType = { id: 123 };
      jest.spyOn(cellTypeFormService, 'getCellTypeCosmos').mockReturnValue({ id: null });
      jest.spyOn(cellTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cellType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cellType }));
      saveSubject.complete();

      // THEN
      expect(cellTypeFormService.getCellTypeCosmos).toHaveBeenCalled();
      expect(cellTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICellTypeCosmos>>();
      const cellType = { id: 123 };
      jest.spyOn(cellTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cellType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cellTypeService.update).toHaveBeenCalled();
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
  });
});
