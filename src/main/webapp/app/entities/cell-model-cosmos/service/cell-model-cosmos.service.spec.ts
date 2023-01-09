import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICellModelCosmos } from '../cell-model-cosmos.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cell-model-cosmos.test-samples';

import { CellModelCosmosService, RestCellModelCosmos } from './cell-model-cosmos.service';

const requireRestSample: RestCellModelCosmos = {
  ...sampleWithRequiredData,
  dateAdded: sampleWithRequiredData.dateAdded?.toJSON(),
  dateUpdated: sampleWithRequiredData.dateUpdated?.toJSON(),
};

describe('CellModelCosmos Service', () => {
  let service: CellModelCosmosService;
  let httpMock: HttpTestingController;
  let expectedResult: ICellModelCosmos | ICellModelCosmos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CellModelCosmosService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CellModelCosmos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cellModel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cellModel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CellModelCosmos', () => {
      const cellModel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cellModel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CellModelCosmos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CellModelCosmos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CellModelCosmos', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCellModelCosmosToCollectionIfMissing', () => {
      it('should add a CellModelCosmos to an empty array', () => {
        const cellModel: ICellModelCosmos = sampleWithRequiredData;
        expectedResult = service.addCellModelCosmosToCollectionIfMissing([], cellModel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cellModel);
      });

      it('should not add a CellModelCosmos to an array that contains it', () => {
        const cellModel: ICellModelCosmos = sampleWithRequiredData;
        const cellModelCollection: ICellModelCosmos[] = [
          {
            ...cellModel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCellModelCosmosToCollectionIfMissing(cellModelCollection, cellModel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CellModelCosmos to an array that doesn't contain it", () => {
        const cellModel: ICellModelCosmos = sampleWithRequiredData;
        const cellModelCollection: ICellModelCosmos[] = [sampleWithPartialData];
        expectedResult = service.addCellModelCosmosToCollectionIfMissing(cellModelCollection, cellModel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cellModel);
      });

      it('should add only unique CellModelCosmos to an array', () => {
        const cellModelArray: ICellModelCosmos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cellModelCollection: ICellModelCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addCellModelCosmosToCollectionIfMissing(cellModelCollection, ...cellModelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cellModel: ICellModelCosmos = sampleWithRequiredData;
        const cellModel2: ICellModelCosmos = sampleWithPartialData;
        expectedResult = service.addCellModelCosmosToCollectionIfMissing([], cellModel, cellModel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cellModel);
        expect(expectedResult).toContain(cellModel2);
      });

      it('should accept null and undefined values', () => {
        const cellModel: ICellModelCosmos = sampleWithRequiredData;
        expectedResult = service.addCellModelCosmosToCollectionIfMissing([], null, cellModel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cellModel);
      });

      it('should return initial array if no CellModelCosmos is added', () => {
        const cellModelCollection: ICellModelCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addCellModelCosmosToCollectionIfMissing(cellModelCollection, undefined, null);
        expect(expectedResult).toEqual(cellModelCollection);
      });
    });

    describe('compareCellModelCosmos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCellModelCosmos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareCellModelCosmos(entity1, entity2);
        const compareResult2 = service.compareCellModelCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareCellModelCosmos(entity1, entity2);
        const compareResult2 = service.compareCellModelCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareCellModelCosmos(entity1, entity2);
        const compareResult2 = service.compareCellModelCosmos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
