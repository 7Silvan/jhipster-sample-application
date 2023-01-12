import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPublicationCosmos } from '../publication-cosmos.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../publication-cosmos.test-samples';

import { PublicationCosmosService } from './publication-cosmos.service';

const requireRestSample: IPublicationCosmos = {
  ...sampleWithRequiredData,
};

describe('PublicationCosmos Service', () => {
  let service: PublicationCosmosService;
  let httpMock: HttpTestingController;
  let expectedResult: IPublicationCosmos | IPublicationCosmos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PublicationCosmosService);
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

    it('should create a PublicationCosmos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const publication = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(publication).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PublicationCosmos', () => {
      const publication = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(publication).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PublicationCosmos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PublicationCosmos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PublicationCosmos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPublicationCosmosToCollectionIfMissing', () => {
      it('should add a PublicationCosmos to an empty array', () => {
        const publication: IPublicationCosmos = sampleWithRequiredData;
        expectedResult = service.addPublicationCosmosToCollectionIfMissing([], publication);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(publication);
      });

      it('should not add a PublicationCosmos to an array that contains it', () => {
        const publication: IPublicationCosmos = sampleWithRequiredData;
        const publicationCollection: IPublicationCosmos[] = [
          {
            ...publication,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPublicationCosmosToCollectionIfMissing(publicationCollection, publication);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PublicationCosmos to an array that doesn't contain it", () => {
        const publication: IPublicationCosmos = sampleWithRequiredData;
        const publicationCollection: IPublicationCosmos[] = [sampleWithPartialData];
        expectedResult = service.addPublicationCosmosToCollectionIfMissing(publicationCollection, publication);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(publication);
      });

      it('should add only unique PublicationCosmos to an array', () => {
        const publicationArray: IPublicationCosmos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const publicationCollection: IPublicationCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addPublicationCosmosToCollectionIfMissing(publicationCollection, ...publicationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const publication: IPublicationCosmos = sampleWithRequiredData;
        const publication2: IPublicationCosmos = sampleWithPartialData;
        expectedResult = service.addPublicationCosmosToCollectionIfMissing([], publication, publication2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(publication);
        expect(expectedResult).toContain(publication2);
      });

      it('should accept null and undefined values', () => {
        const publication: IPublicationCosmos = sampleWithRequiredData;
        expectedResult = service.addPublicationCosmosToCollectionIfMissing([], null, publication, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(publication);
      });

      it('should return initial array if no PublicationCosmos is added', () => {
        const publicationCollection: IPublicationCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addPublicationCosmosToCollectionIfMissing(publicationCollection, undefined, null);
        expect(expectedResult).toEqual(publicationCollection);
      });
    });

    describe('comparePublicationCosmos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePublicationCosmos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePublicationCosmos(entity1, entity2);
        const compareResult2 = service.comparePublicationCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePublicationCosmos(entity1, entity2);
        const compareResult2 = service.comparePublicationCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePublicationCosmos(entity1, entity2);
        const compareResult2 = service.comparePublicationCosmos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
