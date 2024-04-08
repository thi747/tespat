import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILocal } from '../local.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../local.test-samples';

import { LocalService } from './local.service';

const requireRestSample: ILocal = {
  ...sampleWithRequiredData,
};

describe('Local Service', () => {
  let service: LocalService;
  let httpMock: HttpTestingController;
  let expectedResult: ILocal | ILocal[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LocalService);
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

    it('should create a Local', () => {
      const local = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(local).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Local', () => {
      const local = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(local).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Local', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Local', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Local', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLocalToCollectionIfMissing', () => {
      it('should add a Local to an empty array', () => {
        const local: ILocal = sampleWithRequiredData;
        expectedResult = service.addLocalToCollectionIfMissing([], local);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(local);
      });

      it('should not add a Local to an array that contains it', () => {
        const local: ILocal = sampleWithRequiredData;
        const localCollection: ILocal[] = [
          {
            ...local,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLocalToCollectionIfMissing(localCollection, local);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Local to an array that doesn't contain it", () => {
        const local: ILocal = sampleWithRequiredData;
        const localCollection: ILocal[] = [sampleWithPartialData];
        expectedResult = service.addLocalToCollectionIfMissing(localCollection, local);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(local);
      });

      it('should add only unique Local to an array', () => {
        const localArray: ILocal[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const localCollection: ILocal[] = [sampleWithRequiredData];
        expectedResult = service.addLocalToCollectionIfMissing(localCollection, ...localArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const local: ILocal = sampleWithRequiredData;
        const local2: ILocal = sampleWithPartialData;
        expectedResult = service.addLocalToCollectionIfMissing([], local, local2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(local);
        expect(expectedResult).toContain(local2);
      });

      it('should accept null and undefined values', () => {
        const local: ILocal = sampleWithRequiredData;
        expectedResult = service.addLocalToCollectionIfMissing([], null, local, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(local);
      });

      it('should return initial array if no Local is added', () => {
        const localCollection: ILocal[] = [sampleWithRequiredData];
        expectedResult = service.addLocalToCollectionIfMissing(localCollection, undefined, null);
        expect(expectedResult).toEqual(localCollection);
      });
    });

    describe('compareLocal', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLocal(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLocal(entity1, entity2);
        const compareResult2 = service.compareLocal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLocal(entity1, entity2);
        const compareResult2 = service.compareLocal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLocal(entity1, entity2);
        const compareResult2 = service.compareLocal(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
