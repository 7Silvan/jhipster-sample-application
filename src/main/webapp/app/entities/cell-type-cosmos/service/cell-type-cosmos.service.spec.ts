import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICellTypeCosmos } from '../cell-type-cosmos.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cell-type-cosmos.test-samples';

import { CellTypeCosmosService } from './cell-type-cosmos.service';

const requireRestSample: ICellTypeCosmos = {
  ...sampleWithRequiredData,
};

describe('CellTypeCosmos Service', () => {
  let service: CellTypeCosmosService;
  let httpMock: HttpTestingController;
  let expectedResult: ICellTypeCosmos | ICellTypeCosmos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CellTypeCosmosService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CellTypeCosmos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cellType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cellType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CellTypeCosmos', () => {
      const cellType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cellType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CellTypeCosmos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CellTypeCosmos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CellTypeCosmos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCellTypeCosmosToCollectionIfMissing', () => {
      it('should add a CellTypeCosmos to an empty array', () => {
        const cellType: ICellTypeCosmos = sampleWithRequiredData;
        expectedResult = service.addCellTypeCosmosToCollectionIfMissing([], cellType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cellType);
      });

      it('should not add a CellTypeCosmos to an array that contains it', () => {
        const cellType: ICellTypeCosmos = sampleWithRequiredData;
        const cellTypeCollection: ICellTypeCosmos[] = [
          {
            ...cellType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCellTypeCosmosToCollectionIfMissing(cellTypeCollection, cellType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CellTypeCosmos to an array that doesn't contain it", () => {
        const cellType: ICellTypeCosmos = sampleWithRequiredData;
        const cellTypeCollection: ICellTypeCosmos[] = [sampleWithPartialData];
        expectedResult = service.addCellTypeCosmosToCollectionIfMissing(cellTypeCollection, cellType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cellType);
      });

      it('should add only unique CellTypeCosmos to an array', () => {
        const cellTypeArray: ICellTypeCosmos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cellTypeCollection: ICellTypeCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addCellTypeCosmosToCollectionIfMissing(cellTypeCollection, ...cellTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cellType: ICellTypeCosmos = sampleWithRequiredData;
        const cellType2: ICellTypeCosmos = sampleWithPartialData;
        expectedResult = service.addCellTypeCosmosToCollectionIfMissing([], cellType, cellType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cellType);
        expect(expectedResult).toContain(cellType2);
      });

      it('should accept null and undefined values', () => {
        const cellType: ICellTypeCosmos = sampleWithRequiredData;
        expectedResult = service.addCellTypeCosmosToCollectionIfMissing([], null, cellType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cellType);
      });

      it('should return initial array if no CellTypeCosmos is added', () => {
        const cellTypeCollection: ICellTypeCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addCellTypeCosmosToCollectionIfMissing(cellTypeCollection, undefined, null);
        expect(expectedResult).toEqual(cellTypeCollection);
      });
    });

    describe('compareCellTypeCosmos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCellTypeCosmos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCellTypeCosmos(entity1, entity2);
        const compareResult2 = service.compareCellTypeCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCellTypeCosmos(entity1, entity2);
        const compareResult2 = service.compareCellTypeCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCellTypeCosmos(entity1, entity2);
        const compareResult2 = service.compareCellTypeCosmos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
