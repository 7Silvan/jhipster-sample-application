import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PublicationCosmosFormService } from './publication-cosmos-form.service';
import { PublicationCosmosService } from '../service/publication-cosmos.service';
import { IPublicationCosmos } from '../publication-cosmos.model';
import { ICellModelCosmos } from 'app/entities/cell-model-cosmos/cell-model-cosmos.model';
import { CellModelCosmosService } from 'app/entities/cell-model-cosmos/service/cell-model-cosmos.service';

import { PublicationCosmosUpdateComponent } from './publication-cosmos-update.component';

describe('PublicationCosmos Management Update Component', () => {
  let comp: PublicationCosmosUpdateComponent;
  let fixture: ComponentFixture<PublicationCosmosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let publicationFormService: PublicationCosmosFormService;
  let publicationService: PublicationCosmosService;
  let cellModelService: CellModelCosmosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PublicationCosmosUpdateComponent],
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
      .overrideTemplate(PublicationCosmosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PublicationCosmosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    publicationFormService = TestBed.inject(PublicationCosmosFormService);
    publicationService = TestBed.inject(PublicationCosmosService);
    cellModelService = TestBed.inject(CellModelCosmosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CellModelCosmos query and add missing value', () => {
      const publication: IPublicationCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: '50b0c6e6-4487-4f0c-91a8-ca52c1f20790' };
      publication.cellModel = cellModel;

      const cellModelCollection: ICellModelCosmos[] = [{ id: '13776716-5c64-47c2-b19c-58cd10060e3e' }];
      jest.spyOn(cellModelService, 'query').mockReturnValue(of(new HttpResponse({ body: cellModelCollection })));
      const additionalCellModelCosmos = [cellModel];
      const expectedCollection: ICellModelCosmos[] = [...additionalCellModelCosmos, ...cellModelCollection];
      jest.spyOn(cellModelService, 'addCellModelCosmosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ publication });
      comp.ngOnInit();

      expect(cellModelService.query).toHaveBeenCalled();
      expect(cellModelService.addCellModelCosmosToCollectionIfMissing).toHaveBeenCalledWith(
        cellModelCollection,
        ...additionalCellModelCosmos.map(expect.objectContaining)
      );
      expect(comp.cellModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const publication: IPublicationCosmos = { id: 456 };
      const cellModel: ICellModelCosmos = { id: '829491a0-1045-4667-8b97-47be607a030c' };
      publication.cellModel = cellModel;

      activatedRoute.data = of({ publication });
      comp.ngOnInit();

      expect(comp.cellModelsSharedCollection).toContain(cellModel);
      expect(comp.publication).toEqual(publication);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPublicationCosmos>>();
      const publication = { id: 123 };
      jest.spyOn(publicationFormService, 'getPublicationCosmos').mockReturnValue(publication);
      jest.spyOn(publicationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ publication });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: publication }));
      saveSubject.complete();

      // THEN
      expect(publicationFormService.getPublicationCosmos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(publicationService.update).toHaveBeenCalledWith(expect.objectContaining(publication));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPublicationCosmos>>();
      const publication = { id: 123 };
      jest.spyOn(publicationFormService, 'getPublicationCosmos').mockReturnValue({ id: null });
      jest.spyOn(publicationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ publication: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: publication }));
      saveSubject.complete();

      // THEN
      expect(publicationFormService.getPublicationCosmos).toHaveBeenCalled();
      expect(publicationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPublicationCosmos>>();
      const publication = { id: 123 };
      jest.spyOn(publicationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ publication });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(publicationService.update).toHaveBeenCalled();
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
