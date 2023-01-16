import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectSupportedCosmos } from '../project-supported-cosmos.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../project-supported-cosmos.test-samples';

import { ProjectSupportedCosmosService } from './project-supported-cosmos.service';

const requireRestSample: IProjectSupportedCosmos = {
  ...sampleWithRequiredData,
};

describe('ProjectSupportedCosmos Service', () => {
  let service: ProjectSupportedCosmosService;
  let httpMock: HttpTestingController;
  let expectedResult: IProjectSupportedCosmos | IProjectSupportedCosmos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjectSupportedCosmosService);
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

    it('should create a ProjectSupportedCosmos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const projectSupported = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(projectSupported).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProjectSupportedCosmos', () => {
      const projectSupported = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(projectSupported).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProjectSupportedCosmos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProjectSupportedCosmos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProjectSupportedCosmos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProjectSupportedCosmosToCollectionIfMissing', () => {
      it('should add a ProjectSupportedCosmos to an empty array', () => {
        const projectSupported: IProjectSupportedCosmos = sampleWithRequiredData;
        expectedResult = service.addProjectSupportedCosmosToCollectionIfMissing([], projectSupported);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectSupported);
      });

      it('should not add a ProjectSupportedCosmos to an array that contains it', () => {
        const projectSupported: IProjectSupportedCosmos = sampleWithRequiredData;
        const projectSupportedCollection: IProjectSupportedCosmos[] = [
          {
            ...projectSupported,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProjectSupportedCosmosToCollectionIfMissing(projectSupportedCollection, projectSupported);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProjectSupportedCosmos to an array that doesn't contain it", () => {
        const projectSupported: IProjectSupportedCosmos = sampleWithRequiredData;
        const projectSupportedCollection: IProjectSupportedCosmos[] = [sampleWithPartialData];
        expectedResult = service.addProjectSupportedCosmosToCollectionIfMissing(projectSupportedCollection, projectSupported);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectSupported);
      });

      it('should add only unique ProjectSupportedCosmos to an array', () => {
        const projectSupportedArray: IProjectSupportedCosmos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const projectSupportedCollection: IProjectSupportedCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addProjectSupportedCosmosToCollectionIfMissing(projectSupportedCollection, ...projectSupportedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projectSupported: IProjectSupportedCosmos = sampleWithRequiredData;
        const projectSupported2: IProjectSupportedCosmos = sampleWithPartialData;
        expectedResult = service.addProjectSupportedCosmosToCollectionIfMissing([], projectSupported, projectSupported2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectSupported);
        expect(expectedResult).toContain(projectSupported2);
      });

      it('should accept null and undefined values', () => {
        const projectSupported: IProjectSupportedCosmos = sampleWithRequiredData;
        expectedResult = service.addProjectSupportedCosmosToCollectionIfMissing([], null, projectSupported, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectSupported);
      });

      it('should return initial array if no ProjectSupportedCosmos is added', () => {
        const projectSupportedCollection: IProjectSupportedCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addProjectSupportedCosmosToCollectionIfMissing(projectSupportedCollection, undefined, null);
        expect(expectedResult).toEqual(projectSupportedCollection);
      });
    });

    describe('compareProjectSupportedCosmos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProjectSupportedCosmos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProjectSupportedCosmos(entity1, entity2);
        const compareResult2 = service.compareProjectSupportedCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProjectSupportedCosmos(entity1, entity2);
        const compareResult2 = service.compareProjectSupportedCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProjectSupportedCosmos(entity1, entity2);
        const compareResult2 = service.compareProjectSupportedCosmos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
