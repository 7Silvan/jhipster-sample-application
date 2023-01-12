import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CharacterizationDataCosmosFormService } from './characterization-data-cosmos-form.service';
import { CharacterizationDataCosmosService } from '../service/characterization-data-cosmos.service';
import { ICharacterizationDataCosmos } from '../characterization-data-cosmos.model';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

import { CharacterizationDataCosmosUpdateComponent } from './characterization-data-cosmos-update.component';

describe('CharacterizationDataCosmos Management Update Component', () => {
  let comp: CharacterizationDataCosmosUpdateComponent;
  let fixture: ComponentFixture<CharacterizationDataCosmosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let characterizationDataFormService: CharacterizationDataCosmosFormService;
  let characterizationDataService: CharacterizationDataCosmosService;
  let cellModelService: CellModelCosmosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CharacterizationDataCosmosUpdateComponent],
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
      .overrideTemplate(CharacterizationDataCosmosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CharacterizationDataCosmosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    characterizationDataFormService = TestBed.inject(CharacterizationDataCosmosFormService);
    characterizationDataService = TestBed.inject(CharacterizationDataCosmosService);
    cellModelService = TestBed.inject(CellModelCosmosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CellModelCosmos query and add missing value', () => {
      const characterizationData: ICharacterizationDataCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: 'c5ddd5fb-e6d0-4293-8289-556605d7cc3b' };
      characterizationData.cellModel = cellModel;

      const cellModelCollection: ICellModelCosmos[] = [{ id: 'c1f985be-9748-4d84-a67d-9e2836f3feb7' }];
      jest.spyOn(cellModelService, 'query').mockReturnValue(of(new HttpResponse({ body: cellModelCollection })));
      const additionalCellModelCosmos = [cellModel];
      const expectedCollection: ICellModelCosmos[] = [...additionalCellModelCosmos, ...cellModelCollection];
      jest.spyOn(cellModelService, 'addCellModelCosmosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ characterizationData });
      comp.ngOnInit();

      expect(cellModelService.query).toHaveBeenCalled();
      expect(cellModelService.addCellModelCosmosToCollectionIfMissing).toHaveBeenCalledWith(
        cellModelCollection,
        ...additionalCellModelCosmos.map(expect.objectContaining)
      );
      expect(comp.cellModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const characterizationData: ICharacterizationDataCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: '7264de95-02dc-442e-ad13-03aae975bcc8' };
      characterizationData.cellModel = cellModel;

      activatedRoute.data = of({ characterizationData });
      comp.ngOnInit();

      expect(comp.cellModelsSharedCollection).toContain(cellModel);
      expect(comp.characterizationData).toEqual(characterizationData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICharacterizationDataCosmos>>();
      const characterizationData = { id: 123 };
      jest.spyOn(characterizationDataFormService, 'getCharacterizationDataCosmos').mockReturnValue(characterizationData);
      jest.spyOn(characterizationDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ characterizationData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: characterizationData }));
      saveSubject.complete();

      // THEN
      expect(characterizationDataFormService.getCharacterizationDataCosmos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(characterizationDataService.update).toHaveBeenCalledWith(expect.objectContaining(characterizationData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICharacterizationDataCosmos>>();
      const characterizationData = { id: 123 };
      jest.spyOn(characterizationDataFormService, 'getCharacterizationDataCosmos').mockReturnValue({ id: null });
      jest.spyOn(characterizationDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ characterizationData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: characterizationData }));
      saveSubject.complete();

      // THEN
      expect(characterizationDataFormService.getCharacterizationDataCosmos).toHaveBeenCalled();
      expect(characterizationDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICharacterizationDataCosmos>>();
      const characterizationData = { id: 123 };
      jest.spyOn(characterizationDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ characterizationData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(characterizationDataService.update).toHaveBeenCalled();
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
