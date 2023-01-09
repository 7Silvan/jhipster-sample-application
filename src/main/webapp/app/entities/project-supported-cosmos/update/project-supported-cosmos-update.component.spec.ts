import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectSupportedCosmosFormService } from './project-supported-cosmos-form.service';
import { ProjectSupportedCosmosService } from '../service/project-supported-cosmos.service';
import { IProjectSupportedCosmos } from '../project-supported-cosmos.model';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

import { ProjectSupportedCosmosUpdateComponent } from './project-supported-cosmos-update.component';

describe('ProjectSupportedCosmos Management Update Component', () => {
  let comp: ProjectSupportedCosmosUpdateComponent;
  let fixture: ComponentFixture<ProjectSupportedCosmosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectSupportedFormService: ProjectSupportedCosmosFormService;
  let projectSupportedService: ProjectSupportedCosmosService;
  let cellModelService: CellModelCosmosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectSupportedCosmosUpdateComponent],
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
      .overrideTemplate(ProjectSupportedCosmosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectSupportedCosmosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectSupportedFormService = TestBed.inject(ProjectSupportedCosmosFormService);
    projectSupportedService = TestBed.inject(ProjectSupportedCosmosService);
    cellModelService = TestBed.inject(CellModelCosmosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CellModelCosmos query and add missing value', () => {
      const projectSupported: IProjectSupportedCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: '6c9d2024-8bf0-4766-b6af-42e9cc574fdf' };
      projectSupported.cellModel = cellModel;

      const cellModelCollection: ICellModelCosmos[] = [{ id: 'ce47273d-b056-4419-adaf-2da901098ef7' }];
      jest.spyOn(cellModelService, 'query').mockReturnValue(of(new HttpResponse({ body: cellModelCollection })));
      const additionalCellModelCosmos = [cellModel];
      const expectedCollection: ICellModelCosmos[] = [...additionalCellModelCosmos, ...cellModelCollection];
      jest.spyOn(cellModelService, 'addCellModelCosmosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ projectSupported });
      comp.ngOnInit();

      expect(cellModelService.query).toHaveBeenCalled();
      expect(cellModelService.addCellModelCosmosToCollectionIfMissing).toHaveBeenCalledWith(
        cellModelCollection,
        ...additionalCellModelCosmos.map(expect.objectContaining)
      );
      expect(comp.cellModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const projectSupported: IProjectSupportedCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: '6a1b7f1e-9eeb-4014-9271-bf86fdbe088c' };
      projectSupported.cellModel = cellModel;

      activatedRoute.data = of({ projectSupported });
      comp.ngOnInit();

      expect(comp.cellModelsSharedCollection).toContain(cellModel);
      expect(comp.projectSupported).toEqual(projectSupported);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectSupportedCosmos>>();
      const projectSupported = { id: 123 };
      jest.spyOn(projectSupportedFormService, 'getProjectSupportedCosmos').mockReturnValue(projectSupported);
      jest.spyOn(projectSupportedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectSupported });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectSupported }));
      saveSubject.complete();

      // THEN
      expect(projectSupportedFormService.getProjectSupportedCosmos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectSupportedService.update).toHaveBeenCalledWith(expect.objectContaining(projectSupported));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectSupportedCosmos>>();
      const projectSupported = { id: 123 };
      jest.spyOn(projectSupportedFormService, 'getProjectSupportedCosmos').mockReturnValue({ id: null });
      jest.spyOn(projectSupportedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectSupported: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectSupported }));
      saveSubject.complete();

      // THEN
      expect(projectSupportedFormService.getProjectSupportedCosmos).toHaveBeenCalled();
      expect(projectSupportedService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectSupportedCosmos>>();
      const projectSupported = { id: 123 };
      jest.spyOn(projectSupportedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectSupported });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectSupportedService.update).toHaveBeenCalled();
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
