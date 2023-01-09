import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICharacterizationDataCosmos } from '../characterization-data-cosmos.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../characterization-data-cosmos.test-samples';

import { CharacterizationDataCosmosService } from './characterization-data-cosmos.service';

const requireRestSample: ICharacterizationDataCosmos = {
  ...sampleWithRequiredData,
};

describe('CharacterizationDataCosmos Service', () => {
  let service: CharacterizationDataCosmosService;
  let httpMock: HttpTestingController;
  let expectedResult: ICharacterizationDataCosmos | ICharacterizationDataCosmos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CharacterizationDataCosmosService);
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

    it('should create a CharacterizationDataCosmos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const characterizationData = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(characterizationData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CharacterizationDataCosmos', () => {
      const characterizationData = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(characterizationData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CharacterizationDataCosmos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CharacterizationDataCosmos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CharacterizationDataCosmos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCharacterizationDataCosmosToCollectionIfMissing', () => {
      it('should add a CharacterizationDataCosmos to an empty array', () => {
        const characterizationData: ICharacterizationDataCosmos = sampleWithRequiredData;
        expectedResult = service.addCharacterizationDataCosmosToCollectionIfMissing([], characterizationData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(characterizationData);
      });

      it('should not add a CharacterizationDataCosmos to an array that contains it', () => {
        const characterizationData: ICharacterizationDataCosmos = sampleWithRequiredData;
        const characterizationDataCollection: ICharacterizationDataCosmos[] = [
          {
            ...characterizationData,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCharacterizationDataCosmosToCollectionIfMissing(characterizationDataCollection, characterizationData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CharacterizationDataCosmos to an array that doesn't contain it", () => {
        const characterizationData: ICharacterizationDataCosmos = sampleWithRequiredData;
        const characterizationDataCollection: ICharacterizationDataCosmos[] = [sampleWithPartialData];
        expectedResult = service.addCharacterizationDataCosmosToCollectionIfMissing(characterizationDataCollection, characterizationData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(characterizationData);
      });

      it('should add only unique CharacterizationDataCosmos to an array', () => {
        const characterizationDataArray: ICharacterizationDataCosmos[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const characterizationDataCollection: ICharacterizationDataCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addCharacterizationDataCosmosToCollectionIfMissing(
          characterizationDataCollection,
          ...characterizationDataArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const characterizationData: ICharacterizationDataCosmos = sampleWithRequiredData;
        const characterizationData2: ICharacterizationDataCosmos = sampleWithPartialData;
        expectedResult = service.addCharacterizationDataCosmosToCollectionIfMissing([], characterizationData, characterizationData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(characterizationData);
        expect(expectedResult).toContain(characterizationData2);
      });

      it('should accept null and undefined values', () => {
        const characterizationData: ICharacterizationDataCosmos = sampleWithRequiredData;
        expectedResult = service.addCharacterizationDataCosmosToCollectionIfMissing([], null, characterizationData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(characterizationData);
      });

      it('should return initial array if no CharacterizationDataCosmos is added', () => {
        const characterizationDataCollection: ICharacterizationDataCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addCharacterizationDataCosmosToCollectionIfMissing(characterizationDataCollection, undefined, null);
        expect(expectedResult).toEqual(characterizationDataCollection);
      });
    });

    describe('compareCharacterizationDataCosmos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCharacterizationDataCosmos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCharacterizationDataCosmos(entity1, entity2);
        const compareResult2 = service.compareCharacterizationDataCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCharacterizationDataCosmos(entity1, entity2);
        const compareResult2 = service.compareCharacterizationDataCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCharacterizationDataCosmos(entity1, entity2);
        const compareResult2 = service.compareCharacterizationDataCosmos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
