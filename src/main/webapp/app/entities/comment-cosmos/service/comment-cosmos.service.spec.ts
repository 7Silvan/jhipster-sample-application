import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICommentCosmos } from '../comment-cosmos.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../comment-cosmos.test-samples';

import { CommentCosmosService, RestCommentCosmos } from './comment-cosmos.service';

const requireRestSample: RestCommentCosmos = {
  ...sampleWithRequiredData,
  timeCreated: sampleWithRequiredData.timeCreated?.toJSON(),
};

describe('CommentCosmos Service', () => {
  let service: CommentCosmosService;
  let httpMock: HttpTestingController;
  let expectedResult: ICommentCosmos | ICommentCosmos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommentCosmosService);
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

    it('should create a CommentCosmos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const comment = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(comment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CommentCosmos', () => {
      const comment = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(comment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CommentCosmos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CommentCosmos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CommentCosmos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCommentCosmosToCollectionIfMissing', () => {
      it('should add a CommentCosmos to an empty array', () => {
        const comment: ICommentCosmos = sampleWithRequiredData;
        expectedResult = service.addCommentCosmosToCollectionIfMissing([], comment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comment);
      });

      it('should not add a CommentCosmos to an array that contains it', () => {
        const comment: ICommentCosmos = sampleWithRequiredData;
        const commentCollection: ICommentCosmos[] = [
          {
            ...comment,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommentCosmosToCollectionIfMissing(commentCollection, comment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CommentCosmos to an array that doesn't contain it", () => {
        const comment: ICommentCosmos = sampleWithRequiredData;
        const commentCollection: ICommentCosmos[] = [sampleWithPartialData];
        expectedResult = service.addCommentCosmosToCollectionIfMissing(commentCollection, comment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comment);
      });

      it('should add only unique CommentCosmos to an array', () => {
        const commentArray: ICommentCosmos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const commentCollection: ICommentCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addCommentCosmosToCollectionIfMissing(commentCollection, ...commentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const comment: ICommentCosmos = sampleWithRequiredData;
        const comment2: ICommentCosmos = sampleWithPartialData;
        expectedResult = service.addCommentCosmosToCollectionIfMissing([], comment, comment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comment);
        expect(expectedResult).toContain(comment2);
      });

      it('should accept null and undefined values', () => {
        const comment: ICommentCosmos = sampleWithRequiredData;
        expectedResult = service.addCommentCosmosToCollectionIfMissing([], null, comment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comment);
      });

      it('should return initial array if no CommentCosmos is added', () => {
        const commentCollection: ICommentCosmos[] = [sampleWithRequiredData];
        expectedResult = service.addCommentCosmosToCollectionIfMissing(commentCollection, undefined, null);
        expect(expectedResult).toEqual(commentCollection);
      });
    });

    describe('compareCommentCosmos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCommentCosmos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCommentCosmos(entity1, entity2);
        const compareResult2 = service.compareCommentCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCommentCosmos(entity1, entity2);
        const compareResult2 = service.compareCommentCosmos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCommentCosmos(entity1, entity2);
        const compareResult2 = service.compareCommentCosmos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
